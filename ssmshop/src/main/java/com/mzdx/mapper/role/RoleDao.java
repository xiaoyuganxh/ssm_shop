package com.mzdx.mapper.role;

import java.sql.Connection;
import java.util.List;
import com.mzdx.pojo.Role;

public interface RoleDao {
	
	public List<Role> getRoleList(Connection connection)throws Exception;

}
