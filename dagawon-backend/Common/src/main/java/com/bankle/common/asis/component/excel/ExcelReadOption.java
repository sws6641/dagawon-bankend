package com.bankle.common.asis.component.excel;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Getter @Setter
@Slf4j
@RequiredArgsConstructor
public class ExcelReadOption {

    //엑셀파일 경로
    private String filePath;
    //추출 컬럼 명

    private List<String> outputColumns;
    //추출 시작 행 번호
    private int startRow;

     public void setOutputColumns(String... outputColumns){
         if(outputColumns == null){
             this.outputColumns = new ArrayList<>();
         }

         for (String outputColumn : outputColumns) {
             this.outputColumns.add(outputColumn);
         }
     }
}
