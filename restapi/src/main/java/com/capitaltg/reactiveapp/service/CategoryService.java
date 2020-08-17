package com.capitaltg.reactiveapp.service;

import com.capitaltg.reactiveapp.domain.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

/**
 * This service pulls a list of categories from the MidnightBSD App Store REST API.
 *
 * The web app we're getting data from is located at http://app.midnightbsd.org/#!/ and provides APIs on /api/
 *
 * @author Lucas Holt
 * @see com.capitaltg.reactiveapp.config.WebClientConfig
 */
@Service
public class CategoryService {

    @Qualifier("appStoreWebClient")
    @Autowired
    private WebClient appStoreWebClient;

    /**
     * Get a list of software package categories in a reactive manner.  This would replace RestTemplate calls in
     * a traditional spring app.
     *
     *
     * @return
     */
    public Flux<Category> getCategories() {
        return appStoreWebClient.get()
                .uri("/category")
                .retrieve()
                .bodyToFlux(Category.class);
    }
}
