package com.hsgene.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

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

    //   注意验证注解设在getter方法上，在控制器中配合@Validated注解和BindingResult类来验证
    @NotEmpty(message = "用户名不能为空")
    public String getName() {
        return name;
    }

    //@Size(min = 1,max = 3,message = "年龄不合法")    // 验证框架不支持数值类型，只能String类型
    public Integer getAge() {
        return age;
    }
}
