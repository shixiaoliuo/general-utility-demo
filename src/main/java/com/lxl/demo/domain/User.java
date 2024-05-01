package com.lxl.demo.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.lxl.utility.handlers.SecretHandler;
import com.lxl.utility.markup.Secret;
import com.lxl.utility.markup.Sensitive;
import com.lxl.utility.markup.WebDataMaskingConversion;
import com.lxl.demo.parse.markup.WebLabelConversion;
import com.lxl.demo.parse.constant.WebLabelNameEnum;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

/**
 *
 * @TableName user
 */
@Data
@Sensitive
@TableName("user")
@RequiredArgsConstructor
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
    private String username;

    /**
     *
     */
    @Secret
    @TableField(typeHandler = SecretHandler.class)
    @WebDataMaskingConversion()
    private String password;

    /**
     *
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime createTime;

    @WebLabelConversion(label="deletedName",type = WebLabelNameEnum.DELETED)
    private Boolean deleted;
    @TableField(exist = false)
    private String deletedName;

    @WebLabelConversion(label="amountName",type = WebLabelNameEnum.AMOUNT_DATAFORMART)
    private BigDecimal amount;
    @TableField(exist = false)
    private String amountName;
}
