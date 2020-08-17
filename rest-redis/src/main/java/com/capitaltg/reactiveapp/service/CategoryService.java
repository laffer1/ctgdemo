package com.capitaltg.reactiveapp.service;

import com.capitaltg.reactiveapp.domain.Category;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import javax.annotation.PostConstruct;
import java.util.Comparator;
import java.util.Objects;

/**
 * This service pulls a list of categories from the MidnightBSD App Store REST API.
 * <p>
 * The web app we're getting data from is located at http://app.midnightbsd.org/#!/ and provides APIs on /api/
 *
 * @author Lucas Holt
 * @see com.capitaltg.reactiveapp.config.WebClientConfig
 */
@Slf4j
@Service
public class CategoryService {

    private static final String KEY = "CATEGORY_KEY";

    @Qualifier("appStoreWebClient")
    @Autowired
    private WebClient appStoreWebClient;

    @Autowired
    private ReactiveRedisTemplate<String, Category> reactiveRedisTemplateCategory;

    /**
     * Get a list of software package categories in a reactive manner.  This would replace RestTemplate calls in a
     * traditional spring app.
     *
     * @return
     */
    public Flux<Category> getCategories() {
        return appStoreWebClient.get()
                .uri("/category")
                .retrieve()
                .bodyToFlux(Category.class)
                .distinct();
    }

    /**
     * Fetch a cached list from a Redis backed store. 
     * @return
     */
    public Flux<Category> getCachedCategories() {
        return reactiveRedisTemplateCategory.opsForSet()
                .members(KEY)
                .sort(Comparator.comparing(Category::getName));
    }

    /**
     * Populate categories at app startup. 
     */
    @PostConstruct
    public void refresh() {
        log.info("Loading categories");
        
        getCategories()
                .filter(Objects::nonNull)
                .map(c ->
                {
                    log.info("Adding category {}", c.getName());
                    reactiveRedisTemplateCategory.opsForSet().add(KEY, c).publishOn(Schedulers.boundedElastic()).subscribe();
                    return c;
                })
                .subscribe();
    }
}
