package com.capitaltg.reactiveapp.controller;

import com.capitaltg.reactiveapp.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.spring5.context.webflux.IReactiveDataDriverContextVariable;
import org.thymeleaf.spring5.context.webflux.ReactiveDataDriverContextVariable;

/**
 * Display the app index page.
 * @author Lucas Holt
 */
@Controller
public class HomeController {

    @Autowired
    private CategoryService categoryService;

    @RequestMapping("/")
    public String index(final Model model) {

        // Load Data from a Flux into the model so that it can be streamed.
        IReactiveDataDriverContextVariable reactiveDataDrivenMode =
                new ReactiveDataDriverContextVariable(categoryService.getCategories(), 1);

        model.addAttribute("categories", reactiveDataDrivenMode);


        return "index";
    }
}
