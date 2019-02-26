package com.lance.common.config.datasource;

import java.lang.annotation.*;

/**
 * 目标数据源
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TargetDataSource
{
    String name();
}
