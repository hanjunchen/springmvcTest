package com.hsgene.entity;

import lombok.*;

/**
 * Created by hjc on 2016/9/27.
 */
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private String id;
    private String name;
    private Integer age;
}
