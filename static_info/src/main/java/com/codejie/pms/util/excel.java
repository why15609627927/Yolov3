package com.codejie.pms.util;

import java.io.*;

import org.apache.poi.hssf.usermodel.HSSFRow;

import org.apache.poi.hssf.usermodel.HSSFSheet;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class excel {
    /*

     * author:命运的信徒

     * date:2019-07-31

     * arm:通过java程序往excel里面续写数据

     */
    private final static String Excel_2003 = ".xls"; //2003 版本的excel
    private final static String Excel_2007 = ".xlsx"; //2007 版本的excel

    public static Workbook getWorkbook(InputStream inStr, String fileName) throws Exception {
        Workbook work = null;
        String fileType = fileName.substring(fileName.lastIndexOf("."));
        if(Excel_2003.equals(fileType)){
            work=new HSSFWorkbook(inStr);//2003 版本的excel
        }else if(Excel_2007.equals(fileType) || "xlsm".equals(fileType)) {
            work=new XSSFWorkbook(inStr);//2007 版本的excel
        }else {
            work=new XSSFWorkbook(inStr);//2007 版本的excel
            //throw new Exception("解析文件格式有误！");
        }
        return work;
    }


    public static void main(String[] args) {
        String url="D:\\excel\\0182otcposdata20220228.xlsm";

        File  fs;

        try {
            fs = new File(url);

//            POIFSFileSystem ps=new POIFSFileSystem(fs); //使用POI提供的方法得到excel的信息
            InputStream ps = new FileInputStream(fs.getAbsolutePath());

            Workbook wb = null;
            try {
                wb = getWorkbook(ps,url);
            } catch (Exception e) {
                e.printStackTrace();
            }

            //HSSFWorkbook wb=new HSSFWorkbook(ps);

           // XSSFWorkbook wb=new XSSFWorkbook(String.valueOf(ps));

            //HSSFSheet sheet=wb.getSheetAt(0); //获取到工作表，因为一个excel可能有多个工作表
            Sheet sheet=wb.getSheetAt(0); //获取到工作表，因为一个excel可能有多个工作表

            //HSSFRow row=sheet.getRow(0);
            Row row=sheet.getRow(0);
            System.out.println(sheet.getPhysicalNumberOfRows());

            int hang=0;

            if("".equals(row)||row==null){
                hang=0;

            }else{
                hang=sheet.getLastRowNum();

                hang=hang+1;

            }

            //分别得到最后一行的行号，和一条记录的最后一个单元格

            FileOutputStream out=new FileOutputStream(url); //向d://test.xls中写数据

            row=sheet.createRow((short)(hang)); //在现有行号后追加数据

            row.createCell(0).setCellValue("安徽"); //设置第一个(从0开始)单元格的数据

            row.createCell(1).setCellValue("安庆"); //设置第二个(从0开始)单元格的数据

            out.flush();

            wb.write(out);

            out.close();

            System.out.println(row.getPhysicalNumberOfCells()+" "+row.getLastCellNum());

        } catch (IOException e) {
// TODO Auto-generated catch block

            e.printStackTrace();

        } //获取d://test.xls

    }

}
