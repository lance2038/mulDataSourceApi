package com.lance.business.controller;

import com.lance.business.service.ApiService;
import com.lance.common.base.model.JsonResult;
import com.lance.common.core.async.AsyncServiceProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p> 接口控制层
 *
 * @author lance
 * @version v0.0.1
 * @since 2018/9/25
 **/
@Slf4j
@Controller
@RequestMapping("/handler")
public class ApiController
{
    /**
     * 接口业务处理类
     */
    @Autowired
    private ApiService apiService;

    @Autowired
    private AsyncServiceProvider asyncServiceProvider;

    /**
     * test
     */
    @ResponseBody
    @RequestMapping(value = "/test")
    public JsonResult test()
    {
        for (int i = 0; i < 480; i++)
        {
            final String s = String.valueOf(i);
            asyncServiceProvider.process(() ->
            {
                try
                {
                    Thread.sleep(2);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                log.info(s);
            });
        }
        System.out.println(apiService.defaultTest());
        System.out.println(apiService.defaultTestS1());
        System.out.println(apiService.defaultTestS2());
        System.out.println(apiService.defaultTest());
        System.out.println(apiService.defaultTest());
        System.out.println(apiService.defaultTest());
        System.out.println(apiService.defaultTest());
        System.out.println(apiService.defaultTestS2());
        System.out.println(apiService.defaultTestS2());
        return apiService.defaultTest();
    }
}
