package com.bankle.common.asis.infra;

import com.bankle.common.asis.domain.entity.NotiMaster;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class BoardRequest {
    private MultipartFile[] files;
    private NotiMaster notiMaster;
}
