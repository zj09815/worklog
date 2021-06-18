package com.uwntek.worklog.controller.experience;

import com.uwntek.worklog.entity.experience.ExpOptLog;
import com.uwntek.worklog.entity.experience.ExpTagLink;
import com.uwntek.worklog.entity.experience.Tag;
import com.uwntek.worklog.reult.Result;
import com.uwntek.worklog.reult.ResultFactory;
import com.uwntek.worklog.service.experience.ExpOptLogService;
import com.uwntek.worklog.service.experience.ExpTagLinkService;
import com.uwntek.worklog.service.experience.ExperienceService;
import com.uwntek.worklog.service.experience.TagService;
import com.uwntek.worklog.service.user.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping
public class ExpTagController {
    @Autowired
    ExpTagLinkService expTagLinkService;
    @Autowired
    TagService tagService;
    @Autowired
    ExperienceService experienceService;
    @Autowired
    ExpOptLogService expOptLogService;
    @Autowired
    UserService userService;

    @GetMapping("/api/tagbyexp/list")
    @ApiOperation("文章对应的Tag")
    public Result getTagByExp(@RequestParam Long expid) throws Exception {
        if (experienceService.isExist(expid)) {
            List<Tag> tagList = new ArrayList<>();
            List<ExpTagLink> expTagLinkList = expTagLinkService.getTagsByExp(expid);
            for (ExpTagLink expTagLink : expTagLinkList) {
                tagList.add(tagService.findByid(expTagLink.getTagId()));
            }
            return ResultFactory.buildSuccessResult(tagList);
        } else {
            return ResultFactory.buildFailResult("该文章不存在");
        }
    }

    @PostMapping("/api/addTagToExp")
    @ApiOperation("向文章添加标签")
    public Result addTagToExp(@RequestParam Long expId, @RequestParam List<Long> tagIds, @RequestParam Long userid) throws Exception {
        if (userid == null || !userService.idIsExist(userid)){
            return ResultFactory.buildFailResult("用户不存在");
        }
        List<Result> resultList = new ArrayList<>();
            for (Long tagId : tagIds) {
                if (tagService.isExist(tagId)) {
                    if (expTagLinkService.isExist(expId, tagId, 1)) {
                        resultList.add(
                                ResultFactory.buildFailResult(tagService.findByid(tagId).getTagName() + ":链接关系已存在"));
                    } else {
                        ExpTagLink expTagLink = new ExpTagLink();
                        expTagLink.setTagId(tagId);
                        expTagLink.setExpId(expId);
                        expTagLink.setIsEffective(1);
                        expTagLinkService.addOrUpdate(expTagLink);
                        resultList.add(
                                ResultFactory.buildSuccessResult(tagService.findByid(tagId).getTagName() + ":添加成功"));
                    }
                } else {
                    resultList.add(ResultFactory.buildFailResult("标签不存在"));
                }
            }
            return ResultFactory.buildSuccessResult(resultList);

    }



    @PostMapping("/api/deleteTagtoExp")
    @ApiOperation("删除文章与指定标签的链接")
    public Result deleteTagToExp(@RequestParam Long expId, @RequestParam List<Long> tagIds, @RequestParam Long userid) throws Exception {
        List<Result> resultList = new ArrayList<>();
        for(Long tagId:tagIds) {
            if (!expTagLinkService.isExist(expId, tagId, 1)) {
                resultList.add( ResultFactory.buildFailResult("链接关系不存在"));
            } else {
                expTagLinkService.delete(expId, tagId, 1);
                resultList.add( ResultFactory.buildSuccessResultWithoutData("删除成功"));
            }
        }
        return ResultFactory.buildSuccessResult(resultList);
    }

}
