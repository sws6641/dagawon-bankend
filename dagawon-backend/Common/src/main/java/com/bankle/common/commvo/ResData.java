package com.bankle.common.commvo;

import com.bankle.common.config.Const;
import lombok.*;
import org.springframework.http.ResponseEntity;


@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResData<T> {
    private String code;
    private String msg;
    private T data;

    public static ResponseEntity<?> SUCCESS(Object data){
        return ResponseEntity.ok().body(ResData.builder().code(Const.RESULT_CODE_SUCCESS).data(data).build());
    }

    public static ResponseEntity<?> SUCCESS(Object data, String msg) {
        return ResponseEntity.ok().body(ResData.builder().code(Const.RESULT_CODE_SUCCESS).data(data).msg(msg).build());
    }

    public static ResponseEntity<?> FAIL(String msg){
        
        // Fail 메세지는 절때  ":" 를 넣어선 안된다.
//        if(msg.contains(": ")){
//            // ": " 를 나눠서 1번째 메세지만 출력한다.
//            msg = msg.split(": ")[1];
//        }
        
        return ResponseEntity.ok().body(ResData.builder().code(Const.RESULT_CODE_FAIL).msg(msg).build());
    }
    public static ResponseEntity<?> FAIL(Object data, String msg){

        // Fail 메세지는 절때  ":" 를 넣어선 안된다.
//        if(msg.contains(": ")){
//            // ": " 를 나눠서 1번째 메세지만 출력한다.
//            msg = msg.split(": ")[1];
//        }
        return ResponseEntity.ok().body(ResData.builder().code(Const.RESULT_CODE_FAIL).data(data).msg(msg).build());
    }
}
