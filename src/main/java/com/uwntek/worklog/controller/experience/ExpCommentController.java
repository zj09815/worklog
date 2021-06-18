package com.uwntek.worklog.controller.experience;

import com.uwntek.worklog.entity.experience.ExpComment;
import com.uwntek.worklog.reult.Result;
import com.uwntek.worklog.reult.ResultFactory;
import com.uwntek.worklog.service.experience.ExpCommentService;
import com.uwntek.worklog.service.experience.ExperienceService;
import com.uwntek.worklog.service.user.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping
@Api(value = "经验库评论")
public class ExpCommentController {
    @Autowired
    ExpCommentService expCommentService;
    @Autowired
    ExperienceService experienceService;
    @Autowired
    UserService userService;

    @GetMapping("/api/exp/comment")
    @ApiOperation("根据经验库id获取所有评论")
    public Result getCommentByExpId(@RequestParam Long expId) throws Exception {
        if (experienceService.isExist(expId)) {
            return ResultFactory.buildSuccessResult(expCommentService.findAllByCmtExp(expId, 1));
        } else {
            return ResultFactory.buildFailResult("经验库id不存在");
        }
    }

    @PostMapping("/api/exp/comment/add")
    @ApiOperation("发表评论")
    public Result add(@RequestParam Long expId, @RequestParam String cmtContent, @RequestParam Long userId,
                      @RequestParam Long parentId) throws Exception {
        ExpComment expComment = new ExpComment();
        expComment.setCmtContent(cmtContent);
        expComment.setCmtExp(expId);
        expComment.setCmtUser(userId);
        expComment.setCmtUserNameZh(userService.get(userId).getUserNameZh());
        expComment.setIsEffective(1);
        expComment.setParentId(parentId);
        Date date = new Date();
        expComment.setCreateTime(date);
        if (parentId == null) {
            return ResultFactory.buildFailResult("parentId不能为空");
        }
        expCommentService.addOrUpdateExpComment(expComment);
        return ResultFactory.buildSuccessResultWithoutData("success");
    }

    @PostMapping("/api/exp/comment/delete")
    @ApiOperation("删除评论")
    public Result delete(@RequestParam Long id) throws Exception {
        if (expCommentService.idIsExist(id)) {
            if (expCommentService.get(id).getParentId() == 0) {
                expCommentService.deleteExpCommentByParentId(id);
                expCommentService.deleteExpComment(id);
            } else {
                expCommentService.deleteExpComment(id);
            }
            return ResultFactory.buildSuccessResultWithoutData("删除成功");
        } else {
            return ResultFactory.buildFailResult("评论不存在");
        }
    }

}
