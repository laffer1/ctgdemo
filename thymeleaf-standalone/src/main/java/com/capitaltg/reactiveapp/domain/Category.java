package com.capitaltg.reactiveapp.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Software Package Category.
 * @author Lucas Holt
 */
@Getter
@Setter
@Builder
public class Category {

    private int id;

    private String name;

    private String description;
}
