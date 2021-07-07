package com.mzdx.service.user;


import com.alibaba.druid.util.StringUtils;
import com.mzdx.mapper.user.UserMapper;
import com.mzdx.pojo.Role;
import com.mzdx.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


/**
 * service层捕获异常，进行事务处理
 * 事务处理：调用不同dao的多个方法，必须使用同一个connection（connection作为参数传递）
 * 事务完成之后，需要在service层进行connection的关闭，在dao层关闭（PreparedStatement和ResultSet对象）
 * @author Administrator
 *
 */
@Service
public class UserServiceImpl implements UserService{
	@Autowired
	private UserMapper mapper;
    /*@Override
    public List<User> findAll() {
        return mapper.findAll();
    }*/
	@Override
	public User getLoginUser(String userCode, String userPassword) {
		User user = mapper.getLoginUser(userCode, userPassword);
		if (user != null){
			return user;
		}
		return null;
	}




@Override
    public boolean add(User user) {
        boolean add = mapper.add(user);
        return add;
    }





    @Override
    public int getAllUserCount(){
        return mapper.getAllUserCount();
    }

    @Override
    public List<User> getUserList(String queryUserName, String queryUserRole, int currentPageNo, int pageSize) {
        // TODO Auto-generated method stub
        List<User> userList = null;
        System.out.println("queryUserName ---- > " + queryUserName);
        System.out.println("queryUserRole ---- > " + queryUserRole);
        System.out.println("currentPageNo ---- > " + currentPageNo);
        System.out.println("pageSize ---- > " + pageSize);
        currentPageNo=currentPageNo*pageSize;
        userList = mapper.getUserList(queryUserName, queryUserRole, currentPageNo, pageSize);
        return userList;
    }


    @Override
    public int getUserCount(String queryUserName, String queryUserRole) {
        // TODO Auto-generated method stub

        int count = 0;
        System.out.println("queryUserName ---- > " + queryUserName);
        System.out.println("queryUserRole ---- > " + queryUserRole);
        count = mapper.getUserCount(queryUserName, queryUserRole);
        System.out.println("count"+count);
        return count;
    }

    @Override
    public User selectUserCodeExist(String userCode) {
        return mapper.selectUserCodeExist(userCode);
    }

    @Override
    public boolean deleteUserById(Integer delId) {
        return mapper.deleteUserById(delId);
    }

    @Override
    public User getUserById(String id) {
        User userById = mapper.getUserById(id);
        System.out.println(userById+"11111111111");
	    return userById;
    }

    @Override
    public boolean modify(User user) {
        return mapper.modify(user);
    }

    @Override
    public int updatePwd(int id, String pwd) {
        int updatePwd = mapper.updatePwd(id, pwd);
        return updatePwd;
    }

    @Override
    public List<Role> getRoleList() {
      //  List<Role> roleList = mapper.getRoleList();
       return mapper.getRoleList();
    }

    @Override
    public List<User> queryUserList(String queryUserName) {
        return mapper.queryUserList(queryUserName);
    }

    @Override
    public boolean isdelete(User user) {
        return mapper.isdelete(user);
    }

    @Override
    public List<User> getIsdelete() {
        return mapper.getIsdelete();
    }

    @Override
    public boolean deleteALlways(Integer delId) {
        return mapper.deleteALlways(delId);
    }
	


}
