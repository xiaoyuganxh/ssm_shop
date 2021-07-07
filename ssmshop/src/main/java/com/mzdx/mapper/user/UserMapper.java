package com.mzdx.mapper.user;

import java.sql.Connection;
import java.util.List;

import com.mzdx.pojo.Role;
import com.mzdx.pojo.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {


	/**
	登录
	 */
	public User getLoginUser(@Param("userCode") String userCode, @Param("userPassword")String userPassword);
	/**
	 * 修改当前用户密码
	 */
	public int updatePwd(@Param("id") int id,@Param("userPassword") String password);
 //获取所有用户列表
    //List<User> findAll();
    //添加用户
    boolean add(User user);
    //修改用户信息
    boolean modify(User user);
    //根据id删除用户
    boolean deleteUserById(int id);
    //id查询用户信息
    User getUserById(String id);
    //根据userCode查询出User
    User selectUserCodeExist(String userCode);
    //根据条件查询用户列表
    List<User> getUserList(@Param("queryUserName") String queryUserName, @Param("queryUserRole") String queryUserRole,@Param("currentPageNo") int currentPageNo, @Param("pageSize") int pageSize);
    //根据条件查询用户表
    int getUserCount(@Param("queryUserName") String queryUserName, @Param("queryUserRole") String queryUserRole);
    //获取角色列表
    public List<Role> getRoleList();

    public int getAllUserCount();

    List<User> queryUserList(String queryUserName);

    boolean isdelete(User user);

    List<User> getIsdelete();

    boolean deleteALlways(int id);
	
	
}
