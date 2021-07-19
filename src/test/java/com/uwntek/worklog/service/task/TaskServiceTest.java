package com.uwntek.worklog.service.task;

import com.uwntek.worklog.controller.user.LoginController;
import com.uwntek.worklog.entity.task.Task;
import com.uwntek.worklog.service.BaseTest;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class TaskServiceTest extends BaseTest {
    @Autowired
    TaskService taskService;

    @Test
    void getTasksByCurrentUser() {
        List<Task> tasks = taskService.getTasksByCurrentUser(0);
        System.out.println(tasks.toString());

    }
}