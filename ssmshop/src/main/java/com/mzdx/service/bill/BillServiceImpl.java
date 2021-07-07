package com.mzdx.service.bill;

import com.alibaba.fastjson.JSONArray;
import com.mzdx.mapper.bill.BillMapper;
import com.mzdx.pojo.Bill;
import com.mzdx.pojo.BillExcel;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class BillServiceImpl implements BillService {
    @Autowired
    private BillMapper billMapper;

    @Override
    public int add(Bill bill) {
        try{
            int i = billMapper.add(bill);
            return i;
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public List<Bill> getBillList(Bill bill, String startday, String endday, int currentPageNo, int pageSize) {
        try{
            currentPageNo = currentPageNo*pageSize;
            List<Bill> billList = billMapper.getBillList(bill, startday, endday, currentPageNo, pageSize);
            return billList;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public int billCount(Bill bill, String startday, String endday){
        try {
            int totalCount = billMapper.billCount(bill, startday, endday);
            return totalCount;
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int deleteBillById(Integer delId) {
        try {
            int i = billMapper.deleteBillById(delId);
            return i;
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public Bill getBillById(Integer id) {
        try{
            Bill bill = billMapper.getBillById(id);
            return bill;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public int modify(Bill bill) {
        try {
            int i = billMapper.modify(bill);
            return i;
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }

    }
    @Override
    public List<Bill> getBillByIds(String[] billStr) {
        return billMapper.getBillByIds(billStr);
    }

    @Override
    public SXSSFWorkbook getSimpleExcel(String billList) {
        //创建工作簿，SXSSFWorkbook支持大数据量的导出
        SXSSFWorkbook workbook =new SXSSFWorkbook(100);
        //创建sheet表
        Sheet sheet = workbook.createSheet("订单");
        List<BillExcel> bills= JSONArray.parseArray(billList,BillExcel.class);
        //创建标题
        Row rowTitle = sheet.createRow(0);
        Cell cellTitle=null;
        cellTitle = rowTitle.createCell(0);
        cellTitle.setCellValue("账单编码");
        cellTitle = rowTitle.createCell(1);
        cellTitle.setCellValue("商品名称");
        cellTitle = rowTitle.createCell(2);
        cellTitle.setCellValue("供应商名称");
        cellTitle = rowTitle.createCell(3);
        cellTitle.setCellValue("商品总额");
        cellTitle = rowTitle.createCell(4);
        cellTitle.setCellValue("是否支付");
        cellTitle = rowTitle.createCell(5);
        cellTitle.setCellValue("创建时间");
        for (int i = 0; i < bills.size(); i++) {
            sheet.autoSizeColumn(i);
            //创建行
            Row row = sheet.createRow(i+1);
            Cell cell =null;//创建单元格

            cell = row.createCell(0);
            cell.setCellValue(bills.get(i).getBillCode());
            cell = row.createCell(1);
            cell.setCellValue(bills.get(i).getProductName());
            cell = row.createCell(2);
            cell.setCellValue(bills.get(i).getProviderName());
            cell = row.createCell(3);
            cell.setCellValue(bills.get(i).getTotalPrice());
            cell = row.createCell(4);
            cell.setCellValue(bills.get(i).getIsPayment());
            cell = row.createCell(5);
            cell.setCellValue(bills.get(i).getCreationDate());
            int width=Math.max(15*256,Math.min(255*256,sheet.getColumnWidth(i)*12/10));
            sheet.setColumnWidth(i,width);
        }
        return workbook;
    }

    @Override
    public SXSSFWorkbook getBillExcel(List<Bill> bills) {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        //创建工作簿，SXSSFWorkbook支持大数据量的导出
        SXSSFWorkbook workbook = new SXSSFWorkbook();
        //创建sheet表
        Sheet sheet = workbook.createSheet("订单");

        //创建标题
        Row rowTitle = sheet.createRow(0);
        Cell cellTitle=null;

        cellTitle = rowTitle.createCell(0);
        cellTitle.setCellValue("账单编码");
        cellTitle = rowTitle.createCell(1);
        cellTitle.setCellValue("商品名称");
        cellTitle = rowTitle.createCell(2);
        cellTitle.setCellValue("商品单位");
        cellTitle = rowTitle.createCell(3);
        cellTitle.setCellValue("商品描述");
        cellTitle = rowTitle.createCell(4);
        cellTitle.setCellValue("供应商名称");
        cellTitle = rowTitle.createCell(5);
        cellTitle.setCellValue("供应商id");
        cellTitle = rowTitle.createCell(6);
        cellTitle.setCellValue("商品数量");
        cellTitle = rowTitle.createCell(7);
        cellTitle.setCellValue("商品总额");
        cellTitle = rowTitle.createCell(8);
        cellTitle.setCellValue("是否支付");
        cellTitle = rowTitle.createCell(9);
        cellTitle.setCellValue("创建者");
        cellTitle = rowTitle.createCell(10);
        cellTitle.setCellValue("创建时间");
        cellTitle = rowTitle.createCell(11);
        cellTitle.setCellValue("更新者");
        cellTitle = rowTitle.createCell(12);
        cellTitle.setCellValue("更新时间");

        for (int i = 0; i < bills.size(); i++) {
            sheet.autoSizeColumn(i);
            //创建行
            Row row = sheet.createRow(i+1);
            Cell cell =null;//创建单元格

            cell = row.createCell(0);
            cell.setCellValue(bills.get(i).getBillCode()==null?"":bills.get(i).getBillCode());
            cell = row.createCell(1);
            cell.setCellValue(bills.get(i).getProductName()==null?"":bills.get(i).getProductName());
            cell = row.createCell(2);
            cell.setCellValue(bills.get(i).getProductUnit()==null?"":bills.get(i).getProductUnit());
            cell = row.createCell(3);
            cell.setCellValue(bills.get(i).getProductDesc()==null?"":bills.get(i).getProductDesc());
            cell = row.createCell(4);
            cell.setCellValue(bills.get(i).getProviderName()==null?"":bills.get(i).getProviderName());
            cell = row.createCell(5);
            cell.setCellValue(bills.get(i).getProviderId()==null?"":bills.get(i).getProviderId().toString());
            cell = row.createCell(6);
            cell.setCellValue(bills.get(i).getProductCount()==null?"":bills.get(i).getProductCount().toString());
            cell = row.createCell(7);
            cell.setCellValue(bills.get(i).getTotalPrice()==null?"":bills.get(i).getTotalPrice().toString());
            cell = row.createCell(8);
            cell.setCellValue(bills.get(i).getIsPayment()==1?"未支付":"已支付");
            cell = row.createCell(9);
            cell.setCellValue(bills.get(i).getCreatedBy()==null?"":bills.get(i).getCreatedBy().toString());
            cell = row.createCell(10);
            cell.setCellValue(bills.get(i).getCreationDate()==null?"":sdf.format(bills.get(i).getCreationDate()));
            cell = row.createCell(11);
            cell.setCellValue(bills.get(i).getModifyBy()==null?"":bills.get(i).getModifyBy().toString());
            cell = row.createCell(12);
            cell.setCellValue(bills.get(i).getModifyDate()==null?"":sdf.format(bills.get(i).getModifyDate()));
            int width=Math.max(15*256,Math.min(255*256,sheet.getColumnWidth(i)*12/10));
            sheet.setColumnWidth(i,width);
        }
        return workbook;
    }

    @Override
    public Boolean billExcelInput(MultipartFile excelInput,String excelType) throws IOException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        /*先把文件下载到内存中再读
         * poi不能直接读前端传来的字节*/
//        HSSFWorkbook workbook=null;
/*        File file=new File("billExcel");
        FileInputStream fileInputStream=null;*/
        byte[] bytes=excelInput.getBytes();

/*        OutputStream outPut=new FileOutputStream(file);
        BufferedOutputStream bufferedOutput = new BufferedOutputStream(outPut);
        bufferedOutput.write(bytes);
        fileInputStream = new FileInputStream(file);
        file.deleteOnExit();   //删除文件
        excelInput=null;

        BufferedInputStream bis = new BufferedInputStream(fileInputStream);*/
//避免inputStream二次读取
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        /*为了兼容两种格式使用woorbook*/
        Workbook workbook=null;
        if (excelType.equals(".xlsx")) {
            workbook = new XSSFWorkbook(byteArrayInputStream);
        }
        if (excelType.equals(".xls")) {
            workbook = new HSSFWorkbook(byteArrayInputStream);
        }
        //创建工作簿
//        workbook= new HSSFWorkbook(bis);//注意07的版本使用这个
        //按索引读取sheet表
        Sheet sheet = workbook.getSheetAt(0);
        //取得所有的行数
        int rowNum = sheet.getLastRowNum();
        List<Bill> bills=new ArrayList<>();
        for (int i = 1; i <= rowNum; i++) {
            //读取1行
            Row row = sheet.getRow(i);
            Cell cell=null;
            //读取每行的列数
            //short cellNum = row.getLastCellNum();

            Bill bill=new Bill();


            cell= row.getCell(0);
            bill.setBillCode(getCellValue( cell).equals("")?null:getCellValue( cell));
            cell= row.getCell(1);
            bill.setProductName(getCellValue( cell).equals("")?null:getCellValue( cell));
            cell= row.getCell(2);
            bill.setProductUnit(getCellValue( cell).equals("")?null:getCellValue( cell));
            cell= row.getCell(3);
            bill.setProductDesc(getCellValue( cell).equals("")?null:getCellValue( cell));
            try {
                cell = row.getCell(5);
                bill.setProviderId(getCellValue(cell).equals("") ? null : Integer.parseInt(getCellValue(cell)));
                cell = row.getCell(6);
                bill.setProductCount(getCellValue(cell) .equals("") ? null : new BigDecimal(getCellValue(cell)).setScale(2, BigDecimal.ROUND_DOWN));
                cell = row.getCell(7);
                bill.setTotalPrice(getCellValue(cell) .equals("") ? null : new BigDecimal(getCellValue(cell)).setScale(2, BigDecimal.ROUND_DOWN));
                cell = row.getCell(8);
                bill.setIsPayment(getCellValue(cell) .equals("") ? null : getCellValue(cell).equals("已支付")?2:1);
                cell = row.getCell(9);
                bill.setCreatedBy(getCellValue(cell) .equals("") ? null : Integer.parseInt(getCellValue(cell)));
                cell = row.getCell(10);
                bill.setCreationDate(getCellValue(cell) .equals("") ? null :sdf.parse(getCellValue(cell)));
                cell = row.getCell(11);
                bill.setModifyBy(getCellValue(cell) .equals("") ? null : Integer.parseInt(getCellValue(cell)));
                cell = row.getCell(12);
                bill.setModifyDate(getCellValue(cell) .equals("") ? null : sdf.parse(getCellValue(cell)));
            }catch (ParseException e){
                e.printStackTrace();
            }

            bills.add(bill);
        }
        for (Bill b:bills
                ) {
            System.out.println(b);
        }
        int i= billMapper.addBillBatch(bills);
        if (i==bills.size()) {
            return true;
        }
        return false;
    }
    //excel 单元格格式
    private String getCellValue(Cell cell) {
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_NUMERIC: // 数字
                // 如果为时间格式的内容
                if (DateUtil.isCellDateFormatted(cell)) {
                    //注：format格式 yyyy-MM-dd
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    return sdf.format(cell.getDateCellValue());
                } else {
                    //有小数保留两位，位数不够自动忽略
                    return new DecimalFormat("#.##").format(cell.getNumericCellValue());
                }
            case Cell.CELL_TYPE_STRING: // 字符串
                return cell.getStringCellValue();
            case Cell.CELL_TYPE_BOOLEAN: // Boolean
                return cell.getBooleanCellValue() + "";
            case Cell.CELL_TYPE_FORMULA: // 公式
                return cell.getCellFormula() + "";
            case Cell.CELL_TYPE_BLANK: // 空值
                return "";
            case Cell.CELL_TYPE_ERROR: // 故障
                return "";
            default:
                return "";
        }
    }
}
