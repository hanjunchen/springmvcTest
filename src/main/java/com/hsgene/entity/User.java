package com.hsgene.entity;

import lombok.*;

/**
 * Created by hjc on 2016/9/27.
 */
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private String id;
    private String name;
    private Integer age;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }
}
