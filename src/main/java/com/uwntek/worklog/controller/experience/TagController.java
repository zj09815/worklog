package com.uwntek.worklog.controller.experience;

import com.uwntek.worklog.dto.ExpSerchParams;
import com.uwntek.worklog.entity.experience.ExpTagLink;
import com.uwntek.worklog.entity.experience.Experience;
import com.uwntek.worklog.entity.experience.Tag;
import com.uwntek.worklog.reult.Result;
import com.uwntek.worklog.reult.ResultFactory;
import com.uwntek.worklog.service.experience.ExpTagLinkService;
import com.uwntek.worklog.service.experience.ExperienceService;
import com.uwntek.worklog.service.experience.TagService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RestController
@RequestMapping
public class TagController {
    @Autowired
    TagService tagService;
    @Autowired
    ExpTagLinkService expTagLinkService;
    @Autowired
    ExperienceService experienceService;

    @GetMapping("api/tag/list")
    @ApiOperation("标签列表")
    public Result listTag() {
        return ResultFactory.buildSuccessResult(tagService.findAll());
    }

    @PostMapping("api/tag/add")
    @ApiOperation("新增或修改标签")
    public Result addOrUpdate(Long id, @RequestParam String tagName, @RequestParam Long parentId) {
        if (id == null) {
            Tag tag = new Tag();
            tag.setTagName(tagName);
            tag.setParentId(parentId);
            tag.setIsEffective(1);
            tagService.addOrUpdate(tag);
            return ResultFactory.buildSuccessResultWithoutData("更新成功");
        }
        else {
            Tag tag = tagService.findByid(id);
            tag.setTagName(tagName);
            tag.setParentId(parentId);
            tagService.addOrUpdate(tag);
            return ResultFactory.buildSuccessResultWithoutData("更新成功");
        }
    }

    @PostMapping("api/tag/delete")
    @ApiOperation("删除标签")
    public Result delete(@RequestParam Long id) {
        if (tagService.isExist(id)) {
            if (tagService.findByid(id).getParentId() != 0) {
                tagService.delete(id);
                List<ExpTagLink> expTagLinkList = expTagLinkService.getExpsByTag(id);
                for (ExpTagLink expTagLink : expTagLinkList) {
                    expTagLink.setIsEffective(0);
                    expTagLinkService.addOrUpdate(expTagLink);
                }

            } else {
                List<Tag> tagList = tagService.findAllByParentId(id);
                tagService.deleteByParentId(id);
                tagService.delete(id);

                for (Tag tag : tagList) {
                    List<ExpTagLink> expTagLinkList = expTagLinkService.getExpsByTag(tag.getId());
                    for (ExpTagLink expTagLink : expTagLinkList) {
                        expTagLink.setIsEffective(0);
                        expTagLinkService.addOrUpdate(expTagLink);
                    }
                }
            }
            return ResultFactory.buildSuccessResultWithoutData("删除成功");
        } else {
            return ResultFactory.buildFailResult("标签不存在");
        }
    }
}
