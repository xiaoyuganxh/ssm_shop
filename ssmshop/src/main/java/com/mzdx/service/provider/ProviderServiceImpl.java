package com.mzdx.service.provider;

import com.mzdx.mapper.bill.BillMapper;
import com.mzdx.mapper.provider.ProviderMapper;
import com.mzdx.pojo.Provider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProviderServiceImpl implements ProviderService {
    @Autowired
    private ProviderMapper providerMapper;
    @Autowired
    private BillMapper billMapper;

    //添加
    @Override
    public boolean add(Provider provider) {
        boolean flag = false;
        if (providerMapper.add(provider) > 0) {
            flag = true;
        }
        return flag;
    }

    //查询列表
    @Override
    public List<Provider> getProviderList(String proName, String proCode, int currentPageNo, int pageSize) {
        List<Provider> providerList;
        currentPageNo = currentPageNo * pageSize;
        providerList = providerMapper.getProviderList(proName, proCode, currentPageNo, pageSize);
        return providerList;
    }

    /**
     * 业务：根据ID删除供应商表的数据之前，需要先去订单表里进行查询操作
     * 若订单表中无该供应商的订单数据，则可以删除
     * 若有该供应商的订单数据，则不可以删除
     * 返回值billCount
     * 1> billCount == 0  删除---1 成功 （0） 2 不成功 （-1）
     * 2> billCount > 0    不能删除 查询成功（0）查询不成功（-1）
     * <p>
     * ---判断
     * 如果billCount = -1 失败
     * 若billCount >= 0 成功
     */
    //根据Id删除
    @Override
    public int deleteProviderById(String delId) {
        int billCount = -1;
        try {
            billCount = billMapper.getBillCountByProviderId(delId);
            if (billCount == 0) {
                providerMapper.deleteProviderById(delId);
            }
        } catch (Exception e) {
            e.printStackTrace();
            billCount = -1;
        }
        return billCount;
    }

    //根据Id查询
    @Override
    public Provider getProviderById(String id) {
        Provider provider = providerMapper.getProviderById(id);
        return provider;
    }

    //修改
    @Override
    public boolean modify(Provider provider) {
        boolean flag = false;
        if (providerMapper.modify(provider) > 0) {
            flag = true;
        }
        return flag;
    }

    //添加回收站
    @Override
    public boolean addToRecycle(Provider provider) {
        boolean flag = false;
        if (providerMapper.addToRecycle(provider) > 0) {
            flag = true;
        }
        return flag;
    }

    //查询回收站
    @Override
    public List<Provider> getRecycleList(String proName, String proCode, int currentPageNo, int pageSize) {
        List<Provider> providerList;
        currentPageNo = currentPageNo * pageSize;
        providerList = providerMapper.getRecycleList(proName, proCode, currentPageNo, pageSize);
        return providerList;
    }

    //回收站删除
    @Override
    public int deleteRecycleById(String reId) {
        int i = providerMapper.deleteRecycleById(reId);
        return i;
    }

    //回收站查看
    @Override
    public Provider getRecycleById(String id) {
        Provider provider = providerMapper.getRecycleById(id);
        return provider;
    }

    //批量删除
    @Override
    public void delSelectedByIds(String[] ids) {
        if (ids != null && ids.length > 0) {
            //遍历
            for (String id : ids) {
                //调用mapper删除
                providerMapper.deleteRecycleById(id);
            }
        }
    }

    //查询当前列表总数
    @Override
    public int getProviderCount(String proName, String proCode) {
        int count = providerMapper.getProviderCount(proName, proCode);
        return count;
    }

    @Override
    public int getRecycleCount(String proName, String proCode) {
        int count = providerMapper.getRecycleCount(proName, proCode);
        return count;
    }
}
