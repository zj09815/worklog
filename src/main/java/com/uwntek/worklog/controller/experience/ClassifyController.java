package com.uwntek.worklog.controller.experience;
import com.uwntek.worklog.entity.experience.Classify;
import com.uwntek.worklog.entity.experience.Experience;
import com.uwntek.worklog.reult.Result;
import com.uwntek.worklog.reult.ResultFactory;
import com.uwntek.worklog.service.experience.ClassifyService;
import com.uwntek.worklog.service.experience.ExperienceService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping
public class ClassifyController {
    @Autowired
    ClassifyService classifyService;
    @Autowired
    ExperienceService experienceService;

    @GetMapping("api/classify/list")
    @ApiOperation("标签列表")
    public Result listClassify() {
        return ResultFactory.buildSuccessResult(classifyService.findAll());
    }

    @PostMapping("api/classify/add")
    @ApiOperation("新增或修改标签")
    public Result addOrUpdate(Long id, @RequestParam String classifyName, @RequestParam Long parentId) {
        Classify classify;
        if (id == null) {
            classify = new Classify();
            classify.setClassifyName(classifyName);
            classify.setParentId(parentId);
            classify.setIsEffective(1);
        }
        else {
            classify = classifyService.findByid(id);
            classify.setClassifyName(classifyName);
            classify.setParentId(parentId);
        }
        classifyService.addOrUpdate(classify);
        return ResultFactory.buildSuccessResultWithoutData("更新成功");
    }

    @PostMapping("api/classify/delete")
    @ApiOperation("删除标签")
    public Result delete(@RequestParam Long id) {
        if (classifyService.isExist(id)) {
            if (classifyService.findByid(id).getParentId() != 0) {
                classifyService.delete(id);

            } else {
                classifyService.deleteByParentId(id);
                classifyService.delete(id);
            }
            return ResultFactory.buildSuccessResultWithoutData("删除成功");
        } else {
            return ResultFactory.buildFailResult("标签不存在");
        }
    }
}
