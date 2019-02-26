package com.lance.business.service.impl;

import com.lance.business.dao.ApiDao;
import com.lance.business.service.ApiService;
import com.lance.common.base.model.JsonResult;
import com.lance.common.config.datasource.TargetDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>业务处理service接口实现类
 *
 * @author lance
 * @since 2018-09-25
 **/
@Slf4j
@Service
public class ApiServiceImpl implements ApiService
{
    @Autowired
    private ApiDao apiDao;

    @Override
    public JsonResult defaultTest()
    {
        return new JsonResult(true, "", apiDao.listDefaultDataSource());
    }

    @TargetDataSource(name = "ds1")
    @Override
    public JsonResult defaultTestS1()
    {
        return new JsonResult(true, "", apiDao.listDefaultDataSourceS1());
    }

    @TargetDataSource(name = "ds2")
    @Override
    public JsonResult defaultTestS2()
    {
        return new JsonResult(true, "", apiDao.listDefaultDataSourceS2());
    }
}
