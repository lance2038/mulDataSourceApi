package com.lance.common.config.datasource;

import java.util.ArrayList;
import java.util.List;

/**
 * 动态数据源上下文管理
 */
public class DynamicDataSourceContextHolder
{
    /**
     * 存放当前线程使用的数据源类型信息
     */
    private static final ThreadLocal<String> CONTEXT_HOLDER = new ThreadLocal<String>();

    /**
     * 存放数据源id
     */
    public static List<String> dataSourceIds = new ArrayList<String>();

    /**
     * 设置数据源
     *
     * @param dataSourceType
     */
    public static void setDataSourceType(String dataSourceType)
    {
        CONTEXT_HOLDER.set(dataSourceType);
    }

    /**
     * 获取数据源
     *
     * @return
     */
    public static String getDataSourceType()
    {
        return CONTEXT_HOLDER.get();
    }

    /**
     * 清除数据源
     */
    public static void clearDataSourceType()
    {
        CONTEXT_HOLDER.remove();
    }

    /**
     * 判断当前数据源是否存在
     *
     * @param dataSourceId
     * @return
     */
    public static boolean isContainsDataSource(String dataSourceId)
    {
        return dataSourceIds.contains(dataSourceId);
    }
}
