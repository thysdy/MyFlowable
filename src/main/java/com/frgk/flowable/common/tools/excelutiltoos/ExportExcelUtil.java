package com.frgk.flowable.common.tools.excelutiltoos;

import com.frgk.flowable.common.tools.DateTools;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class ExportExcelUtil {
    /**
     * 根据属性名获取属性值
     */
    public static Object getFieldValueByName(String fieldName, Object o) {
        try {

            String[] fieldNameSplit = fieldName.split("\\.");
            Object value = o;
            for (String name : fieldNameSplit) {
                String firstLetter = name.substring(0, 1).toUpperCase();
                String getter = "get" + firstLetter + name.substring(1);
                Method method = value.getClass().getMethod(getter);
                value = method.invoke(value);
            }

            if (value instanceof Date) {
                return DateTools.format((Date) value, DateTools.DEFAULT_DATE_PATTERN2);
            }
            if (value != null) {
                return String.valueOf(value);
            } else {
                return "";
            }
        } catch (Exception e) {
            return null;
        }
    }

    public static<T> void export(String excelName, String sheetName,String[] title, String[] colName, List<T> objList, HttpServletResponse response){
        try {
            HSSFWorkbook wb = new HSSFWorkbook();

            HSSFSheet sheet = wb.createSheet(sheetName);

            HSSFRow row = null;

            row = sheet.createRow(0);
            row.setHeight((short) (30 * 20));
            row.createCell(0).setCellValue(sheetName);


            CellRangeAddress rowRegion = new CellRangeAddress(0, 0, 0, 2);
            sheet.addMergedRegion(rowRegion);
            row = sheet.createRow(1);
            //设置行高
            row.setHeight((short) (22.50 * 20));
            int length=colName.length;
            for(int i=0;i<length;i++){
                row.createCell(i).setCellValue(title[i]);
            }


            for (int j = 0; j < objList.size(); j++) {
                Object o = objList.get(j);
                List<String> list = new ArrayList();
                for (int i = 0; i < colName.length; i++) {

                    list.add((String) getFieldValueByName(colName[i], o));
                }
                row = sheet.createRow(j + 2);

                for (int i = 0; i < list.size(); i++) {

                    row.createCell(i).setCellValue(list.get(i));

                }
            }

            sheet.setDefaultRowHeight((short) (16.5 * 20));
            //列宽自适应
            for (int i = 0; i <= 13; i++) {
                sheet.autoSizeColumn(i);
            }
            String newtitle= excelName;
            String  fileName = new String(newtitle.getBytes("GB2312"), "ISO_8859_1");
            fileName = URLEncoder.encode(fileName,"utf-8");

            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            OutputStream os = response.getOutputStream();
            //默认Excel名称
            response.setHeader("Content-disposition", "attachment;filename="+fileName+".xls");
            wb.write(os);
            os.flush();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }



    }
}
