package com.lance.common.config.datasource;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 切换 关闭数据源
 */
@Slf4j
@Aspect
@Order(-1)
@Component
public class DynamicDataSourceAspect
{
    /**
     * 在注解的目标执行之前切换数据源
     *
     * @param joinPoint        切点
     * @param targetDataSource 目标数据源
     */
    @Before("@annotation(targetDataSource)")
    public void changeDataSource(JoinPoint joinPoint, TargetDataSource targetDataSource)
    {
        String dataSourceName = targetDataSource.name();

        if (!DynamicDataSourceContextHolder.isContainsDataSource(dataSourceName))
        {
            log.error("数据源 [{}] 不存在使用默认的数据源 [{}]", dataSourceName, joinPoint.getSignature());
        }
        else
        {
            DynamicDataSourceContextHolder.setDataSourceType(dataSourceName);
        }
    }

    /**
     * 在注解的目标执行之后清除数据源
     *
     * @param targetDataSource 目标数据源
     */
    @After("@annotation(targetDataSource)")
    public void clearDataSource(TargetDataSource targetDataSource)
    {
        DynamicDataSourceContextHolder.clearDataSourceType();
    }
}
