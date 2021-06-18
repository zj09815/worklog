package com.uwntek.worklog.util;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;

/**
 * POI Excel工具类
 * created on 2020/5/28 10:08
 *
 * @author puhaiyang
 */
public class POIExcelUtil {

    public static void cloneSheet(File excelFile, String srcSheetName, String destSheetName) {
        Workbook sheets = readExcelFromFile(excelFile);
        int index = sheets.getSheetIndex(srcSheetName);
        cloneSheet(excelFile, index, destSheetName);
    }

    public static void cloneSheet(File excelFile, Integer index, String destSheetName) {
        Workbook sheets = readExcelFromFile(excelFile);
        //克隆一个新的sheet
        Sheet newSheet = sheets.cloneSheet(index);
        int sheetIndex = sheets.getSheetIndex(newSheet);
        sheets.setSheetName(sheetIndex, destSheetName);
        try {
            FileOutputStream out = new FileOutputStream(excelFile);
            out.flush();
            sheets.write(out);
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //读取excel
    public static Workbook readExcelFromFile(File file) {
        if (file == null) {
            return null;
        }
        try {
            return new XSSFWorkbook(new FileInputStream(file));
        } catch (IOException e) {
            throw new RuntimeException("文件解析失败");
        }
    }
}


