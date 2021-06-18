package com.uwntek.worklog.dao.experience;

import com.uwntek.worklog.entity.experience.Experience;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface ExperienceDAO extends JpaRepository<Experience, Long> {

    List<Experience> findAllByIsEffective(int isEffective);



    List<Experience> findAllByClassifyId(Long classifyId);




    Page<Experience> findAll(Specification<Experience> experienceSpecification, Pageable pageable);

}
