package com.capitaltg.reactiveapp.service;

import com.capitaltg.reactiveapp.domain.Category;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/**
 * This service simulates getting data from a slow external data source.
 * @author Lucas Holt
 */
@Service
public class CategoryService {

    /**
     * Get a list of software package categories in a reactive manner.
     * @return
     */
    public Flux<Category> getCategories() {
        List<Category> categories = new ArrayList<>();
        categories.add(Category.builder().id(1).name("ftp").description("File Transfer Protocol Apps").build());
        categories.add(Category.builder().id(2).name("misc").description("Applications that fit no where else").build());
        categories.add(Category.builder().id(3).name("www").description("World Wide Web apps").build());

        return Flux.fromIterable(categories).delayElements(Duration.ofSeconds(1));
    }
}
