package com.lxl.demo.parse.markup;


import com.lxl.demo.parse.constant.WebLabelNameEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @Description 字段数据转换标注
 * @author liuxinlei
 * @date 2024/4/28 16:14
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface WebLabelConversion {

    /**
     * 转换到中文的列
     */
     String label();
    /**
     * 类型
     */
     WebLabelNameEnum type();
}
