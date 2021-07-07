package com.mzdx.service.user;

import java.util.List;

import com.mzdx.pojo.Role;
import com.mzdx.pojo.User;

public interface UserService {
	
	
	/**
	 * 用户登录
	 * @param userCode
	 * @param userPassword
	 * @return
	 */
	public User getLoginUser(String userCode,String userPassword);
	
	/**
	 * 根据条件查询用户列表
	 * @param queryUserName
	 * @param queryUserRole
	 * @return
	 */
	public int updatePwd(int id,String pwd);
//获取所有用户列表
	//public  List<User> findAll();
	/**
	 * 增加用户信息
	 * @param user
	 * @return
	 */
	public boolean add(User user);
	
	/**
	 * 用户登录
	 * @param userCode
	 * @param userPassword
	 * @return
	 */
	//public User login(String userCode, String userPassword);

	/**
	 * 根据条件查询用户列表
	 * @param queryUserName
	 * @param queryUserRole
	 * @return
	 */
	public List<User> getUserList(String queryUserName,String queryUserRole,int currentPageNo, int pageSize);
	/**
	 * 根据条件查询用户表记录数
	 * @param queryUserName
	 * @param queryUserRole
	 * @return
	 */
	public int getUserCount(String queryUserName,String queryUserRole);

	/**
	 * 根据userCode查询出User
	 * @param userCode
	 * @return
	 */
	public User selectUserCodeExist(String userCode);

	/**
	 * 根据ID删除user
	 * @param delId
	 * @return
	 */
	public boolean deleteUserById(Integer delId);

	/**
	 * 根据ID查找user
	 * @param id
	 * @return
	 */
	public User getUserById(String id);

	/**
	 * 修改用户信息
	 * @param user
	 * @return
	 */
	public boolean modify(User user);

	/**
	 * 根据userId修改密码
	 * @param id
	 * @param pwd
	 * @return
	 */
	//获取角色列表
	public List<Role> getRoleList();

	public int getAllUserCount();
	//
	List<User> queryUserList(String queryUserName);
//回收站
	boolean isdelete(User user);

	List<User> getIsdelete();

	boolean deleteALlways(Integer delId);
}
