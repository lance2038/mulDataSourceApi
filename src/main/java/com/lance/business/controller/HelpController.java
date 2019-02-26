package com.lance.business.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping(value = "/help")
public class HelpController
{
    /**
     * 展示测试
     *
     * @param modelAndView
     * @return
     */
    @RequestMapping(value = "/test")
    public ModelAndView test(ModelAndView modelAndView, @ModelAttribute("form") String code)
    {
        List<Map> list = new ArrayList<>();
        Map<String, String> map1 = new HashMap<>(3);
        map1.put("State", "0000");
        map1.put("Message", "成功");
        map1.put("Url", "https://www.baidu.com?text=slsklsddsjdsjkkjdfjkdajkadjkgkjdfjksdfkjdfgkjdfgkjdfgkjdfjhndfjhnk");

        Map<String, String> map2 = new HashMap<>(3);
        map2.put("State", "0001");
        map2.put("Message", "失败1");
        map2.put("Url", null);

        Map<String, String> map3 = new HashMap<>(3);
        map3.put("State", "0002");
        map3.put("Message", "失败2");
        map3.put("Url", null);

        list.add(map1);
        list.add(map2);
        list.add(map3);

        modelAndView.addObject("result", list);
        modelAndView.addObject("code", "dashabi");
        modelAndView.setViewName("TestPage");

        return modelAndView;
    }
}
