package com.codejie.pms.util;


import com.microsoft.schemas.office.visio.x2012.main.CellType;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ImportExcelUtil {

    private final static String Excel_2003 = ".xls"; //2003 版本的excel
    private final static String Excel_2007 = ".xlsx"; //2007 版本的excel
    private final static String Excel_xlsm = ".xlsm"; //2007 版本的excel

    public ArrayList<String> getBankListByExcel1(InputStream in, String fileName) throws Exception{
        ArrayList<String> dataoneline = null;

        //创建Excel工作簿
        Workbook work = this.getWorkbook(in, fileName);
        if(work == null) {
            throw new Exception("创建Excel工作簿为空！");
        }
        Sheet sheet = null;
        Row row = null;
        Cell cell = null;
        dataoneline = new ArrayList<String>();
        //遍历Excel中的所有sheet
        for(int i = 0; i< work.getNumberOfSheets(); i++) {
            sheet = work.getSheetAt(i);
            if(sheet == null) {continue;}

            int firstRowCount = sheet.getFirstRowNum();//获取第一行的序号
            Row firstRow = sheet.getRow(firstRowCount);
            int cellCount = firstRow.getLastCellNum();//获取列数
            //解析每一行数据，构成数据对象
            int rowStart = firstRowCount + 1;
            int rowEnd = sheet.getPhysicalNumberOfRows();
//            System.out.println(firstRowCount);
//            System.out.println(cellCount);
//            System.out.println(rowEnd);

            for(int j = rowStart ; j < rowEnd ; j++){
                row = sheet.getRow(j);//获取对应的row对象
//                ArrayList<String> dataoneline = new ArrayList<String>();
                for(int k = 0; k < cellCount ; k++){
//                    System.out.println(getCellVal(row.getCell(k)));
                    dataoneline.add(getCellVal(row.getCell(k)));
                }
            }

//            NumberFormat nf=NumberFormat.getPercentInstance();
//            try {
//                Number userId=nf.parse(getCellVal(sheet.getRow(1).getCell(7)));
//                dataoneline.add(new String(String.valueOf(userId)));
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }

            for(int j = 2 ; j < 17 ; j++){
                row = sheet.getRow(j);//获取对应的row对象
                for(int k = 1 ; k < 6 ; k++){
                    dataoneline.add(getCellVal(row.getCell(k)));
                }

            }
        }
        in.close();
        return dataoneline;
    }

    /**
     * Checks if a {@code Row} is {@code null}, empty or all cells in this row are blank.
     * @param row
     * @return
     */
    @SuppressWarnings("deprecation")
    public static boolean isEmptyRow(Row row) {
//        if (row == null || row.toString().isEmpty()) {
//            return true;
//        } else {
//            int i = 0;
//            Iterator<Cell> it = row.iterator();
//            boolean isEmpty = true;
//            while (it.hasNext()) {
//                i++;
//                Cell cell = it.next();
//
//                if (cell != null || cell.getCellType() != Cell.CELL_TYPE_BLANK) {
//                    isEmpty = false;
//                    break;
//                }
//            }
//            System.out.println(i);
//            return isEmpty;
//        }
        for (int c = row.getFirstCellNum(); c < row.getLastCellNum(); c++) {
          Cell cell = row.getCell(c);
          if (cell != null && cell.getCellType() != Cell.CELL_TYPE_BLANK)
             return false;
        }
        return true;
    }

    public static String getbeforedate1(){
        Date dNow = new Date();   //当前时间
        Date dBefore = new Date();
        Calendar calendar = Calendar.getInstance(); //得到日历
        calendar.setTime(dNow);//把当前时间赋给日历
        calendar.add(Calendar.DAY_OF_MONTH, -1);  //设置为前一天
        dBefore = calendar.getTime();   //得到前一天的时间
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd"); //设置时间格式
        return sdf.format(dBefore);
    }



    public ArrayList<ArrayList<String>> getBankListByExcel2(InputStream in, String fileName) throws Exception{
        ArrayList<ArrayList<String>> returnList = null;

        //创建Excel工作簿
        Workbook work = this.getWorkbook(in, fileName);
        if(work == null) {
            throw new Exception("创建Excel工作簿为空！");
        }
        Sheet sheet = null;
        Row row = null;
        Cell cell = null;
        returnList = new ArrayList<ArrayList<String>>();
        String excel_name = fileName.replaceAll("\\d+", "").substring(fileName.replaceAll("\\d+", "").lastIndexOf("/")+1, fileName.replaceAll("\\d+", "").lastIndexOf("."));
        System.out.println(excel_name);
        //遍历Excel中的所有sheet
        System.out.println(work.getNumberOfSheets());
        for(int i = 0; i< work.getNumberOfSheets(); i++) {
//            int firstRowCount = sheet.getFirstRowNum();//获取第一行的序号
//            Row firstRow = sheet.getRow(firstRowCount);
//            int cellCount = firstRow.getLastCellNum();//获取列数
//            //解析每一行数据，构成数据对象
//            int rowStart = firstRowCount + 2;
//            int rowEnd = sheet.getLastRowNum() + 1;
            sheet = work.getSheetAt(i);
            if(sheet == null) {break;}
            String sheetname = sheet.getSheetName();

            if(excel_name.equals("集团并表")){
                if(sheetname.indexOf("金融衍生品表") >= 0){
                    int firstRowCount = sheet.getFirstRowNum();//获取第一行的序号
                    Row firstRow = sheet.getRow(firstRowCount);
                    int cellCount = firstRow.getLastCellNum() + 2;//获取列数
                    //解析每一行数据，构成数据对象
                    int rowStart = firstRowCount + 2;
                    int rowEnd = sheet.getLastRowNum() + 1;
                    System.out.println(rowStart + " " + rowEnd + " " +cellCount);
                    for(int j = rowStart ; j < rowEnd; j++){
                        row = sheet.getRow(j);//获取对应的row对象
                        //过滤掉空行
                        if (isEmptyRow(row) || row.getCell(0) == null || row.getCell(0).getCellType() == Cell.CELL_TYPE_BLANK) {
                            continue;
                        }
                        ArrayList<String> dataoneline = new ArrayList<String>();
                        for(int k = 0; k < cellCount; k++){
                            if(k == 0 || k == 1 || k == 2 || k == 3 || k == 4 || k == 5 || k == 6 || k == 7 || k == 8 || k == 9 || k == 10 || k == 11
                                    || k == 12 || k == 13 || k == 14 || k == 15 || k == 16 || k == 17 || k == 18 || k == 19 || k == 20 || k == 21 || k == 22 || k == 23
                                    || k == 24 || k == 25 || k == 26 || k == 27 || k == 28 || k == 29 || k == 30){
                                if(row.getCell(k) == null || row.getCell(k).getCellType() == Cell.CELL_TYPE_BLANK){
                                    dataoneline.add(" ");
                                }else{
//                                    try {
//                                        dataoneline.add(String.valueOf(row.getCell(k).getNumericCellValue()).trim());
//                                    } catch (IllegalStateException e) {
//                                        dataoneline.add(String.valueOf(row.getCell(k).getRichStringCellValue()).trim());
//                                    }
                                    if(k == 7){
                                        dataoneline.add(getCellVal(row.getCell(k)));
                                    }else{
                                        try {
                                            dataoneline.add(String.valueOf(row.getCell(k).getNumericCellValue()).trim());
                                        } catch (IllegalStateException e) {
                                            dataoneline.add(String.valueOf(row.getCell(k).getRichStringCellValue()).trim());
                                        }
                                    }
                                }
                            }
                        }
                        returnList.add(dataoneline);
                    }
                    ArrayList<String> dataoneline1 = new ArrayList<String>();
                    dataoneline1.add("***");
                    returnList.add(dataoneline1);
                }
                if(sheetname.indexOf("产品估值表") >= 0){
                    int firstRowCount = sheet.getFirstRowNum();//获取第一行的序号
                    Row firstRow = sheet.getRow(firstRowCount);
                    int cellCount = firstRow.getLastCellNum();//获取列数
                    //解析每一行数据，构成数据对象
                    int rowStart = firstRowCount + 3;
                    int rowEnd = sheet.getLastRowNum() + 1;
                    System.out.println(rowStart + " " + rowEnd + " " +cellCount);
                    for(int j = rowStart ; j < rowEnd; j++){
                        row = sheet.getRow(j);//获取对应的row对象
                        //过滤掉空行
                        if (isEmptyRow(row) || row.getCell(0) == null || row.getCell(0).getCellType() == Cell.CELL_TYPE_BLANK) {
                            continue;
                        }
                        ArrayList<String> dataoneline = new ArrayList<String>();
                        for(int k = 0; k < cellCount; k++){
                            if(k == 9 || k == 15){
                                if(row.getCell(k) == null || row.getCell(k).getCellType() == Cell.CELL_TYPE_BLANK){
                                    dataoneline.add(" ");
                                }else{
                                    try {
                                        dataoneline.add(String.valueOf(row.getCell(k).getNumericCellValue()).trim());
                                    } catch (IllegalStateException e) {
                                        dataoneline.add(String.valueOf(row.getCell(k).getRichStringCellValue()).trim());
                                    }
                                    //dataoneline.add(row.getCell(k).toString());
                                }
                            }
                        }
                        returnList.add(dataoneline);
                    }
                }

            }

            if(fileName.indexOf("项目日报") >= 0 && sheetname.indexOf("财务并表") >= 0){
                int firstRowCount = sheet.getFirstRowNum();//获取第一行的序号
                Row firstRow = sheet.getRow(firstRowCount);
                int cellCount = firstRow.getLastCellNum();//获取列数
                //解析每一行数据，构成数据对象
                int rowStart = firstRowCount + 2;
                int rowEnd = sheet.getLastRowNum() + 1;
                for(int j = rowStart ; j < rowEnd; j++){
                    row = sheet.getRow(j);//获取对应的row对象
                    if (isEmptyRow(row) || row.getCell(1) == null) {
                        continue;
                    }
                    ArrayList<String> dataoneline = new ArrayList<String>();
                    for(int k = 0; k < cellCount; k++){
                        if(k == 1 || k == 11 || k == 12 || k == 13){
                            if(row.getCell(k) == null || row.getCell(k).getCellType() == Cell.CELL_TYPE_BLANK){
                                dataoneline.add("0");
                            }else{
//                                    dataoneline.add(getCellVal(row.getCell(k)));
                                try {
                                    dataoneline.add(String.valueOf(row.getCell(k).getNumericCellValue()).trim());
                                } catch (IllegalStateException e) {
                                    dataoneline.add(String.valueOf(row.getCell(k).getRichStringCellValue()).trim());
                                }
                            }
                        }
                    }
                    returnList.add(dataoneline);
                }
            }

            if(i == 0){
//                sheet = work.getSheetAt(i);
//                if(sheet == null) {break;}
                //金融衍生品表
                if(fileName.indexOf("otcposdata") >= 0) {
                    int firstRowCount = sheet.getFirstRowNum();//获取第一行的序号
                    Row firstRow = sheet.getRow(firstRowCount);
                    int cellCount = firstRow.getLastCellNum();//获取列数
                    System.out.println(firstRowCount);
//                    System.out.println(firstRow);
                    System.out.println(cellCount);
                    //解析每一行数据，构成数据对象
                    int rowStart = firstRowCount + 2;
                    int rowEnd = sheet.getLastRowNum() + 1;
                    for(int j = rowStart ; j < rowEnd ; j++){
                        row = sheet.getRow(j);//获取对应的row对象
                        if (isEmptyRow(row)) {
                            continue;
                        }
                        ArrayList<String> dataoneline = new ArrayList<String>();
                        for(int k = 0; k < cellCount; k++){
                            //需要用到的表数据
                            if(k == 0 || k == 2 || k == 3 || k == 4 || k == 5 || k == 7 || k == 8 || k == 12 || k == 18 || k == 21 || k == 23 ||
                                    k == 38 || k == 41 || k == 42 || k == 43){
                                if(row.getCell(k) == null || row.getCell(k).getCellType() == Cell.CELL_TYPE_BLANK){
                                    dataoneline.add(" ");
                                }else{
                                    dataoneline.add(getCellVal(row.getCell(k)));
                                }
                            }
                        }
                        returnList.add(dataoneline);
                    }
                }

//                sheet = work.getSheetAt(i);
//                if(sheet == null) {
//                    break;
//                }
//                if(fileName.indexOf("项目日报") >= 0){
//                    int firstRowCount = sheet.getFirstRowNum();//获取第一行的序号
//                    Row firstRow = sheet.getRow(firstRowCount);
//                    int cellCount = firstRow.getLastCellNum();//获取列数
//                    //解析每一行数据，构成数据对象
//                    int rowStart = firstRowCount + 2;
//                    int rowEnd = sheet.getLastRowNum() + 1;
////                    System.out.println(rowStart); //2
////                    System.out.println(rowEnd);   //93 - 7
////                    System.out.println(cellCount); //37
//                    for(int j = rowStart ; j < rowEnd; j++){
//                        row = sheet.getRow(j);//获取对应的row对象
//                        if (isEmptyRow(row)) {
//                            continue;
//                        }
//                        ArrayList<String> dataoneline = new ArrayList<String>();
//                        for(int k = 0; k < cellCount; k++){
//                            if(k == 1 || k == 12 || k == 13){
//                                if(row.getCell(k) == null || row.getCell(k).getCellType() == Cell.CELL_TYPE_BLANK){
//                                    dataoneline.add(" ");
//                                }else{
////                                    dataoneline.add(getCellVal(row.getCell(k)));
//                                    try {
//                                        dataoneline.add(String.valueOf(row.getCell(k).getNumericCellValue()).trim());
//                                    } catch (IllegalStateException e) {
//                                        dataoneline.add(String.valueOf(row.getCell(k).getRichStringCellValue()).trim());
//                                    }
//                                }
//                            }
//                        }
//                        returnList.add(dataoneline);
//                    }
//                }
            }
//            if(i == 1){
//                sheet = work.getSheetAt(i);
//                if(sheet == null) {break;}
//                if(fileName.indexOf("资管") >= 0){
//                    int firstRowCount = sheet.getFirstRowNum();//获取第一行的序号
//                    Row firstRow = sheet.getRow(firstRowCount);
//                    int cellCount = firstRow.getLastCellNum();//获取列数
//                    //解析每一行数据，构成数据对象
//                    int rowStart = firstRowCount + 3;
//                    int rowEnd = sheet.getLastRowNum() + 1;
//                    System.out.println(rowStart + " " + rowEnd + " " +cellCount);
//                    for(int j = rowStart ; j < rowEnd; j++){
//                        row = sheet.getRow(j);//获取对应的row对象
//                        //过滤掉空行
//                        if (isEmptyRow(row) || row.getCell(0) == null || row.getCell(0).getCellType() == Cell.CELL_TYPE_BLANK) {
//                            continue;
//                        }
//                        ArrayList<String> dataoneline = new ArrayList<String>();
//                        for(int k = 0; k < cellCount; k++){
//                            if(k == 9 || k == 10){
//                                if(row.getCell(k) == null || row.getCell(k).getCellType() == Cell.CELL_TYPE_BLANK){
//                                    dataoneline.add(" ");
//                                }else{
//                                    dataoneline.add(row.getCell(k).toString());
//                                }
//                            }
//                        }
//                        returnList.add(dataoneline);
//                    }
//                }

                if(fileName.indexOf("集团并表明细接口表") >= 0){
                    if(sheetname.indexOf("金融资产表") >= 0){
                        int firstRowCount = sheet.getFirstRowNum();//获取第一行的序号
                        Row firstRow = sheet.getRow(firstRowCount);
                        int cellCount = firstRow.getLastCellNum();//获取列数
                        //解析每一行数据，构成数据对象
                        int rowStart = firstRowCount + 3;
                        int rowEnd = sheet.getLastRowNum() + 1;
                        System.out.println(rowStart + " " + rowEnd + " " +cellCount);
                        for(int j = rowStart ; j < rowEnd; j++){
                            row = sheet.getRow(j);//获取对应的row对象
                            //过滤掉空行
                            if (isEmptyRow(row) || row.getCell(0) == null || row.getCell(0).getCellType() == Cell.CELL_TYPE_BLANK) {
                                continue;
                            }
//                        System.out.println(row.getCell(0));
                            ArrayList<String> dataoneline = new ArrayList<String>();
                            for(int k = 0; k < cellCount; k++){
                                if(k == 0 || k == 1 || k == 5 || k == 6 || k == 13 || k == 14 || k == 15 || k == 16 || k == 17 || k == 18 || k == 19 || k == 20 || k == 21
                                        || k == 31 || k == 38 || k == 39 || k == 40){
                                    if(row.getCell(k) == null || row.getCell(k).getCellType() == Cell.CELL_TYPE_BLANK){
                                        dataoneline.add(" ");
                                    }else{
//                                    System.out.println(getCellVal(row.getCell(k)));
                                        dataoneline.add(getCellVal(row.getCell(k)));
                                    }
                                }
                            }
                            returnList.add(dataoneline);
                        }
                        ArrayList<String> dataoneline1 = new ArrayList<String>();
                        dataoneline1.add("***");
                        returnList.add(dataoneline1);
                    }
                    if(sheetname.indexOf("填报指标值维护") >= 0){
                        int firstRowCount = sheet.getFirstRowNum();//获取第一行的序号
                        Row firstRow = sheet.getRow(firstRowCount);
                        int cellCount = firstRow.getLastCellNum();//获取列数
                        //解析每一行数据，构成数据对象
                        int rowStart = firstRowCount + 2;
                        int rowEnd = sheet.getLastRowNum() + 1;
                        for(int j = rowStart ; j < rowEnd; j++){
                            row = sheet.getRow(j);//获取对应的row对象
                            //过滤掉空行
                            if (isEmptyRow(row) || row.getCell(0) == null || row.getCell(0).getCellType() == Cell.CELL_TYPE_BLANK) {
                                continue;
                            }
                            ArrayList<String> dataoneline = new ArrayList<String>();
                            for(int k = 0; k < cellCount; k++){
                                if(k == 0 || k == 2 || k == 3 || k == 4 || k == 5 || k == 6 || k == 7 || k == 8 || k == 9){
                                    if(row.getCell(k) == null || row.getCell(k).getCellType() == Cell.CELL_TYPE_BLANK){
                                        dataoneline.add(" ");
                                    }else{
                                        if(k == 4){
                                            try {
                                                dataoneline.add(String.valueOf(row.getCell(k).getNumericCellValue()).trim());
                                            } catch (IllegalStateException e) {
                                                dataoneline.add(String.valueOf(row.getCell(k).getRichStringCellValue()).trim());
                                            }
                                        }else if(k == 6){
                                            dataoneline.add(row.getCell(k).toString().trim());
                                        }else{
                                            dataoneline.add(getCellVal(row.getCell(k)));
                                        }
//                                System.out.println(getCellVal(row.getCell(k)));

                                    }
                                }
                            }
                            returnList.add(dataoneline);
                        }
                    }
                }
//            }


//            if(i == 8){
////                sheet = work.getSheetAt(i);
////                if(sheet == null) {break;}
//                if(fileName.indexOf("明细接口表") >= 0){
//                    int firstRowCount = sheet.getFirstRowNum();//获取第一行的序号
//                    Row firstRow = sheet.getRow(firstRowCount);
//                    int cellCount = firstRow.getLastCellNum();//获取列数
//                    //解析每一行数据，构成数据对象
//                    int rowStart = firstRowCount + 2;
//                    int rowEnd = sheet.getLastRowNum() + 1;
////                    System.out.println(rowStart + " " + rowEnd + " " +cellCount);
//                    for(int j = rowStart ; j < rowEnd; j++){
//                        row = sheet.getRow(j);//获取对应的row对象
//                        //过滤掉空行
//                        if (isEmptyRow(row) || row.getCell(0) == null || row.getCell(0).getCellType() == Cell.CELL_TYPE_BLANK) {
//                            continue;
//                        }
//                        ArrayList<String> dataoneline = new ArrayList<String>();
//                        for(int k = 0; k < cellCount; k++){
//                            if(k == 0 || k == 2 || k == 3 || k == 4 || k == 5 || k == 6 || k == 7 || k == 8 || k == 9){
//                                if(row.getCell(k) == null || row.getCell(k).getCellType() == Cell.CELL_TYPE_BLANK){
//                                    dataoneline.add(" ");
//                                }else{
//                                    if(k == 4){
//                                        try {
//                                            dataoneline.add(String.valueOf(row.getCell(k).getNumericCellValue()).trim());
//                                        } catch (IllegalStateException e) {
//                                            dataoneline.add(String.valueOf(row.getCell(k).getRichStringCellValue()).trim());
//                                        }
//                                    }else if(k == 6){
//                                        dataoneline.add(row.getCell(k).toString().trim());
//                                    }else{
//                                        dataoneline.add(getCellVal(row.getCell(k)));
//                                    }
////                                    System.out.println(getCellVal(row.getCell(k)));
//
//                                }
//                            }
//                        }
//                        returnList.add(dataoneline);
//                    }
//                }
//            }


        }
        in.close();
        return returnList;
    }


    public ArrayList<ArrayList<String>> getBankListByExcel3(InputStream in, String fileName) throws Exception{
        ArrayList<ArrayList<String>> returnList = null;

        //创建Excel工作簿
        Workbook work = this.getWorkbook(in, fileName);
        if(work == null) {
            throw new Exception("创建Excel工作簿为空！");
        }
        Sheet sheet = null;
        Row row = null;
        Cell cell = null;
        returnList = new ArrayList<ArrayList<String>>();
        String excel_name = fileName.replaceAll("\\d+", "").substring(fileName.replaceAll("\\d+", "").lastIndexOf("/")+1, fileName.replaceAll("\\d+", "").lastIndexOf("."));
        System.out.println(excel_name);
        //遍历Excel中的所有sheet
//        System.out.println(work.getNumberOfSheets());
        for(int i = 0; i< work.getNumberOfSheets(); i++) {

            sheet = work.getSheetAt(i);
            if(sheet == null) {break;}
            String sheetname = sheet.getSheetName();

            if(fileName.indexOf("金融资产表") >= 0){
                int firstRowCount = sheet.getFirstRowNum();//获取第一行的序号
                Row firstRow = sheet.getRow(firstRowCount);
                int cellCount = firstRow.getLastCellNum();//获取列数
                //解析每一行数据，构成数据对象
                int rowStart = firstRowCount + 2;
                int rowEnd = sheet.getLastRowNum() + 1;
                System.out.println(rowStart + " " + rowEnd + " " +cellCount);
                for(int j = rowStart ; j < rowEnd; j++){
                    row = sheet.getRow(j);//获取对应的row对象
                    //过滤掉空行
                    if (isEmptyRow(row)) {
                        continue;
                    }
                    ArrayList<String> dataoneline = new ArrayList<String>();
                    for(int k = 0; k < cellCount; k++){
                        if(k == 0 || k == 1 || k == 2 || k == 3 || k == 4 || k == 5 || k == 6 || k == 7 || k == 8 || k == 9 || k == 10 || k == 11 || k == 12 || k == 13){
                            if(row.getCell(k) == null || row.getCell(k).getCellType() == Cell.CELL_TYPE_BLANK){
                                dataoneline.add(" ");
                            }else{
                                dataoneline.add(getCellVal(row.getCell(k)));
                            }
                        }
                    }
                    returnList.add(dataoneline);
                }
            }

            if(excel_name.equals("集团并表") && sheetname.indexOf("产品估值表") >= 0){
                int firstRowCount = sheet.getFirstRowNum();//获取第一行的序号
                Row firstRow = sheet.getRow(firstRowCount);
                int cellCount = firstRow.getLastCellNum();//获取列数
                //解析每一行数据，构成数据对象
                int rowStart = firstRowCount + 3;
                int rowEnd = sheet.getLastRowNum() + 1;
                System.out.println(rowStart + " " + rowEnd + " " +cellCount);
                for(int j = rowStart ; j < rowEnd; j++){
                    row = sheet.getRow(j);//获取对应的row对象
                    //过滤掉空行
                    if (isEmptyRow(row) || row.getCell(0) == null || row.getCell(0).getCellType() == Cell.CELL_TYPE_BLANK) {
                        continue;
                    }
                    ArrayList<String> dataoneline = new ArrayList<String>();
                    for(int k = 0; k < cellCount; k++){
                        if(k == 0 || k == 1 || k == 2 || k == 3 || k == 4 || k == 5 || k == 6 || k == 7 || k == 8 || k == 9 || k == 10 ||
                                k == 11 || k == 12 || k == 13 || k == 14 || k == 15 || k == 16 || k == 17 || k == 18 || k == 19 || k == 20 ||
                                k == 21 || k == 22 || k == 23 || k == 24 || k == 25 || k == 26 || k == 27 || k == 28 || k == 29 || k == 30 ||
                                k == 31 || k == 32 || k == 33 || k == 34){
                            if(row.getCell(k) == null || row.getCell(k).getCellType() == Cell.CELL_TYPE_BLANK){
                                dataoneline.add(" ");
                            }else{
                                try {
//                                    if(k == 1){
//                                        dataoneline.add(getCellVal(row.getCell(k)));
//                                    }else{
                                        dataoneline.add(String.valueOf(row.getCell(k).getNumericCellValue()).trim());
//                                    }
                                } catch (IllegalStateException e) {
//                                    System.out.println(String.valueOf(row.getCell(k).getRichStringCellValue()).trim());
                                    if(k == 1){
                                        dataoneline.add(getCellVal(row.getCell(k)));
                                    }else{
                                        dataoneline.add(String.valueOf(row.getCell(k).getRichStringCellValue()).trim());
                                    }
                                }
                            }
                        }
                    }
                    returnList.add(dataoneline);
                }
            }

            if(fileName.indexOf("集团并表明细接口表") >= 0){
                if(sheetname.indexOf("股权投资表") >= 0){
                    int firstRowCount = sheet.getFirstRowNum();//获取第一行的序号
                    Row firstRow = sheet.getRow(firstRowCount);
                    int cellCount = firstRow.getLastCellNum();//获取列数
                    //解析每一行数据，构成数据对象
                    int rowStart = firstRowCount + 2;
                    int rowEnd = sheet.getLastRowNum() + 1;
                    System.out.println(rowStart + " " + rowEnd + " " +cellCount);
                    for(int j = rowStart ; j < rowEnd; j++){
                        row = sheet.getRow(j);//获取对应的row对象
                        //过滤掉空行
                        if (isEmptyRow(row) || row.getCell(0) == null || row.getCell(0).getCellType() == Cell.CELL_TYPE_BLANK) {
                            continue;
                        }
                        ArrayList<String> dataoneline = new ArrayList<String>();
                        for(int k = 0; k < 13; k++){
                            if(k == 0 || k == 1 || k == 2 || k == 3 || k == 4 || k == 5 || k == 6 || k == 7 || k == 8 || k == 9 || k == 10 || k == 11 || k == 12) {
                                if(row.getCell(k) == null || row.getCell(k).getCellType() == Cell.CELL_TYPE_BLANK){
                                    dataoneline.add(" ");
                                }else{
                                    dataoneline.add(getCellVal(row.getCell(k)));
                                }
                            }
                        }
                        returnList.add(dataoneline);
                    }
                    ArrayList<String> dataoneline1 = new ArrayList<String>();
                    dataoneline1.add("***");
                    returnList.add(dataoneline1);
                }
                if(sheetname.indexOf("最低结算备用金") >= 0){
                    int firstRowCount = sheet.getFirstRowNum();//获取第一行的序号
                    Row firstRow = sheet.getRow(firstRowCount);
                    int cellCount = firstRow.getLastCellNum();//获取列数
                    //解析每一行数据，构成数据对象
                    int rowStart = firstRowCount + 2;
                    int rowEnd = sheet.getLastRowNum() + 1;
                    System.out.println(rowStart + " " + rowEnd + " " +cellCount);
                    for(int j = rowStart ; j < rowEnd; j++){
                        row = sheet.getRow(j);//获取对应的row对象
                        //过滤掉空行
                        if (isEmptyRow(row) || row.getCell(0) == null || row.getCell(0).getCellType() == Cell.CELL_TYPE_BLANK) {
                            continue;
                        }
                        ArrayList<String> dataoneline = new ArrayList<String>();
                        for(int k = 0; k < 7; k++){
                            if(k == 0 || k == 1 || k == 2 || k == 3 || k == 4 || k == 5 || k == 6) {
                                if(row.getCell(k) == null || row.getCell(k).getCellType() == Cell.CELL_TYPE_BLANK){
                                    dataoneline.add(" ");
                                }else{
                                    dataoneline.add(getCellVal(row.getCell(k)));
                                }
                            }
                        }
                        returnList.add(dataoneline);
                    }
                    ArrayList<String> dataoneline1 = new ArrayList<String>();
                    dataoneline1.add("%*%");
                    returnList.add(dataoneline1);
                }
                if(sheetname.indexOf("填报指标值维护") >= 0){
                  int firstRowCount = sheet.getFirstRowNum();//获取第一行的序号
                  Row firstRow = sheet.getRow(firstRowCount);
                  int cellCount = firstRow.getLastCellNum();//获取列数
                  //解析每一行数据，构成数据对象
                  int rowStart = firstRowCount + 2;
                  int rowEnd = sheet.getLastRowNum() + 1;
                  for(int j = rowStart ; j < rowEnd; j++){
                      row = sheet.getRow(j);//获取对应的row对象
                      //过滤掉空行
                      if (isEmptyRow(row) || row.getCell(0) == null || row.getCell(0).getCellType() == Cell.CELL_TYPE_BLANK) {
                          continue;
                      }
                      ArrayList<String> dataoneline = new ArrayList<String>();
                      for(int k = 0; k < cellCount; k++){
                          if(k == 0 || k == 2 || k == 3 || k == 4 || k == 5 || k == 6 || k == 7 || k == 8 || k == 9){
                              if(row.getCell(k) == null || row.getCell(k).getCellType() == Cell.CELL_TYPE_BLANK){
                                  dataoneline.add(" ");
                              }else{
                                  if(k == 4){
                                      try {
                                          dataoneline.add(String.valueOf(row.getCell(k).getNumericCellValue()).trim());
                                      } catch (IllegalStateException e) {
                                          dataoneline.add(String.valueOf(row.getCell(k).getRichStringCellValue()).trim());
                                      }
                                  }else if(k == 6){
                                      dataoneline.add(row.getCell(k).toString().trim());
                                  }else{
                                      dataoneline.add(getCellVal(row.getCell(k)));
                                  }
                              }
                          }
                      }
                      returnList.add(dataoneline);
                  }
                }
            }
        }
        in.close();
        return returnList;
    }

    public ArrayList<ArrayList<String>> getBankListByExcel4(InputStream in, String fileName) throws Exception{
        ArrayList<ArrayList<String>> returnList = null;
        //创建Excel工作簿
        Workbook work = this.getWorkbook(in, fileName);
        if(work == null) {
            throw new Exception("创建Excel工作簿为空！");
        }
        Sheet sheet = null;
        Row row = null;
        Cell cell = null;
        returnList = new ArrayList<ArrayList<String>>();
        for(int i = 0; i< work.getNumberOfSheets(); i++) {

            sheet = work.getSheetAt(i);
            if(sheet == null) {break;}
            String sheetname = sheet.getSheetName();
            System.out.println(sheetname);

            int firstRowCount = sheet.getFirstRowNum();//获取第一行的序号
            Row firstRow = sheet.getRow(firstRowCount);
            int cellCount = firstRow.getLastCellNum();//获取列数
            //解析每一行数据，构成数据对象
            int rowStart = firstRowCount + 1;
            int rowEnd = sheet.getLastRowNum() + 1;
            System.out.println(rowStart + " " + rowEnd + " " +cellCount);
            if(sheetname.indexOf("金融资产表") >= 0){
                for(int j = rowStart ; j < rowEnd; j++){
                    row = sheet.getRow(j);//获取对应的row对象
                    //过滤掉空行
                    if (isEmptyRow(row) || row.getCell(0) == null || row.getCell(0).getCellType() == Cell.CELL_TYPE_BLANK) {
                        continue;
                    }
                    ArrayList<String> dataoneline = new ArrayList<String>();
                    for(int k = 0; k < cellCount; k++){
                        if(k == 0 || k == 1 || k == 2 || k == 3 || k == 4 || k == 5 || k == 6 || k == 7 || k == 8 || k == 9 || k == 10 || k == 11 || k == 12 || k == 13 || k == 14 || k == 15 || k == 16){
                            if(row.getCell(k) == null || row.getCell(k).getCellType() == Cell.CELL_TYPE_BLANK){
                                dataoneline.add(" ");
                            }else{
                                dataoneline.add(getCellVal(row.getCell(k)));
                            }
                        }
                    }
                    returnList.add(dataoneline);
                }
            } else if(sheetname.indexOf("金融衍生品表") >= 0){
                for(int j = rowStart ; j < rowEnd; j++){
                    row = sheet.getRow(j);//获取对应的row对象
                    //过滤掉空行
                    if (isEmptyRow(row) || row.getCell(0) == null || row.getCell(0).getCellType() == Cell.CELL_TYPE_BLANK) {
                        continue;
                    }
                    ArrayList<String> dataoneline = new ArrayList<String>();
                    for(int k = 0; k < cellCount; k++){
                        if(k == 0 || k == 1 || k == 2 || k == 3 || k == 4 || k == 5 || k == 6 || k == 7 || k == 8 || k == 9 || k == 10 ||
                                k == 11 || k == 12 || k == 13 || k == 14 || k == 15 || k == 16 || k == 17 || k == 18 || k == 19 || k == 20 ||
                                k == 21 || k == 22 || k == 23 || k == 24 || k == 25 || k == 26){
                            if(row.getCell(k) == null || row.getCell(k).getCellType() == Cell.CELL_TYPE_BLANK){
                                dataoneline.add(" ");
                            }else{
                                dataoneline.add(getCellVal(row.getCell(k)));
                            }
                        }
                    }
                    returnList.add(dataoneline);
                }
            }else if(sheetname.indexOf("产品估值表") >= 0){
                for(int j = rowStart ; j < rowEnd; j++){
                    row = sheet.getRow(j);//获取对应的row对象
                    //过滤掉空行
                    if (isEmptyRow(row) || row.getCell(0) == null || row.getCell(0).getCellType() == Cell.CELL_TYPE_BLANK) {
                        continue;
                    }
                    ArrayList<String> dataoneline = new ArrayList<String>();
                    for(int k = 0; k < cellCount; k++){
                        if(k == 0 || k == 1 || k == 2 || k == 3 || k == 4 || k == 5 || k == 6 || k == 7 || k == 8 || k == 9 || k == 10 ||
                                k == 11 || k == 12 || k == 13 || k == 14 || k == 15 || k == 16 || k == 17 || k == 18 || k == 19 || k == 20 ||
                                k == 21 || k == 22 || k == 23 || k == 24 || k == 25 || k == 26 || k == 27 || k == 28 || k == 29 || k == 30 ||
                                k == 31 || k == 32 || k == 33 || k == 34){
                            if(row.getCell(k) == null || row.getCell(k).getCellType() == Cell.CELL_TYPE_BLANK){
                                dataoneline.add(" ");
                            }else{
                                dataoneline.add(getCellVal(row.getCell(k)));
                            }
                        }
                    }
                    returnList.add(dataoneline);
                }
            } else if(sheetname.indexOf("股权投资表") >= 0){
                for(int j = rowStart ; j < rowEnd; j++){
                    row = sheet.getRow(j);//获取对应的row对象
                    //过滤掉空行
                    if (isEmptyRow(row) || row.getCell(0) == null || row.getCell(0).getCellType() == Cell.CELL_TYPE_BLANK) {
                        continue;
                    }
                    ArrayList<String> dataoneline = new ArrayList<String>();
                    for(int k = 0; k < cellCount; k++){
                        if(k == 0 || k == 1 || k == 2 || k == 3 || k == 4 || k == 5 || k == 6 || k == 7 || k == 8 || k == 9 || k == 10){
                            if(row.getCell(k) == null || row.getCell(k).getCellType() == Cell.CELL_TYPE_BLANK){
                                dataoneline.add(" ");
                            }else{
                                dataoneline.add(getCellVal(row.getCell(k)));
                            }
                        }
                    }
                    returnList.add(dataoneline);
                }
            }else if(sheetname.indexOf("最低结算备用金") >= 0){
                for(int j = rowStart ; j < rowEnd; j++){
                    row = sheet.getRow(j);//获取对应的row对象
                    //过滤掉空行
                    if (isEmptyRow(row) || row.getCell(0) == null || row.getCell(0).getCellType() == Cell.CELL_TYPE_BLANK) {
                        continue;
                    }
                    ArrayList<String> dataoneline = new ArrayList<String>();
                    for(int k = 0; k < cellCount; k++){
                        if(k == 0 || k == 1 || k == 2 || k == 3 || k == 4){
                            if(row.getCell(k) == null || row.getCell(k).getCellType() == Cell.CELL_TYPE_BLANK){
                                dataoneline.add(" ");
                            }else{
                                dataoneline.add(getCellVal(row.getCell(k)));
                            }
                        }
                    }
                    returnList.add(dataoneline);
                }
            }else if(sheetname.indexOf("填报指标值维护") >= 0){
                for(int j = rowStart ; j < rowEnd; j++){
                    row = sheet.getRow(j);//获取对应的row对象
                    //过滤掉空行
                    if (isEmptyRow(row) || row.getCell(0) == null || row.getCell(0).getCellType() == Cell.CELL_TYPE_BLANK) {
                        continue;
                    }
                    ArrayList<String> dataoneline = new ArrayList<String>();
                    for(int k = 0; k < cellCount; k++){
                        if(k == 0 || k == 1 || k == 2 || k == 3 || k == 4 || k == 5 || k == 6 || k == 7 || k == 8 || k == 9){
                            if(row.getCell(k) == null || row.getCell(k).getCellType() == Cell.CELL_TYPE_BLANK){
                                dataoneline.add(" ");
                            }else{
                                dataoneline.add(getCellVal(row.getCell(k)));
                            }
                        }
                    }
                    returnList.add(dataoneline);
                }
            }
            ArrayList<String> dataoneline1 = new ArrayList<String>();
            dataoneline1.add("***");
            returnList.add(dataoneline1);
        }
        in.close();
        return returnList;
    }

    public ArrayList<ArrayList<String>> getBankListByExcel5(InputStream in, String fileName) throws Exception{
        ArrayList<ArrayList<String>> returnList = null;
        //创建Excel工作簿
        Workbook work = this.getWorkbook(in, fileName);
        if(work == null) {
            throw new Exception("创建Excel工作簿为空！");
        }
        Sheet sheet = null;
        Row row = null;
        Cell cell = null;
        returnList = new ArrayList<ArrayList<String>>();
        for(int i = 0; i< work.getNumberOfSheets(); i++) {

            sheet = work.getSheetAt(i);
            if(sheet == null) {break;}
            String sheetname = sheet.getSheetName();
            System.out.println(sheetname);

            int firstRowCount = sheet.getFirstRowNum();//获取第一行的序号
            Row firstRow = sheet.getRow(firstRowCount);
            int cellCount = firstRow.getLastCellNum();//获取列数
            //解析每一行数据，构成数据对象
            int rowStart = firstRowCount + 1;
            int rowEnd = sheet.getLastRowNum() + 1;
//            System.out.println(rowStart + " " + rowEnd + " " +cellCount);
            if(sheetname.indexOf("金融资产表") >= 0){
                for(int j = rowStart ; j < rowEnd; j++){
                    row = sheet.getRow(j);//获取对应的row对象
                    //过滤掉空行
                    if (isEmptyRow(row) || row.getCell(0) == null || row.getCell(0).getCellType() == Cell.CELL_TYPE_BLANK) {
                        continue;
                    }
                    ArrayList<String> dataoneline = new ArrayList<String>();
                    for(int k = 0; k < cellCount; k++){
                        if(k == 0 || k == 1 || k == 2 || k == 3 || k == 4 || k == 5 || k == 6 || k == 7 || k == 8 || k == 9 || k == 10 || k == 11 || k == 12 || k == 13 || k == 14 || k == 15 || k == 16){
                            if(row.getCell(k) == null || row.getCell(k).getCellType() == Cell.CELL_TYPE_BLANK){
                                dataoneline.add(" ");
                            }else{
                                dataoneline.add(getCellVal(row.getCell(k)));
                            }
                        }
                    }
                    returnList.add(dataoneline);
                }
            } else if(sheetname.indexOf("金融衍生品表") >= 0){
                for(int j = rowStart ; j < rowEnd; j++){
                    row = sheet.getRow(j);//获取对应的row对象
                    //过滤掉空行
                    if (isEmptyRow(row) || row.getCell(0) == null || row.getCell(0).getCellType() == Cell.CELL_TYPE_BLANK) {
                        continue;
                    }
                    ArrayList<String> dataoneline = new ArrayList<String>();
                    for(int k = 0; k < cellCount; k++){
                        if(k == 0 || k == 1 || k == 2 || k == 3 || k == 4 || k == 5 || k == 6 || k == 7 || k == 8 || k == 9 || k == 10 ||
                                k == 11 || k == 12 || k == 13 || k == 14 || k == 15 || k == 16 || k == 17 || k == 18 || k == 19 || k == 20 ||
                                k == 21 || k == 22 || k == 23 || k == 24 || k == 25 || k == 26 || k == 27){
                            if(row.getCell(k) == null || row.getCell(k).getCellType() == Cell.CELL_TYPE_BLANK){
                                dataoneline.add(" ");
                            }else{
                                dataoneline.add(getCellVal(row.getCell(k)));
                            }
                        }
                    }
                    returnList.add(dataoneline);
                }
            } else if(sheetname.indexOf("填报指标值维护") >= 0){
                for(int j = rowStart ; j < rowEnd; j++){
                    row = sheet.getRow(j);//获取对应的row对象
                    //过滤掉空行
                    if (isEmptyRow(row) || row.getCell(0) == null || row.getCell(0).getCellType() == Cell.CELL_TYPE_BLANK) {
                        continue;
                    }
                    ArrayList<String> dataoneline = new ArrayList<String>();
                    for(int k = 0; k < cellCount; k++){
                        if(k == 0 || k == 1 || k == 2 || k == 3 || k == 4 || k == 5 || k == 6 || k == 7 || k == 8){
                            if(row.getCell(k) == null || row.getCell(k).getCellType() == Cell.CELL_TYPE_BLANK){
                                dataoneline.add(" ");
                            }else{
                                dataoneline.add(getCellVal(row.getCell(k)));
                            }
                        }
                    }
                    returnList.add(dataoneline);
                }
            }
            ArrayList<String> dataoneline1 = new ArrayList<String>();
            dataoneline1.add("***");
            returnList.add(dataoneline1);
        }
        in.close();
        return returnList;
    }

    public static String getCellVal(Cell cel) {
//        if(cel.getCellType() == Cell.CELL_TYPE_STRING) {
//            return cel.getRichStringCellValue().getString();
//        }
//        switch (cel.getCellType()) {
//            case HSSFCell.CELL_TYPE_FORMULA: //公式类型
//                // cell.getCellFormula();
//                try {
//                    System.out.println(String.valueOf(cel.getNumericCellValue()));
//                } catch (IllegalStateException e) {
//                    System.out.println(String.valueOf(cel.getRichStringCellValue()));
//                }
//                break;
//        }
//        if(cel.getCellType() == HSSFCell.CELL_TYPE_FORMULA){
//            try {
//                    System.out.println(String.valueOf(cel.getNumericCellValue()));
//            } catch (IllegalStateException e) {
//                    System.out.println(String.valueOf(cel.getRichStringCellValue()));
//            }
//            return String.valueOf(cel.getNumericCellValue());
//        }
        if(cel.getCellType() == Cell.CELL_TYPE_NUMERIC) {
            String result = new String();
            if (HSSFDateUtil.isCellDateFormatted(cel)) {// 处理日期格式、时间格式
                SimpleDateFormat sdf;
                if (cel.getCellStyle().getDataFormat() == HSSFDataFormat
                        .getBuiltinFormat("h:mm")) {
                    sdf = new SimpleDateFormat("HH:mm");
                } else {// 日期
                    sdf = new SimpleDateFormat("yyyyMMdd");
                }
                Date date = cel.getDateCellValue();
                result = sdf.format(date);
            }
            else {
                double d = cel.getNumericCellValue();
                NumberFormat nf = NumberFormat.getInstance();

                String s = nf.format(d);
                if (s.indexOf(",") >= 0) {
                    s = s.replace(",", "");
                }
//                double value = cel.getNumericCellValue();
//                CellStyle style = cel.getCellStyle();
//                DecimalFormat format = new DecimalFormat();
//                String temp = style.getDataFormatString();
                // 单元格设置成常规
//                if (temp.equals("General")) {
//                    format.applyPattern("#.##");
//                }
                result = s;
            }
            return result;
        }
//        if(cel.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
//            return cel.getBooleanCellValue() + "";
//        }
//        if(cel.getCellType() == Cell.CELL_TYPE_FORMULA) {
//            return cel.getCellFormula() + "";
//        }
        return cel.toString();
    }

    public List<List<Object>> getBankListByExcel(InputStream in, String fileName) throws Exception{
        List<List<Object>> list = null;

        //创建Excel工作簿
        Workbook work = this.getWorkbook(in, fileName);
        if(work == null) {
            throw new Exception("创建Excel工作簿为空！");
        }
        Sheet sheet = null;
        Row row = null;
        Cell cell = null;
        list = new ArrayList<List<Object>>();
        //遍历Excel中的所有sheet
        for(int i = 0; i<work.getNumberOfSheets(); i++) {
            sheet = work.getSheetAt(i);
            if(sheet == null) {continue;}
            //遍历当前sheet中的所有行
            //int totalRow = sheet.getPhysicalNumberOfRows();//如果excel有格式，这种方式取值不准确
            int totalRow = sheet.getPhysicalNumberOfRows();
            for(int j = sheet.getFirstRowNum(); j<totalRow; j++) {
                row = sheet.getRow(j);
                if(!isRowEmpty(row)) {
                    //if(row != null && !"".equals(row)) {
                    //获取第一个单元格的数据是否存在
                    Cell fristCell=row.getCell(0);
                    if(fristCell!=null){
                        //遍历所有的列
                        List<Object> li = new ArrayList<Object>();
                        //int totalColum = row.getLastCellNum();
                        for(int y = row.getFirstCellNum(); y<row.getLastCellNum(); y++) {
                            cell = row.getCell(y);
                            String callCal = this.getCellValue(cell)+"";
                            li.add(callCal);
                        }
                        list.add(li);
                    }
                }else if(isRowEmpty(row)){
                    continue;
                }

            }
        }
        in.close();
        return list;
    }
    /**
     * 判断行是否为空
     * @param row
     * @return
     */
    public static boolean isRowEmpty(Row row) {
//        for (int c = row.getFirstCellNum(); c < row.getLastCellNum(); c++) {
//            Cell cell = row.getCell(c);
//            if (cell != null && cell.getCellType() != Cell.CELL_TYPE_BLANK)
//                return false;
//        }
        return true;
    }
    /**
     * 描述：根据文件后缀，自动适应上传文件的版本
     * @param inStr,fileName
     * @return
     * @throws Exception
     * */
    public Workbook getWorkbook(InputStream inStr,String fileName) throws Exception {
        Workbook work = null;
        String fileType = fileName.substring(fileName.lastIndexOf("."));
        if(Excel_2003.equals(fileType)){
            work=new HSSFWorkbook(inStr);//2003 版本的excel
        }else if(Excel_2007.equals(fileType) || Excel_xlsm.equals(fileType)) {
            work=new XSSFWorkbook(inStr);//2007 版本的excel
        }else {
            throw new Exception("解析文件格式有误！");
        }
        return work;
    }

    /**
     * 描述：对表格中数值进行格式化
     * @param cell
     * @return
     * */
    public Object getCellValue(Cell cell) {
		/*Object value = null;
		DecimalFormat df1 = new DecimalFormat("0.00");//格式化number，string字符
		SimpleDateFormat sdf = new  SimpleDateFormat("yyy-MM-dd");//日期格式化
		DecimalFormat df2 = new DecimalFormat("0.00");//格式化数字
		if(cell !=null && !"".equals(cell)) {
			switch (cell.getCellType()) {
			case Cell.CELL_TYPE_STRING:
				value = cell.getRichStringCellValue().getString();
				break;
			case Cell.CELL_TYPE_NUMERIC:
				if("General".equals(cell.getCellStyle().getDataFormatString())) {
					value = df1.format(cell.getNumericCellValue());
				}else if("m/d/yy".equals(cell.getCellStyle().getDataFormatString())) {
					value = sdf.format(cell.getDateCellValue());
				}else if(HSSFDateUtil.isCellDateFormatted(cell)){
					Date date = cell.getDateCellValue();
					value = sdf.format(date);
				}
				else {
					value = df2.format(cell.getNumericCellValue());
				}
				break;
			case Cell.CELL_TYPE_BOOLEAN:
				value = cell.getBooleanCellValue();
				break;
			case Cell.CELL_TYPE_BLANK:
				value = "";
				break;
			default:
				break;
			}
		}
		return value;*/
        String result = new String();
        switch (cell.getCellType()) {
            case HSSFCell.CELL_TYPE_FORMULA:  //Excel公式
                try {
                    result = String.valueOf(cell.getNumericCellValue());
                } catch (IllegalStateException e) {
                    result = String.valueOf(cell.getRichStringCellValue());
                }
                break;
            case HSSFCell.CELL_TYPE_NUMERIC:// 数字类型
                if (HSSFDateUtil.isCellDateFormatted(cell)) {// 处理日期格式、时间格式
                    SimpleDateFormat sdf;
                    if (cell.getCellStyle().getDataFormat() == HSSFDataFormat
                            .getBuiltinFormat("h:mm")) {
                        sdf = new SimpleDateFormat("HH:mm");
                    } else {// 日期
                        sdf = new SimpleDateFormat("yyyy-MM-dd");
                    }
                    Date date = cell.getDateCellValue();
                    result = sdf.format(date);
                } else if (cell.getCellStyle().getDataFormat() == 58) {
                    // 处理自定义日期格式：m月d日(通过判断单元格的格式id解决，id的值是58)
                    SimpleDateFormat sdf = new SimpleDateFormat("M月d日");
                    double value = cell.getNumericCellValue();
                    Date date = org.apache.poi.ss.usermodel.DateUtil
                            .getJavaDate(value);
                    result = sdf.format(date);
                } else {
                    double value = cell.getNumericCellValue();
                    CellStyle style = cell.getCellStyle();
                    DecimalFormat format = new DecimalFormat();
                    String temp = style.getDataFormatString();
                    // 单元格设置成常规
                    if (temp.equals("General")) {
                        format.applyPattern("#.##");
                    }
                    result = format.format(value);
                }
                break;
            case HSSFCell.CELL_TYPE_STRING:// String类型
                result = cell.getRichStringCellValue().toString();
                break;
            case HSSFCell.CELL_TYPE_BLANK:
                result = "";
            default:
                result = "";
                break;
        }
        return result;
    }

    public String getFormat(String str) {
        if(str.equals("null")) {
            str="";
            return str;
        }else{
            return str;
        }
    }
    public Integer getFormats(Integer str) {
        if(str==null) {
            str=0;
            return str;
        }else{
            return str;
        }
    }

    /**
     * 获取字符串中的数字订单号、数字金额等，如从"USD 374.69"中获取到374.69、从“交易单号：66666666666”获取到66666666666
     * @param
     * @return
     */
    public static String getFormatNumber(String str){
        str = str.trim();
        Pattern p = Pattern.compile("[0-9]");
        int indexNum = 0;
        int lenght = str.length();
        String num = "";
        for(int i=0;i<lenght;i++){
            num += str.charAt(i);
            Matcher m = p.matcher(num);
            if(m.find()){
                indexNum = i;
                break;
            }
        }
        String formatNumber = str.substring(indexNum,lenght);
        return formatNumber;
    }
}