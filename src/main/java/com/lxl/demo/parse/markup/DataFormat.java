package com.lxl.demo.parse.markup;


import com.lxl.demo.parse.constant.WebLabelNameEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @Description 数据字符串填充
 * @author liuxinlei
 * @date 2024/4/30 16:14
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface DataFormat {
    /**
     * 类型
     */
    WebLabelNameEnum type();
}
