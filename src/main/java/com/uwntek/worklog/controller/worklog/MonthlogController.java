package com.uwntek.worklog.controller.worklog;

import com.uwntek.worklog.entity.worklog.Monthlog;
import com.uwntek.worklog.reult.Result;
import com.uwntek.worklog.reult.ResultFactory;
import com.uwntek.worklog.service.user.DeptService;
import com.uwntek.worklog.service.worklog.MonthlogService;
import com.uwntek.worklog.service.user.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping
@Api(value = "月度总结")
public class MonthlogController {
    @Autowired
    MonthlogService monthlogService;
    @Autowired
    UserService userService;
    @Autowired
    DeptService deptService;

    @GetMapping("/api/admin/monthlog/user/{userid}")
    @ApiOperation(notes = "月度总结", value = "月度总结")
    public Result getByUser(@PathVariable("userid") Long userid, Date month) throws Exception {
        Monthlog monthlog = monthlogService.getByUser(userid, month);
        return ResultFactory.buildSuccessResult(monthlog);
    }

    @GetMapping("/api/admin/monthlog/dept/{deptid}")
    @ApiOperation(notes = "按部门查询月度总结", value = "按部门查询月度总结")
    public Result getByDept(@PathVariable("deptid") int deptid, Date month) throws Exception {
        List<Monthlog> monthlogList = monthlogService.findByDept(deptid,month);
        return ResultFactory.buildSuccessResult(monthlogList);
    }

    @PostMapping("/api/admin/addOrUpdateMonthlog")
    @ApiOperation(notes = "新增或修改月度总结", value = "新增或修改月度总结")
    public Result addOrUpdateMonthlog(@RequestBody Monthlog monthlog) throws Exception {
        if (!userService.idIsExist(monthlog.getUser_fk())) {
            return ResultFactory.buildFailResult("用户不存在");
        } else if (!deptService.idIsExist(monthlog.getDept_fk())) {
            return ResultFactory.buildFailResult("部门不存在");
        } else {
            monthlogService.addOrUpdateMonthlog(monthlog);
            return ResultFactory.buildSuccessResultWithoutData("新增或修改成功");
        }
    }
/*
    @GetMapping("api/admin/exportExcel/{deptid}")
    @ApiOperation("导出为Excel")
    public void export(@PathVariable("deptid") int deptid, Date month){
        ExcelWriter excelWriter = null;
        List<Monthlog> monthlogs = monthlogService.findByDept(deptid,month);
        List<MonthlogExport> monthlogExports = new ArrayList<>();
        for (Monthlog monthlog : monthlogs){
            MonthlogExport monthlogExport = new MonthlogExport();
            monthlogExport.setMonthlog1(monthlog.getMonthlog1());
            monthlogExport.setMonthlog2(monthlog.getMonthlog2());
            monthlogExport.setMonthlog3(monthlog.getMonthlog3());
            monthlogExport.setMonthlog4(monthlog.getMonthlog4());
            monthlogExport.setMonthlogconclusion(monthlog.getMonthlogconclusion());
            monthlogExport.setMonthlogplan(monthlog.getMonthlogplan());
            monthlogExport.setUser(monthlog.getCreateUserName());
            monthlogExports.add(monthlogExport);
        }
        String templateFileName = "/Users/zhangjin/IdeaProjects/worklog/src/main/resources/monthlogsample.xlsx";
        String fileName = "/Users/zhangjin/"+System.currentTimeMillis()+".xlsx";
        File file = new File(templateFileName);
        try {
            excelWriter = EasyExcel.write(fileName).withTemplate(templateFileName).build();
            for (int i=0; i<monthlogs.size();i++) {
                MonthlogExport monthlogExport = monthlogExports.get(i);
                POIExcelUtil.cloneSheet(file,0,monthlogExport.getUser());
                WriteSheet writeSheet = EasyExcel.writerSheet(i,monthlogExport.getUser()).build();
                excelWriter.fill(monthlogExport,writeSheet);
            }

        }finally {
            if (excelWriter != null) {
                excelWriter.finish();
            }
        }
    }

 */
}
