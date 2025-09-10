package com.bankle.common.asis.component.excel;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.ss.usermodel.Cell;

@Slf4j
public class ExcelCellRef {

    public static String getName(Cell cell, int cellIndex) {
        return (cell == null) ?
                CellReference.convertNumToColString(cellIndex) :
                CellReference.convertNumToColString(cell.getColumnIndex());
    }

    public static String getValue(Cell cell) {

        if(cell == null)
            return "";

        switch(cell.getCellType()) {
            case Cell.CELL_TYPE_FORMULA :
                return cell.getCellFormula();

            case Cell.CELL_TYPE_NUMERIC :
                return String.valueOf(cell.getNumericCellValue());

            case Cell.CELL_TYPE_STRING :
                return cell.getStringCellValue();

            case Cell.CELL_TYPE_BOOLEAN :
                return String.valueOf(cell.getBooleanCellValue());

            case Cell.CELL_TYPE_BLANK :
                return "";

            case Cell.CELL_TYPE_ERROR :
                return String.valueOf(cell.getErrorCellValue());

            default:
                return cell.getStringCellValue();
        }

    }
}
