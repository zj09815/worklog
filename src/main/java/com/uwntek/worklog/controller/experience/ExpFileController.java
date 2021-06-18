package com.uwntek.worklog.controller.experience;

import com.uwntek.worklog.config.HTTPAddress;
import com.uwntek.worklog.entity.experience.ExpFile;
import com.uwntek.worklog.reult.Result;
import com.uwntek.worklog.reult.ResultFactory;
import com.uwntek.worklog.service.experience.ExpFileService;
import com.uwntek.worklog.service.experience.ExperienceService;
import com.uwntek.worklog.util.FileUtil;
import com.uwntek.worklog.util.StringUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping(name = "经验库文件")
public class ExpFileController {
    @Autowired
    ExpFileService expFileService;
    @Autowired
    ExperienceService experienceService;

    @GetMapping("/api/epxFile/{expId}")
    @ApiOperation("根据经验库id获取文件")
    public Result listFileByExp(Long expId) throws Exception {
        if (experienceService.isExist(expId)) {
            return ResultFactory.buildSuccessResult(expFileService.findAllByExpandIsEffective(expId, 1));
        } else {
            return ResultFactory.buildFailResult("经验库不存在");
        }
    }

    @PostMapping("/api/expFile/fileUpload")
    @ApiOperation(notes = "文件上传", value = "文件上传")
    public Result fileUpload(@RequestParam MultipartFile file){
        String folder = "E:\\files";
        ExpFile expFile = new ExpFile();
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
            // 本机
            String fileURL = HTTPAddress.NeedAddress.getAddress() + ":8443/api/admin/file/" + fileClass + "/" + fileName;
            return ResultFactory.buildSuccessResult(fileURL);
        } catch (IOException e) {
            e.printStackTrace();
            return ResultFactory.buildFailResult("文件上传失败");
        }
    }

    @PostMapping("/api/expPic/post")
    @ApiOperation("上传图片")
    public String uploadFile(@RequestParam("image") MultipartFile file, HttpServletRequest request) {
        // 上传的绝对路径
        String imgAbPath = "E:\\files\\Pic\\";
        // 绝对路径对应urlpath
        String imgUrlDir = HTTPAddress.NeedAddress.getAddress() + ":8443/api/admin/file/Pic/";
        // 返回对应的File类型f
        File f = FileUtil.upload(file, imgAbPath);

        // 返回图片地址
        return imgUrlDir + f.getName();

    }


}
