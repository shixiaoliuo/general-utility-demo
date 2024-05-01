package com.lxl.demo.parse;

import com.lxl.demo.parse.constant.DeletedFlagEnum;
import com.lxl.demo.parse.constant.WebLabelNameEnum;
import com.lxl.demo.parse.markup.DataFormat;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @Description
 * @author liuxinlei
 * @date 2024/5/1 20:13
 */
@Component
@DataFormat(type = WebLabelNameEnum.DELETED)
public class DeletedFlagDataFormat implements DataFormatInterface {

    @Override
    public Map<String, String> query() {
        return Arrays.stream(DeletedFlagEnum.values()).collect(Collectors.toMap(DeletedFlagEnum::getCode, DeletedFlagEnum::getDesc));
    }
}
