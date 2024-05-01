package com.lxl.demo.parse;

import java.util.Map;

/**
 *
 * @Description 数据字段转换需实现接口
 * @author liuxinlei
 * @date 2024/4/30 15:38
 */
public interface DataFormatInterface {
    /**
     * 枚举查询
     */
    Map<String, String> query();
}
