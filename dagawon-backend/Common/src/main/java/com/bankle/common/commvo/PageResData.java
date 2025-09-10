package com.bankle.common.commvo;

import lombok.*;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageResData {

    private PageData pageData;


    @Data
    @ToString
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PageData {

        private int currPageNum;

        private int totalPageNum;

        private int pageSize;

        private boolean isLast;

        private long totalElements;

    }
}


