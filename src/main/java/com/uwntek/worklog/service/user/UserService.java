package com.uwntek.worklog.service.user;

import com.uwntek.worklog.dao.user.UserDAO;
import com.uwntek.worklog.dto.UserDTO;
import com.uwntek.worklog.dto.UserInfo;
import com.uwntek.worklog.entity.user.User;
import com.uwntek.worklog.util.ObjectMapperUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
public class UserService {
    @Autowired
    private UserDAO userDAO;
    @Autowired
    DeptService deptService;
    @Autowired
    PositionService positionService;


    public List<UserDTO> list() {
        List<User> users = userDAO.findAllByIsEffective(1);

        List<UserDTO> userDTOList = ObjectMapperUtils.mapAll(users, UserDTO.class);
        for(UserDTO userDTO: userDTOList){
            userDTO.setDeptName(deptService.get(userDTO.getDept()).getDeptName());
            userDTO.setPositionName(positionService.getPosition(userDTO.getPosition()).getPositionName());
        }

        return userDTOList;
    }

    public List<UserDTO> listByUserName(String userName, int isEffective) {
        List<User> users = userDAO.findAllByUserNameAndIsEffective(userName, isEffective);

        List<UserDTO> userDTOList = ObjectMapperUtils.mapAll(users, UserDTO.class);
        for(UserDTO userDTO: userDTOList){
            userDTO.setDeptName(deptService.get(userDTO.getDept()).getDeptName());
            userDTO.setPositionName(positionService.getPosition(userDTO.getPosition()).getPositionName());
        }

        return userDTOList;
    }

    public List<UserDTO> listByDept(int dept, int isEffective) {
        List<User> users = userDAO.findByDeptAndIsEffective(dept, isEffective);

        List<UserDTO> userDTOList = ObjectMapperUtils.mapAll(users, UserDTO.class);
        for(UserDTO userDTO: userDTOList){
            userDTO.setDeptName(deptService.get(userDTO.getDept()).getDeptName());
            userDTO.setPositionName(positionService.getPosition(userDTO.getPosition()).getPositionName());
        }

        return userDTOList;
    }

    public boolean isExist(String userName) {
        User user = userDAO.findByUserName(userName);
        return null != user;
    }

    public boolean positionIsExist(int position) {
        return userDAO.existsByPosition(position);
    }

    public boolean idIsExist(Long id) {
        return userDAO.existsById(id);
    }

    public User findByUserName(String userName) {
        return userDAO.findByUserName(userName);
    }

    public User getByUserName(String userName, String password) {
        return userDAO.findByUserNameAndPassword(userName, password);
    }

    public void addOrUpdateUser(User user) {
        userDAO.save(user);
    }

    public User get(Long id) {
        return userDAO.findById(id).orElse(null);
    }

    public UserInfo getUserInfo(Long id){
        return ObjectMapperUtils.map(userDAO.findById(id).orElse(new User()),UserInfo.class);
    }

}
