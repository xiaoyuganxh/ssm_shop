package com.mzdx.service.bill;

import com.mzdx.pojo.Bill;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface BillService {
    /**
     * 增加订单
     * @param bill
     * @return
     */
    public int add(Bill bill);


    /**
     * 通过条件获取订单列表-模糊查询-billList
     * @param bill
     * @return
     */
    public List<Bill> getBillList(Bill bill, String startday, String endday,int currentPageNo, int pageSize);

    public int billCount(Bill bill, String startday, String endday);

    /**
     * 通过billId删除Bill
     * @param delId
     * @return
     */
    public int deleteBillById(Integer delId);


    /**
     * 通过billId获取Bill
     * @param id
     * @return
     */
    public Bill getBillById(Integer id);

    /**
     * 修改订单信息
     * @param bill
     * @return
     */
    public int modify(Bill bill);
    List<Bill> getBillByIds(String[] billStr);

    SXSSFWorkbook getSimpleExcel(String billList);

    SXSSFWorkbook getBillExcel(List<Bill> bills);

    Boolean billExcelInput(MultipartFile excelInput,String excelType) throws IOException;

}
