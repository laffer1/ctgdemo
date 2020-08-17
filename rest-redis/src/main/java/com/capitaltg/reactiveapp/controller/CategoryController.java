package com.capitaltg.reactiveapp.controller;

import com.capitaltg.reactiveapp.domain.Category;
import com.capitaltg.reactiveapp.service.CategoryService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * Category REST API
 *
 * @author Lucas Holt
 */
@RequestMapping("/api/category")
@RestController
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(final CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    /**
     * Event streams can be useful for modern reactive front end frameworks.
     *
     * @return
     */
    @GetMapping(name = "/event", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Category> getCategoriesEventStream() {
        return categoryService.getCachedCategories();
    }

    /**
     * Same thing but as a JSON response.
     * @return
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<Category> getCategories() {
        return categoryService.getCachedCategories();
    }
}
