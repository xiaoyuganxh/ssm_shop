package com.mzdx.mapper.provider;

import com.mzdx.pojo.Provider;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProviderMapper {
 //增加供应商
    public int add(Provider provider);
    //根据编码和供应商名字模糊查询
    public List<Provider> getProviderList(@Param("proName") String proName,@Param("proCode") String proCode,
                                          @Param("currentPageNo") int currentPageNo, @Param("pageSize") int pageSize);
    //根据供应商id删除
    public void deleteProviderById(String delId);
    //根据供应商id获取供应商
    public Provider getProviderById(String id);
    //修改供应商信息
    public int modify(Provider provider);
    //将数据添加到回收站
    public int addToRecycle(Provider provider);
    //查询回收站
    public List<Provider> getRecycleList(@Param("proName") String proName, @Param("proCode") String proCode,
                                         @Param("currentPageNo") int currentPageNo, @Param("pageSize") int pageSize);
    //删除回收站
    public int deleteRecycleById(String reId);
    //根据id查询 返回一个provider
    public Provider getRecycleById(String id);
    //查询总数目
    public int getProviderCount(@Param("proName") String proName,@Param("proCode") String proCode);
    //查询回收站数目
    public int getRecycleCount(@Param("proName") String proName,@Param("proCode") String proCode);
}
