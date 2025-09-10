//package kr.co.anbu.controller;
//
//import kr.co.anbu.infra.Response;
//import kr.co.anbu.utils.FileCustUtils;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import net.coobird.thumbnailator.Thumbnailator;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.util.FileCopyUtils;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.File;
//import java.io.IOException;
//import java.net.URLDecoder;
//import java.nio.charset.StandardCharsets;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.util.ArrayList;
//import java.util.List;
//
//@RestController
//@Slf4j
//@RequiredArgsConstructor
//@RequestMapping("/api/files")
//public class FileCmnRestController {
//
//    @Value("${file.dir.board}")
//    private String uploadPath;
//    private final Response response;
//
//    /**
//     * 파일 업로드
//     * @param uploadFiles
//     */
//    @PostMapping("/upload")
//    public ResponseEntity<?> uploadFile(MultipartFile[] uploadFiles){
//
//        List<String> newFileNames = new ArrayList<>();
//        for (MultipartFile uploadFile : uploadFiles) {
//
//            String originalName = uploadFile.getOriginalFilename();
//            String fileName = originalName.substring(originalName.lastIndexOf("\\") + 1);
//
//            log.info("fileName : " + fileName);
//
//            FileCustUtils.checkValidPath(uploadPath);
//
//            //원본 파일
//            String newFileName = FileCustUtils.getNewFileNameWithExt(fileName);
//            log.info("newFileName : " + newFileName);
//
//            //썸네일 파일
//            String thumbNailSaveName = uploadPath + "s_"+newFileName;
//            log.info("thumbNailSaveName : " + thumbNailSaveName);
//            File thumbNailFile = new File(thumbNailSaveName);
//
//            Path savePath = Paths.get(uploadPath + newFileName);
//            try{
//
//                uploadFile.transferTo(savePath);
//                Thumbnailator.createThumbnail(savePath.toFile(), thumbNailFile, 100, 100);
//
//            }catch (IOException e){
//                e.printStackTrace();
//            }
//
//            newFileNames.add(newFileName);
//        }
//
//        return response.success(newFileNames, "success", HttpStatus.OK);
//    }
//
//    /**
//     * 파일 디스플레이
//     * @param fileName
//     * @return
//     */
//    @GetMapping("/display")
//    public ResponseEntity<byte[]> getFiles(String fileName){
//
//        ResponseEntity<byte[]> result;
//
//        try{
//            String srcFileName = URLDecoder.decode(fileName, StandardCharsets.UTF_8);
//            log.info("fileName : "+srcFileName);
//
//            File file = new File(uploadPath + srcFileName);
//            log.info("file : "+file);
//
//            HttpHeaders headers = new HttpHeaders();
//            headers.add("Content-Type", Files.probeContentType(file.toPath()));
//
//            result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), headers, HttpStatus.OK);
//
//        }catch (Exception e){
//            log.error(e.getMessage());
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//
//        return result;
//    }
//
//    /**
//     * 파일 삭제
//     * @param fileName
//     * @return
//     */
//    @PostMapping("/remove")
//    public ResponseEntity<Boolean> removeFile(String fileName){
//
//        String srcFileName;
//        srcFileName = URLDecoder.decode(fileName, StandardCharsets.UTF_8);
//        File file = new File(uploadPath + srcFileName);
//        boolean result = file.delete();
//
//        File thumbnail = new File(file.getParent(), "s_" + file.getName());
//        result = thumbnail.delete();
//
//        return new ResponseEntity<>(result, HttpStatus.OK);
//
//    }
//}
