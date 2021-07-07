package com.mzdx.service.provider;

import com.mzdx.pojo.Provider;

import java.util.List;

public interface ProviderService {
/**
	 * 增加供应商
	 */
	public boolean add(Provider provider);
	/**
	 * 通过供应商名称、编码获取供应商列表-模糊查询-providerList
	 */
	public List<Provider> getProviderList(String proName,String proCode,int currentPageNo,int pageSize);
	/**
	 * 通过proId删除Provider
	 */
	public int deleteProviderById(String delId);
	/**
	 * 通过proId获取Provider
	 */
	public Provider getProviderById(String id);
	
	/**
	 * 修改用户信息
	 */
	public boolean modify(Provider provider);
	/**
	 * 添加到回收站
	 */
	public boolean addToRecycle(Provider provider);
	/**
	 * 通过供应商名称、编码获取回收站的供应商列表-模糊查询
	 */
	public List<Provider> getRecycleList(String proName,String proCode,int currentPageNo,int pageSize);
	/**
	 * 根据Id删除回收站里面的数据
	 */
	public int deleteRecycleById(String id);
	/**
	 * 回收站通过proId获取Provider
	 */
	public Provider getRecycleById(String id);
	/**
	 *批量删除回收站
	 */
	public void delSelectedByIds(String[] ids);
	/**
	*查询供应商数目
	 */
	public int getProviderCount(String proName,String proCode);
	/**
	 * 查询回收站数目
	 */
	public int getRecycleCount(String proName,String proCode);
}
