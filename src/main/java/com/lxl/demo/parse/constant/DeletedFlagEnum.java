package com.lxl.demo.parse.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 *
 * @Description
 * @author liuxinlei
 * @date 2024/5/1 20:10
 */
@RequiredArgsConstructor
@Getter
public enum DeletedFlagEnum {

    /**
     * 正常
     */
    NORMAL("false", "正常"),
    /**
     * 删除
     */
    DELETED("true", "删除");


    private final String code;
    private final String desc;

}
