package com.bankle.common.commvo;

import lombok.*;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResPagingData<T> {

    private T data;
}
