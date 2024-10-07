package com.lxl.demo.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lxl.utility.handlers.SecretHandler;
import com.lxl.utility.markup.*;
import com.lxl.demo.parse.markup.WebLabelConversion;
import com.lxl.demo.parse.constant.WebLabelNameEnum;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @TableName user
 */
@Data
@Sensitive
@TableName("user")
@RequiredArgsConstructor
@ExcelFileName(value = "测试导出", sheetName = "导出1")
@Accessors(chain = true)
public class User {
    /**
     *
     */
    @TableId
    private Long id;

    /**
     *
     */
    @WebDataMaskingConversion(isName = true)
    @ExcelField("用户名")
    private String username;

    /**
     *
     */
    @Secret
//    @TableField(typeHandler = SecretHandler.class)
    @SensitiveLikeQuery
    @WebDataMaskingConversion()
    @ExcelField("密码")
    private String password;

    /**
     *
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime createTime;

    @WebLabelConversion(label = "deletedName", type = WebLabelNameEnum.DELETED)
    private Boolean deleted;
    @TableField(exist = false)
    private String deletedName;

    @WebLabelConversion(label = "amountName", type = WebLabelNameEnum.AMOUNT_DATAFORMART)
    private BigDecimal amount;
    @TableField(exist = false)
    @ExcelField("金额")
    private String amountName;
}
