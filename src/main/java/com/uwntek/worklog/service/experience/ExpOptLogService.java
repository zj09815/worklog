package com.uwntek.worklog.service.experience;

import com.uwntek.worklog.dao.experience.ExpOptLogDAO;
import com.uwntek.worklog.entity.experience.ExpOptLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ExpOptLogService {
    @Autowired
    ExpOptLogDAO expOptLogDAO;

    public List<ExpOptLog> findAllByExpId(Long expId){
        return expOptLogDAO.findAllByExpId(expId,Sort.by(Sort.Direction.DESC, "optTime"));
    }

    public Page<ExpOptLog> findAll(int pagenum){
        Pageable pageable = PageRequest.of(pagenum, 20, Sort.by(Sort.Direction.DESC, "optTime"));
        return expOptLogDAO.findAll(pageable);
    }

    public void saveLog(ExpOptLog expOptLog){
        expOptLogDAO.save(expOptLog);
    }
}
