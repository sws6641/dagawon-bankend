package com.bankle.common.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ImgConfig {

    //업무 이미지 경로
    public static String IMG_PATH_BIZ;
    //회원 이미지 경로
    public static String IMG_PATH_CUST;

    @Value("${data.files.biz}")
    public void setIMG_PATH_BIZ(String imgPathBiz){IMG_PATH_BIZ = imgPathBiz;}

    @Value("${data.files.cust}")
    public void setIMG_PATH_CUST(String imgPathCust){IMG_PATH_CUST = imgPathCust;}

}
