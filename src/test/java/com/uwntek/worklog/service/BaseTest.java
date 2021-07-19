package com.uwntek.worklog.service;

import cn.hutool.extra.spring.SpringUtil;
import com.uwntek.worklog.controller.user.LoginController;
import com.uwntek.worklog.filter.ShiroLoginFilter;
import com.uwntek.worklog.util.SpringContextUtils;
import org.apache.shiro.util.ThreadContext;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.servlet.Filter;


@RunWith(SpringRunner.class)
@SpringBootTest
public class BaseTest {
    @Autowired
    LoginController loginController;

    @Autowired
    org.apache.shiro.mgt.SecurityManager securityManager;
    private MockMvc mockMvc;

    private MockHttpSession session;


    @Before
    public void before()  throws Exception{

        mockMvc = MockMvcBuilders.standaloneSetup(loginController).addFilter((Filter) SpringContextUtils.getBean("shiroFilter")).build();
        this.session = doLogin();
    }

    @Test
    public void getAuthentication(){
        System.out.println(loginController.authentication());
    }

    private MockHttpSession doLogin() throws Exception{
        ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders.post("/api/login")
                .param("username","admin").param("password","admin"));
        resultActions.andExpect(MockMvcResultMatchers.status().isOk());
        MvcResult result = resultActions.andReturn();
        session = (MockHttpSession) result.getRequest().getSession();
        return session;
    }

}
