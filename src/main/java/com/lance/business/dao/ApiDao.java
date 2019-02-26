package com.lance.business.dao;

import com.lance.common.base.dao.BaseDao;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>业务处理dao层
 *
 * @author lance
 * @since 2018-09-25
 **/
@Repository
@SuppressWarnings("unchecked")
public class ApiDao extends BaseDao
{
    /**
     * 默认数据源测试
     *
     * @return
     */
    public List listDefaultDataSource()
    {
        String sql = "select 1 from dual";
        return super.queryForList(sql);
    }

    /**
     * 数据源1
     *
     * @return
     */
    public List listDefaultDataSourceS1()
    {
        String sql = "select 2 from dual";
        return super.queryForList(sql);
    }

    /**
     * 数据源2
     *
     * @return
     */
    public List listDefaultDataSourceS2()
    {
        String sql = "select 3 from dual";
        return super.queryForList(sql);
    }
}
