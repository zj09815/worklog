package com.uwntek.worklog.service.experience;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.uwntek.worklog.dao.experience.ExpTagLinkDAO;
import com.uwntek.worklog.dao.experience.ExperienceDAO;
import com.uwntek.worklog.dto.ExpSerchParams;
import com.uwntek.worklog.entity.experience.ExpTagLink;
import com.uwntek.worklog.entity.experience.Experience;

import com.uwntek.worklog.entity.user.User;
import com.uwntek.worklog.service.user.UserService;
import com.uwntek.worklog.util.LongJsonDeserializer;
import com.uwntek.worklog.util.LongJsonSerializer;
import com.uwntek.worklog.util.MyPage;
import lombok.Data;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
public class ExperienceService {
    @Autowired
    ExperienceDAO experienceDAO;
    @Autowired
    UserService userService;

    @Autowired
    ExpTagLinkDAO expTagLinkDAO;
    @Autowired
    ClassifyService classifyService;



    public Experience addOrUpdate(Experience experience) {
        experienceDAO.save(experience);
        return experience;
    }

    public void delete(Long id) {
        Experience experience = findById(id);
        experience.setIsEffective(0);
        experienceDAO.save(experience);
    }

    public Experience findById(Long id) {
        Experience experience;
        experience = experienceDAO.findById(id).orElse(null);
        return experience;
    }



    public boolean isExist(Long id) {
        return experienceDAO.existsById(id);
    }

    public List<Experience> findAll(){
        return experienceDAO.findAllByIsEffective(1);
    }


    public Page<Experience> findAllByTerms(ExpSerchParams expSerchParams, int pagenum) {
        Pageable pageable = PageRequest.of(pagenum, 20, Sort.by(Sort.Direction.DESC,"updateTime"));
        Page<Experience> experiences = experienceDAO.findAll(new Specification<Experience>() {
            @Override
            public Predicate toPredicate(Root<Experience> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
               //                查询结果必须是有效的
                predicates.add(criteriaBuilder.equal(root.get("isEffective").as(Integer.class), 1));
//                查询文章内容
                if (expSerchParams.getSearchString() != null) {
                    Predicate predicateTitle = criteriaBuilder.like(root.get("expTitle").as(String.class), "%" + expSerchParams.getSearchString() + "%");
                    Predicate predicateIndex = criteriaBuilder.like(root.get("expIndex").as(String.class), "%" + expSerchParams.getSearchString() + "%");
                    Predicate predicateContent = criteriaBuilder.like(root.get("expContent").as(String.class), "%" + expSerchParams.getSearchString() + "%");
                    predicates.add(criteriaBuilder.or(predicateTitle,predicateIndex,predicateContent));
                }
//                查询审核状态
                if (expSerchParams.getVerifyStatus()!= null) {
                    Path<Object> path = root.get("verifyStatus");//定义查询的字段
                    CriteriaBuilder.In<Object> in = criteriaBuilder.in(path);
                    for (String status:expSerchParams.getVerifyStatus()) {
                        in.value(status);//存入值
                    }
                    predicates.add(criteriaBuilder.and(criteriaBuilder.and(in)));//存入条件集合里

                }

//                查询分类
                if (expSerchParams.getClassifyId() != null) {
                    Path<Object> path = root.get("classifyId");//定义查询的字段
                    CriteriaBuilder.In<Object> in = criteriaBuilder.in(path);
                    for (Long classifyId:expSerchParams.getClassifyId()) {
                        in.value(classifyId);//存入值
                    }
                    predicates.add(criteriaBuilder.and(in));//存入条件集合里

                }
//                查询用户
                if(expSerchParams.getUserId() != null){
                    Path<Object> path = root.get("createUser").get("id");//定义查询的字段
                    CriteriaBuilder.In<Object> in = criteriaBuilder.in(path);
                    for (Long userId:expSerchParams.getUserId()) {
                        in.value(userId);//存入值
                    }
                    predicates.add(criteriaBuilder.and(in));//存入条件集合里

                }
//                查询标签
                if(expSerchParams.getTagId() != null){
                    Root<ExpTagLink> linkRoot = criteriaQuery.from(ExpTagLink.class);
                    predicates.add(criteriaBuilder.equal(root.get("id"), linkRoot.get("expId")));
                    Path<Object> path = linkRoot.get("tagId");
                    CriteriaBuilder.In<Object> in = criteriaBuilder.in(path);
                    for (Long tagId: expSerchParams.getTagId()) {
                        in.value(tagId);//存入值
                    }
                    predicates.add(criteriaBuilder.and(in));
                    criteriaQuery.distinct(true);
                }

                Predicate[] pre = new Predicate[predicates.size()];
                criteriaQuery.where(predicates.toArray(pre));
                return criteriaBuilder.and(predicates.toArray(pre));
            }
        }, pageable);
        for (Experience experience:experiences.getContent()){
            if (experience.getClassifyId()!=null) {
                if (classifyService.findByid(experience.getClassifyId()).getParentId() == 0) {
                    experience.setExpClassify(classifyService.findByid(experience.getClassifyId()).getClassifyName());
                } else {
                    experience.setExpClassify(classifyService.findByid(classifyService.findByid(experience.getClassifyId()).getParentId()).getClassifyName() + "/" + classifyService.findByid(experience.getClassifyId()).getClassifyName());
                }
            }
        }
        return experiences;
    }
}


