package com.springtest;

import com.lance.business.service.ApiService;
import com.lance.common.base.model.JsonResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests
{
    @Autowired
    private ApiService apiService;

    @Test
    public void contextLoads()
    {
        JsonResult ded = apiService.defaultTest();
        System.out.println("ded");
        System.out.println(ded);
        JsonResult de1 = apiService.defaultTestS1();
        System.out.println("de1");
        System.out.println(de1);
        JsonResult de2 = apiService.defaultTestS2();
        System.out.println("de2");
        System.out.println(de2);
    }

}
