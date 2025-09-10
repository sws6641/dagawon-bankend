package com.bankle.common.asis.component.excel;

import com.bankle.common.asis.component.excel.ExcelCellRef;
import com.bankle.common.asis.component.excel.ExcelFileType;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class ExcelRead {

    public static List<Map<String, Object>> read(ExcelReadOption excelReadOption){

        List<Map<String, Object>> rowList = new ArrayList<>();

        try {
            Workbook workbook = ExcelFileType.getWorkbook(excelReadOption.getFilePath());
            Sheet sheet = workbook.getSheetAt(0);
            log.info("이름 >> " + workbook.getSheetName(0));
            log.info("갯수 >> " + workbook.getNumberOfSheets());

            int rows = sheet.getPhysicalNumberOfRows();

            for(int rowIdx = excelReadOption.getStartRow() - 1; rowIdx < rows; rowIdx++){

                Row row = sheet.getRow(rowIdx);

                if(row != null){

                    Map<String, Object> columnMap = new HashMap<>();

                    for(int cellIdx = 0; cellIdx < row.getLastCellNum(); cellIdx++){

                        //cell index에 해당하는 cell
                        Cell cell = row.getCell(cellIdx);
                        String cellName = ExcelCellRef.getName(cell, cellIdx);

                        if(!excelReadOption.getOutputColumns().contains(cellName))
                            continue;

                        columnMap.put(cellName, ExcelCellRef.getValue(cell));
                    }

                    rowList.add(columnMap);
                }
            }



        } catch (IOException e) {
            log.error(e.getMessage(),e);
        }

        return rowList;
    }
}
