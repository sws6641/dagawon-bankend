package com.bankle.common.asis.component.excel;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

;

@Slf4j
public class ExcelFileType {

    public static Workbook getWorkbook(String filePath) throws FileNotFoundException, IOException {
        FileInputStream fis = new FileInputStream(filePath);
        return (filePath.toUpperCase().endsWith(".XLS")) ? new HSSFWorkbook(fis) : new XSSFWorkbook(fis);
    }
}
