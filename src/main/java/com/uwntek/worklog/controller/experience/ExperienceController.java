package com.uwntek.worklog.controller.experience;

import cn.hutool.core.date.DateTime;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.uwntek.worklog.dto.ExpSerchParams;
import com.uwntek.worklog.entity.experience.ExpOptLog;
import com.uwntek.worklog.entity.experience.Experience;
import com.uwntek.worklog.entity.user.User;
import com.uwntek.worklog.reult.Result;
import com.uwntek.worklog.reult.ResultFactory;
import com.uwntek.worklog.service.experience.*;
import com.uwntek.worklog.service.user.UserService;
import com.uwntek.worklog.util.LongJsonDeserializer;
import com.uwntek.worklog.util.LongJsonSerializer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.UUID;

@RestController
@RequestMapping
@Api(value = "经验库")
public class ExperienceController {
    @Autowired
    ExperienceService experienceService;
    @Autowired
    UserService userService;
    @Autowired
    TagService tagService;
    @Autowired
    ExpTagLinkService expTagLinkService;
    @Autowired
    ClassifyService classifyService;
    @Autowired
    ExpOptLogService expOptLogService;

    @Getter
    @Setter
    @JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
    private static class ID{
        @JsonDeserialize(using = LongJsonDeserializer.class)
        @JsonSerialize(using = LongJsonSerializer.class)
        Long id;

        public ID() {
            this.id = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
        }
    }


    @GetMapping("api/uuid")
    @ApiOperation("获取uuid")
    public Result getUUID(){
        ID id = new ID();
        return ResultFactory.buildSuccessResult(id);
    }


    @GetMapping("api/experience")
    @ApiOperation("根据id查询经验库")
    public Result findById(@RequestParam Long id) throws Exception {
        if (experienceService.isExist(id)) {
            return ResultFactory.buildSuccessResult(experienceService.findById(id));
        } else {
            return ResultFactory.buildFailResult("该经验库不存在");
        }
    }



    @PostMapping("api/admin/experience/addOrUpdate")
    @ApiOperation(value = "新增或编辑经验库",notes = "新增时id设置为null,编辑时设置为对应id")
    public Result addOrUpdate(@RequestBody Experience experience, @RequestParam Long userid) throws Exception {
        Long finnalid;
        String optContent = "新增经验库: ";

        if (experience.getId() == null) {
            finnalid = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
        } else {
            if (experienceService.isExist(experience.getId())){
                optContent = "编辑经验库: ";
            }
            finnalid = experience.getId();
        }
        if (experience.getClassifyId() ==null){
            return ResultFactory.buildFailResult("分类id不能为空");
        }
        if (!classifyService.isExist(experience.getClassifyId())){
            return ResultFactory.buildFailResult("分类id不存在");
        }
        experience.setId(finnalid);
        User user = userService.get(experience.getCreateUser().getId());
        experience.setCreateUserName(user.getUserName());
        experience.setCreateUserNameZh(user.getUserNameZh());
        experience.setIsEffective(1);
        experience.setUpdateTime(DateTime.now());
        if (classifyService.findByid(experience.getClassifyId()).getParentId()==0){
            experience.setExpClassify(classifyService.findByid(experience.getClassifyId()).getClassifyName());
        }else {
            experience.setExpClassify(classifyService.findByid(classifyService.findByid(experience.getClassifyId()).getParentId()).getClassifyName() + "/" + classifyService.findByid(experience.getClassifyId()).getClassifyName());
        }
        experienceService.addOrUpdate(experience);
        expOptLogService.saveLog(new ExpOptLog(experience.getId(),userid,userService.get(userid).getUserNameZh(),optContent+"\""+experience.getExpTitle()+"\""));
        return ResultFactory.buildSuccessResult(finnalid.toString());
    }

    @PostMapping("api/admin/experience/delete")
    @ApiOperation("删除经验库")
    public Result delete(@RequestParam Long id, @RequestParam Long userid) {
        if(userService.idIsExist(userid)) {
            experienceService.delete(id);
            expOptLogService.saveLog(new ExpOptLog(id, userid, userService.get(userid).getUserNameZh(), "删除经验库: " + "\"" + experienceService.findById(id).getExpTitle() + "\""));
        }else {
            return ResultFactory.buildFailResult("用户不存在");
        }
        return ResultFactory.buildSuccessResultWithoutData("成功");
    }



    @PostMapping("/api/experience/list/v2")
    @ApiOperation("新的查询方式")
    public Result getExpList2(@RequestBody ExpSerchParams expSerchParams, @RequestParam int pagenum){
        return ResultFactory.buildSuccessResult(experienceService.findAllByTerms(expSerchParams,pagenum));
    }



}
