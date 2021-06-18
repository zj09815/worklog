package com.uwntek.worklog.controller.worklog;

import com.uwntek.worklog.config.HTTPAddress;
import com.uwntek.worklog.entity.worklog.FileUpdate;
import com.uwntek.worklog.reult.Result;
import com.uwntek.worklog.reult.ResultFactory;
import com.uwntek.worklog.service.user.DeptService;
import com.uwntek.worklog.service.worklog.FileUpdateService;
import com.uwntek.worklog.service.user.UserService;
import com.uwntek.worklog.util.StringUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping
public class FileUpdateController {
    @Autowired
    FileUpdateService fileUpdateService;
    @Autowired
    UserService userService;
    @Autowired
    DeptService deptService;

    String folder = "E:\\files";

    @PostMapping("api/admin/fileUpload")
    @ApiOperation(notes = "文件上传", value = "文件上传")
    public Result fileUpload(@RequestParam MultipartFile file, Long userid, Date month) {
        FileUpdate fileUpdate = new FileUpdate();
        File uploadFolder = new File(folder);
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        int begin = file.getOriginalFilename().indexOf(".") + 1;
        int last = file.getOriginalFilename().length();
        String fileClass = file.getOriginalFilename().substring(begin, last);

        File file1 = new File(uploadFolder,
                fileClass + "/" + df.format(new Date()) + StringUtils.getRandomString(10) + file.getOriginalFilename());
        if (!file1.getParentFile().exists())
            file1.getParentFile().mkdirs();
        try {
            file.transferTo(file1);
            String fileName = file1.getName();
            String fileURL = HTTPAddress.NeedAddress.getAddress() + ":8443/api/admin/file/" + fileClass + "/" + fileName;
            fileUpdate.setOriName(file.getOriginalFilename());
            fileUpdate.setIsEffective(1);
            fileUpdate.setUser(userService.get(userid));
            fileUpdate.setUser_fk(userid);
            fileUpdate.setMonth(month);
            fileUpdate.setName(fileName);
            fileUpdate.setUrl(fileURL);
            fileUpdateService.addOrUpdateFile(fileUpdate);
            return ResultFactory.buildSuccessResultWithoutData("文件" + file1.getName() + "上传成功");
        } catch (IOException e) {
            e.printStackTrace();
            return ResultFactory.buildFailResult("文件上传失败");
        }
    }

    @GetMapping("/api/admin/fileListByUser")
    @ApiOperation(value = "用户的所有文件")
    public Result fileListByBuser(Long userid) {
        if (!userService.idIsExist(userid)) {
            return ResultFactory.buildFailResult("用户不存在");
        } else {
            List<FileUpdate> listFileUpdate = fileUpdateService.findAllByUser(userid, 1);
            return ResultFactory.buildSuccessResult(listFileUpdate);
        }
    }

    @GetMapping("/api/admin/fileListByUserAndMonth")
    @ApiOperation(value = "按用户和月份筛选文件")
    public Result fileListByBuserAndMonth(@RequestParam Long userid, Date month) {
        if (!userService.idIsExist(userid)) {
            return ResultFactory.buildFailResult("用户不存在");
        } else {
            List<FileUpdate> listFileUpdate = fileUpdateService.findAllByUserAndMonth(userid, month, 1);
            return ResultFactory.buildSuccessResult(listFileUpdate);
        }
    }

    @GetMapping("/api/admin/fileListByDeptAndMonth")
    @ApiOperation("按部门和月份筛选文件")
    public Result fileListByDeptAndMonth(int deptid, Date month) {
        if (!deptService.idIsExist(deptid)) {
            return ResultFactory.buildFailResult("部门不存在");
        } else {
            List<FileUpdate> fileUpdateList = fileUpdateService.findAllByDeptAdnMonth(deptid, month, 1);
            return ResultFactory.buildSuccessResult(fileUpdateList);
        }
    }

    @PostMapping("/api/admin/fileDelete/{id}")
    @ApiOperation(value = "删除文件")
    public Result fileDelete(@PathVariable("id") Long id) {
        FileUpdate fileUpdate = fileUpdateService.findById(id);
        fileUpdate.setIsEffective(0);
        fileUpdateService.addOrUpdateFile(fileUpdate);
        return ResultFactory.buildSuccessResultWithoutData("删除成功");
    }

}
