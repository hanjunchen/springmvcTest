package com.hsgene.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Size;

/**
 * Created by hjc on 2016/9/27.
 */
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private String id;
    private String name;
    private String password;

    public String getId() {
        return id;
    }

    //   注意验证注解设在getter方法上，在控制器中配合@Validated注解和BindingResult类来验证
    @NotEmpty(message = "用户名不能为空")
    public String getName() {
        return name;
    }

    @Size(min = 3,max = 10,message = "密码长度3-10位")    // JSR303不支持数值类型，只支持String类型
    public String getPassword() {
        return password;
    }
}
