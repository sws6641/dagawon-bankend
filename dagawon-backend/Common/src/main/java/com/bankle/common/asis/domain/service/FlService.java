package com.bankle.common.asis.domain.service;

import com.bankle.common.asis.domain.entity.FlDetail;
import com.bankle.common.asis.domain.entity.FlMaster;
import com.bankle.common.asis.domain.repositories.FlMasterRepository;
import com.bankle.common.util.FileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class FlService {

//    @Value("${file.dir.board}")
    private String uploadPath;

    private final FlMasterRepository flMasterRepository;

    /**
     * 파일 저장
     *
     * @param files
     * @return
     */
    public FlMaster uploadFile(MultipartFile[] files, Long flMKey) {

        Optional<FlMaster> byId = flMasterRepository.findById(flMKey);
        List<FlDetail> flDetails;

        FlMaster master;
        if (byId.isPresent()) {
            master = byId.get();
            flDetails = master.getFlDetails();
        } else {
            master = FlMaster.builder().build();
            flDetails = new ArrayList<>();
        }

        for (MultipartFile file : files) {

            //원본 파일
            String originalName = file.getOriginalFilename();
            String fileName = originalName.substring(originalName.lastIndexOf("\\") + 1);

            //업로드 패스 체크
            FileUtil.checkValidPath(uploadPath);

            //파일 저장
            String newFileName = FileUtil.getNewFileNameWithExt(fileName);
            Path savePath = Paths.get(uploadPath + newFileName);
            try {
                file.transferTo(savePath);
            } catch (IOException e) {
                e.printStackTrace();
            }

            flDetails.add(FlDetail.builder()
                    .flMKey(flMKey)
                    .flNm(newFileName)
                    .flOgnNm(FileUtil.getNewFileNameWithExt(fileName))
                    .flExt(FileUtil.getFileExt(originalName))
                    .flPth(savePath.toString())
                    .flSz(file.getSize())
                    .build());
        }

        master.setFlDetails(flDetails);

        return flMasterRepository.save(master);
    }

    /**
     * 파일 조회
     *
     * @param flMKey
     * @return
     */
    public FlMaster getFiles(Long flMKey) {
        return flMasterRepository.findById(flMKey).orElse(null);
    }
}
