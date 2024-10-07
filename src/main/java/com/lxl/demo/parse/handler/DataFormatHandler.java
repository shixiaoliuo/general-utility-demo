package com.lxl.demo.parse.handler;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.map.MapUtil;
import com.google.common.collect.Maps;
import com.lxl.demo.parse.DataFormatInterface;
import com.lxl.demo.parse.constant.WebLabelNameEnum;
import com.lxl.demo.parse.markup.DataFormat;
import com.lxl.demo.parse.markup.WebLabelConversion;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author liuxinlei
 * @Description 数据填充处理器
 * @date 2024/4/30 15:38
 */
@Component
@Slf4j
public class DataFormatHandler implements ApplicationContextAware {

    private Map<WebLabelNameEnum, DataFormatInterface> mapInterfaceMap;


    public <T> void webLabelConversion(List<T> data, Class<T> tClass) {
        if (CollectionUtils.isEmpty(data)) {
            return;
        }
        //1.获取需要转换的字段
        Field[] fields = tClass.getDeclaredFields();
        Map<Field, WebLabelConversion> conversionMap = MapUtil.newHashMap();
        Map<WebLabelNameEnum, Map<String, String>> webLabelConversionMap = Maps.newHashMap();
        Map<String, Field> labelMap = MapUtil.newHashMap();
        for (Field field : fields) {
            labelMap.put(field.getName(), field);
            WebLabelConversion webLabelConversion = field.getAnnotation(WebLabelConversion.class);
            if (Objects.nonNull(webLabelConversion)) {
                conversionMap.put(field, webLabelConversion);
                if (!webLabelConversionMap.containsKey(webLabelConversion.type()) &&
                        mapInterfaceMap.containsKey(webLabelConversion.type())) {
                    webLabelConversionMap.put(webLabelConversion.type(),
                            mapInterfaceMap.get(webLabelConversion.type()).query());

                }
            }
        }
        //2.页面枚举填充
        webLabelConversion(conversionMap, labelMap, data, webLabelConversionMap);

    }

    private <T> void webLabelConversion(Map<Field, WebLabelConversion> conversionMap,
                                        Map<String, Field> labelMap,
                                        List<T> data,
                                        Map<WebLabelNameEnum, Map<String, String>> webLabelConversionMap) {

        data.forEach(t -> {
            for (Map.Entry<Field, WebLabelConversion> entry : conversionMap.entrySet()) {
                try {
                    entry.getKey().setAccessible(true);
                    Object value = entry.getKey().get(t);
                    if (value == null) {
                        continue;
                    }
                    List<String> values = formatValue(value);

                    WebLabelConversion conversion = entry.getValue();
                    List<String> labels = values.stream()
                            .map(v -> switchLabel(conversion.type(), v, webLabelConversionMap)).collect(Collectors.toList());
                    String labelStr = StringUtils.join(labels, ",");
                    log.info("webLabel填充 code = {} , type = {} , label = {} , conversionKEy = {}", value, conversion.type().name(), labelStr, conversion.label());
                    Field newKey = labelMap.get(conversion.label());
                    if (Objects.nonNull(newKey) && newKey.getType() == String.class) {
                        newKey.setAccessible(true);
                        newKey.set(t, labelStr);
                        continue;
                    }
                    log.info("conversionKEy = {} , 类型错误 , 非文本格式", conversion.label());


                } catch (Exception e) {
                    log.info("数据转换失败 , type = {}", entry.getValue().type().name());
                    log.error(e.getMessage(), e);
                }
            }

        });


    }

    /**
     * 金额千分位
     *
     * @param type
     * @param value
     * @param webLabelConversionMap
     * @return
     */
    private String switchLabel(WebLabelNameEnum type, String value, Map<WebLabelNameEnum, Map<String, String>> webLabelConversionMap) {
        if (type == WebLabelNameEnum.AMOUNT_DATAFORMART) {
            return new DecimalFormat("###,##0.00").format(Double.parseDouble(value));
        }
        if (webLabelConversionMap.containsKey(type)) {
            if (webLabelConversionMap.get(type).containsKey(value)) {
                return webLabelConversionMap.get(type).getOrDefault(value, value);
            }
        }
        return value;
    }

    private List<String> formatValue(Object value) {
        if (value instanceof String) {
            return Collections.singletonList(String.valueOf(value));
        }
        if (value instanceof List) {
            return ((List<?>) value).stream().map(String::valueOf).collect(Collectors.toList());
        }
        if (value instanceof LocalDateTime) {
            return Collections.singletonList(
                    ((LocalDateTime) value).format(DateTimeFormatter.ofPattern(DatePattern.NORM_DATETIME_PATTERN))
            );
        }
        if (value instanceof LocalDate) {
            return Collections.singletonList(
                    ((LocalDate) value).format(DateTimeFormatter.ofPattern(DatePattern.NORM_DATE_PATTERN))
            );
        }
        if (value instanceof BigDecimal) {
            return Collections.singletonList(String.valueOf(value));
        }
        if (value instanceof Integer) {
            return Collections.singletonList(String.valueOf(value));
        }
        if (value instanceof Boolean) {
            return Collections.singletonList(String.valueOf(value));
        }
        return Collections.emptyList();
    }

    /**
     * 初始化查询
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Collection<DataFormatInterface> dataFormatInterfaces = applicationContext.getBeansOfType(DataFormatInterface.class).values();
        log.info("初始化查询format策略类结束，size = {}", dataFormatInterfaces.size());
        mapInterfaceMap = dataFormatInterfaces.stream()
                .filter(impl -> impl.getClass().getAnnotation(DataFormat.class) != null)
                .collect(Collectors.toMap(impl -> impl.getClass().getAnnotation(DataFormat.class).type(),
                        impl -> impl));
    }
}
