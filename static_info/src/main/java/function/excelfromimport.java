package function;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.*;

public class excelfromimport {

    private static final String XLS = ".xls";
    private static final String XLSX = ".xlsx";

    /**
     * 根据文件后缀获取对应Workbook对象
     * @param filePath
     * @param fileType
     * @return
     */
    public static Workbook getWorkbook(String filePath, String fileType){
        Workbook workbook = null;
        FileInputStream fileInputStream = null;
        try{
            File excelFile = new File(filePath);
            if(!excelFile.exists()){
                //logger.info(filePath+"文件不存在");
                return null;
            }
            fileInputStream = new FileInputStream(excelFile);
            if(fileType.equalsIgnoreCase(XLS)){

                workbook = new HSSFWorkbook(fileInputStream);
            }else if(fileType.equalsIgnoreCase(XLSX)){
                System.out.println("进来");
                workbook = new XSSFWorkbook(fileInputStream);
            }
        }catch (Exception e){
           // logger.error("获取文件失败",e);
        }finally {
            try {
                if (null != fileInputStream) {
                    fileInputStream.close();
                }
            } catch (Exception e) {
                //logger.error("关闭数据流出错！错误信息：" , e);
                return null;
            }
        }
        return workbook;
    }

    public static ArrayList<ArrayList<String>> readFolder(String filePath){
        int fileNum = 0;
        File file = new File(filePath);
       // List<Object> returnList = new ArrayList<>();
        ArrayList<ArrayList<String>> arrayLists = new ArrayList<ArrayList<String>>();
        //List<Map<String,String>> resultList = new ArrayList<>();
        ArrayList<String> resultList = new ArrayList<String>();
        if (file.exists()) {
            File[] files = file.listFiles();
            for (File file2 : files) {
                if (file2.isFile()) {
                    resultList = readExcel(file2.getAbsolutePath());
                    //System.out.println(file2.getAbsolutePath());
                    arrayLists.add(resultList);
                    fileNum++;
                }
            }
        } else {
            System.out.println("文件夹不存在");
            return null;
        }
       // logger.info("共有文件："+fileNum);
        return arrayLists;
    }

    /**
     * 批量读取Excel文件，返回数据对象
     * @param filePath
     * @return
     */
    public static ArrayList<String> readExcel(String filePath){
        Workbook workbook = null;
       // List<Map<String,String>> resultList = new ArrayList<>();
        try{
            String fileType = filePath.substring(filePath.lastIndexOf("."));
            //System.out.println(fileType);
            workbook = getWorkbook(filePath,fileType);
            if(workbook == null){
              //  logger.info("获取workbook对象失败");
                return null;
            }
            //resultList = analysisExcel(workbook);
            return analysisExcel(workbook);
        }catch (Exception e){
           // logger.error("读取Excel文件失败"+filePath+"错误信息",e);
            return null;
        }finally {
            try {
                if (null != workbook) {
                    workbook.close();
                }
            } catch (Exception e) {
              //  logger.error("关闭数据流出错！错误信息：" , e);
                return null;
            }

        }
    }

    /**
     * 解析Excel文件，返回数据对象
     * @param workbook
     * @return
     */
    public static ArrayList<String> analysisExcel(Workbook workbook){
        ArrayList<String> dataoneline = new ArrayList<String>();
        int m = 0;
       // int sheetCount = workbook.getNumberOfSheets();//或取一个Excel中sheet数量
        for(int i = 0 ; i < 1 ; i ++){
            Sheet sheet = workbook.getSheetAt(i);

            if(sheet == null){
                continue;
            }
            int firstRowCount = sheet.getFirstRowNum();//获取第一行的序号
            //System.out.println(firstRowCount);
            Row firstRow = sheet.getRow(firstRowCount);
            int cellCount = firstRow.getLastCellNum();//获取列数
            //System.out.println(cellCount);

            //解析每一行数据，构成数据对象
            NumberFormat nf=NumberFormat.getPercentInstance();
            try {
                Number userId=nf.parse(getCellVal(sheet.getRow(1).getCell(7)));
                //System.out.println(new String(String.valueOf(userId)));
                dataoneline.add(new String(String.valueOf(userId)));
            } catch (ParseException e) {
                e.printStackTrace();
            }



            for(int j = 4 ; j < 17 ; j++){
                //System.out.println("进来");
                Row row = sheet.getRow(j);//获取对应的row对象
                for(int k = 1 ; k < 6 ; k++){
                    if((j == 5 && k == 1) || (j == 5 && k == 2) || (j == 5 && k == 3) || (j == 5 && k == 4) || (j == 6 && k == 1) ||
                            (j == 6 && k == 2) || (j == 6 && k == 3) || (j == 6 && k == 4) || (j == 7 && k == 1) || (j == 7 && k == 2) ||
                            (j == 7 && k == 3) || (j == 8 && k == 1) || (j == 8 && k == 2) || (j == 8 && k == 3) || (j == 8 && k == 4) ||
                            (j == 9 && k == 1) || (j == 9 && k == 2) || (j == 9 && k == 3) || (j == 9 && k == 4) || (j == 10 && k == 1) ||
                            (j == 10 && k == 5) || (j == 11 && k == 1) || (j == 11 && k == 2) || (j == 11 && k == 3) || (j == 11 && k == 5) ||
                            (j == 12 && k == 5) || (j == 13 && k == 1) || (j == 13 && k == 5) || (j == 14 && k == 1) || (j == 14 && k == 2) ||
                            (j == 14 && k == 3) || (j == 14 && k == 5) || (j == 15 && k == 5) || (j == 16 && k == 1) || (j == 16 && k == 5) ){
                        continue;
                    }
                    //System.out.println(j +" " +  k + " " + getCellVal(row.getCell(k)));
                    dataoneline.add(getCellVal(row.getCell(k)));
                }
            }
        }
        return dataoneline;
    }

    /**
     * 获取单元格的值
     * @param cel
     * @return
     */
    public static String getCellVal(Cell cel) {
        if(cel.getCellType() == Cell.CELL_TYPE_STRING) {
            return cel.getRichStringCellValue().getString();
        }
        if(cel.getCellType() == Cell.CELL_TYPE_NUMERIC) {
            NumberFormat percent = NumberFormat.getPercentInstance();
            percent.setMaximumFractionDigits(2); //保留多少位
            String result = percent.format(cel.getNumericCellValue()).toString();
            return result + "";
        }
        if(cel.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
            return cel.getBooleanCellValue() + "";
        }
        if(cel.getCellType() == Cell.CELL_TYPE_FORMULA) {
            return cel.getCellFormula() + "";
        }
        return cel.toString();
    }


    //批量读取excel文件
    public static void getAllExcelinfo(String filepath) {
        ArrayList<ArrayList<String>> returnList = readFolder(filepath);
        System.out.println(returnList.size());
        System.out.println(returnList.get(1).size());
    }

}
