package com.lance.common.config.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * 注册动态数据源
 */
@Slf4j
@SuppressWarnings("unchecked")
public class DynamicDataSourceRegister implements ImportBeanDefinitionRegistrar, EnvironmentAware
{
    /**
     * 主数据源
     */
    private DataSource masterDataSource;

    /**
     * 从数据源
     */
    private Map<String, DataSource> slaveDataSources = new HashMap<>();

    @Override
    public void setEnvironment(Environment environment)
    {
        loadMasterDataSource(environment);
        loadSlaveDataSources(environment);
    }

    /**
     * 加载默认数据源
     *
     * @param environment
     */
    private void loadMasterDataSource(Environment environment)
    {
        // 读取主数据源
        Map<String, Object> dataSourceMap = new HashMap<>(4);
        dataSourceMap.put("driver", environment.getProperty("spring.datasource.driver-class-name"));
        dataSourceMap.put("url", environment.getProperty("spring.datasource.url"));
        dataSourceMap.put("username", environment.getProperty("spring.datasource.username"));
        dataSourceMap.put("password", environment.getProperty("spring.datasource.password"));

        masterDataSource = loadDruidDataSource(dataSourceMap, environment);
    }

    /**
     * 加载其他slave数据源
     *
     * @param environment
     */
    private void loadSlaveDataSources(Environment environment)
    {
        // 其他数据源
        String dataSourcePrefixes = environment.getProperty("slave.datasource.using");
        if (!StringUtils.isEmpty(dataSourcePrefixes))
        {
            String[] dataSourceArray = dataSourcePrefixes.split(",");
            for (String dataSourcePrefix : dataSourceArray)
            {
                Map<String, Object> dataSourceMap = new HashMap<>(4);
                dataSourceMap.put("driver", environment.getProperty("slave.datasource." + dataSourcePrefix + ".driver-class-name"));
                dataSourceMap.put("url", environment.getProperty("slave.datasource." + dataSourcePrefix + ".url"));
                dataSourceMap.put("username", environment.getProperty("slave.datasource." + dataSourcePrefix + ".username"));
                dataSourceMap.put("password", environment.getProperty("slave.datasource." + dataSourcePrefix + ".password"));

                DataSource dataSource = loadDruidDataSource(dataSourceMap, environment);
                slaveDataSources.put(dataSourcePrefix, dataSource);
            }
        }
    }

    /**
     * 注册动态数据源bean
     *
     * @param annotationMetadata
     * @param beanDefinitionRegistry
     */
    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry beanDefinitionRegistry)
    {
        Map<Object, Object> targetDataSources = new HashMap<>();
        //添加默认数据源
        targetDataSources.put("dataSource", this.masterDataSource);
        DynamicDataSourceContextHolder.dataSourceIds.add("dataSource");

        //添加其他数据源
        targetDataSources.putAll(slaveDataSources);
        DynamicDataSourceContextHolder.dataSourceIds.addAll(slaveDataSources.keySet());

        //创建动态数据源
        GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
        beanDefinition.setBeanClass(DynamicDataSource.class);
        beanDefinition.setSynthetic(true);
        MutablePropertyValues mutablePropertyValues = beanDefinition.getPropertyValues();
        mutablePropertyValues.addPropertyValue("defaultTargetDataSource", masterDataSource);
        mutablePropertyValues.addPropertyValue("targetDataSources", targetDataSources);

        //向spring context注册数据源bean
        beanDefinitionRegistry.registerBeanDefinition("dataSource", beanDefinition);
    }

    /**
     * 加载德鲁伊数据源
     *
     * @param dataSourceMap
     * @param environment
     * @return
     */
    private DataSource loadDruidDataSource(Map<String, Object> dataSourceMap, Environment environment)
    {
        DruidDataSource datasource = new DruidDataSource();
        datasource.setUrl(String.valueOf(dataSourceMap.get("url")));
        datasource.setUsername(String.valueOf(dataSourceMap.get("username")));
        datasource.setPassword(String.valueOf(dataSourceMap.get("password")));
        datasource.setDriverClassName(String.valueOf(dataSourceMap.get("driver")));
        datasource.setInitialSize(Integer.parseInt(environment.getProperty("spring.datasource.initialSize", "50")));
        datasource.setMinIdle(Integer.parseInt(environment.getProperty("spring.datasource.minIdle", "50")));
        datasource.setMaxActive(Integer.parseInt(environment.getProperty("spring.datasource.maxActive", "50")));
        datasource.setMaxWait(Long.parseLong(environment.getProperty("spring.datasource.maxWait", "60000")));
        datasource.setTimeBetweenEvictionRunsMillis(Long.parseLong(environment.getProperty("spring.datasource.timeBetweenEvictionRunsMillis", "60000")));
        datasource.setMinEvictableIdleTimeMillis(Long.parseLong(environment.getProperty("spring.datasource.minEvictableIdleTimeMillis", "60000")));
        datasource.setValidationQuery(environment.getProperty("spring.datasource.validationQuery"));
        datasource.setTestWhileIdle(Boolean.parseBoolean(environment.getProperty("spring.datasource.testWhileIdle")));
        datasource.setTestOnBorrow(Boolean.parseBoolean(environment.getProperty("spring.datasource.testOnBorrow")));
        datasource.setTestOnReturn(Boolean.parseBoolean(environment.getProperty("spring.datasource.testOnReturn")));
        datasource.setPoolPreparedStatements(Boolean.parseBoolean(environment.getProperty("spring.datasource.poolPreparedStatements")));
        datasource.setMaxPoolPreparedStatementPerConnectionSize(Integer.parseInt(environment.getProperty("spring.datasource.maxPoolPreparedStatementPerConnectionSize", "100")));
        datasource.setConnectionProperties(environment.getProperty("spring.datasource.connectionProperties"));
        try
        {
            datasource.init();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return datasource;
    }
}
