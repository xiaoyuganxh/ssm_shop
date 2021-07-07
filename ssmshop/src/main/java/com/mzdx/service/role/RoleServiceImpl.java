package com.mzdx.service.role;


import com.mzdx.mapper.BaseDao;
import com.mzdx.mapper.role.RoleDao;
import com.mzdx.mapper.role.RoleDaoImpl;
import com.mzdx.mapper.role.RoleMapper;
import com.mzdx.pojo.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.util.List;


@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleMapper roleMapper;


    @Override
    public List<Role> getRoleList() {
        // TODO Auto-generated method stub

        List<Role> roleList = null;

        roleList = roleMapper.getRoleList();

        return roleList;
    }

}
