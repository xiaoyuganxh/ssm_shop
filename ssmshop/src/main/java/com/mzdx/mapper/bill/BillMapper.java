package com.mzdx.mapper.bill;

import com.mzdx.pojo.Bill;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BillMapper {
    public int add(Bill bill);

    public List<Bill> getBillList(@Param("bill") Bill bill,@Param("startday") String startday,@Param("endday") String endday, @Param("currentPageNo") int currentPageNo, @Param("pageSize") int pageSize);

    public int deleteBillById(Integer delId);

    public Bill getBillById(Integer id);

    public int modify(Bill bill);

    public int billCount(@Param("bill") Bill bill,@Param("startday") String startday,@Param("endday") String endday);
    List<Bill> getBillByIds(String[] billStr);

    int addBillBatch(List<Bill> bills);
 public int getBillCountByProviderId(String providerId);
}
