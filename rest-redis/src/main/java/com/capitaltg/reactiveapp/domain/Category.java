package com.capitaltg.reactiveapp.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * Software Package Category.
 * @author Lucas Holt
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
public class Category implements Serializable, Comparable<Category> {

    private int id;

    private String name;

    private String description;
    
    public Category() {
        super();
    }

    @Override
    public int compareTo(final Category o) {
        if (this.getName() == null)
            return -1;
        if (o.getName() == null) {
            return 1;
        }

        return this.getName().compareTo(o.getName());
    }
}
