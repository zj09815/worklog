package com.uwntek.worklog.service.user;

import com.uwntek.worklog.entity.user.Menu;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class MenuServiceTest {
    @Autowired
    MenuService menuService;


    @Test
    void findAllByParentId() {
        List<Menu> allByParentId = menuService.findAllByParentId(0);
        System.out.println(allByParentId.toString());
    }

    @Test
    void getMenusByRoleId(){
        List<Menu> menus = menuService.getMenusByRoleId(1);
        System.out.println(menus.toString());
    }
}