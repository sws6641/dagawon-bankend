package com.bankle.common.userAuth;


import jakarta.validation.constraints.NotBlank;
import lombok.*;

public class UserAuthVo {

    private UserAuthVo() {
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Info {
        private int id;
        private String name;
        private int age;
    }

    @Getter
    @Setter
    public static class Req {
        private String name;
        private int age;
    }

    @Getter
    @AllArgsConstructor
    public static class Res {
        private Info info;
        private int returnCode;
        private String returnMessage;
    }
    /*
     * 사용자 생성
     */
    @Getter
    @AllArgsConstructor
    public static class CreateReq {
        private String id;
        @NotBlank(message = "Email은 필수 항목입니다.")
        private String email;
        private String name;
        private String nickname;
        private String profileImageUrl;
        private String brithday;
        private String phoneNumber;
        private String role;
        private String refreshAccess;
    }


    /*
     * 사용자 생성
     */
    @Getter
    @AllArgsConstructor
    public static class UpdateReq {
        @NotBlank(message = "ID는 필수 항목입니다.")
        private String id;
        private String email;
        private String name;
        private String nickname;
        private String profileImageUrl;
        private String brithday;
        private String phoneNumber;
        private String role;
        private String refreshAccess;
    }
}
