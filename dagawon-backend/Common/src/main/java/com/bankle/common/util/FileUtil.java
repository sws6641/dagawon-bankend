package com.bankle.common.util;

import com.bankle.common.config.ImgConfig;
import com.bankle.common.dto.TbEscrFileDto;
import com.bankle.common.entity.TbEscrFile;
import com.bankle.common.mapper.TbEscrFileMapper;
import com.bankle.common.repo.TbEscrFileRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Optional;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileUtil extends FileUtils {

    private final TbEscrFileRepository tbEscrFileRepository;
    /**
     * 확장자 조회
     *
     * @param fileName
     * @return
     */
    public static String getFileExt(String fileName) {
        return StringUtils.substringAfter(fileName, ".");
    }

    public static void checkValidPath(String path) {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    /**
     * 파일 압축
     *
     * @param path        압축할 파일의 위치
     * @param files       압축할 파일 리스트
     * @param outFilePath 압축한 파일의 저장 위치
     * @param outFileName 압축한 파일명
     * @return
     */
    public static boolean compressZip(String path, String[] files, String outFilePath, String outFileName) {
        boolean result = false;

        ZipOutputStream zout = null;
        if (files.length > 0) {
            try {
                zout = new ZipOutputStream(new FileOutputStream(outFilePath + outFileName));
                FileInputStream in;

                for (String s : files) {
                    File file = new File(path + s);
                    byte[] buffer = new byte[(int) file.length()];

                    in = new FileInputStream(path + s);
                    zout.putNextEntry(new ZipEntry(s));

                    int len;
                    while ((len = in.read(buffer)) > 0) {
                        zout.write(buffer, 0, len);
                    }

                    zout.closeEntry();
                    in.close();
                }

                zout.close();

                result = true;

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (zout != null) {
                    zout = null;
                }
            }
        }

        return result;
    }

    /**
     * 새파일 이름 + 확장자
     *
     * @param orgFileNm
     * @return
     */
    public static String getNewFileNameWithExt(String orgFileNm) {
        String orgFileExt = StringUtils.substringAfter(orgFileNm, ".");
        return getNewFileName() + "." + orgFileExt;
    }

    /**
     * 새파일 이름 (날짜)
     *
     * @return
     */
    private static String getNewFileName() {
        return DateUtil.getThisDate("yyyymmddHHmmss") + StringUtil.getRandomString().substring(0, 4);
    }

    /**
     * 이미지 리사이징
     *
     * @param srcFile
     * @param tgtFile
     * @param tgtSize
     * @return
     */
    public static File imageResize(File srcFile, File tgtFile, int tgtSize) {

        log.info("=============================================================================");
        log.info("=============================================================================");
        log.info("IMAGE RESIZE START !!!  ( ewframework.cmm.FileUtilSNGL.imageResize)          ");

        String srcFileName = srcFile.getName();
        String tgtFileName = tgtFile.getName();
        String ext = tgtFileName.substring(tgtFileName.lastIndexOf(".")).replace(".", "");
        int srcWidth;
        int srcHeight;
        int resizeWidth;
        int resizeHeight;

        log.info("srcFileName [" + srcFileName + "]   tgtFileName [" + tgtFileName + "]   ext [" + ext + "]");

        try {

            Image srcImage = ImageIO.read(srcFile);
            srcWidth = srcImage.getWidth(null);
            srcHeight = srcImage.getHeight(null);

            if (srcWidth > srcHeight) {

                resizeWidth = tgtSize;
                resizeHeight = (tgtSize * srcHeight) / srcWidth;
            } else {
                resizeHeight = tgtSize;
                resizeWidth = (tgtSize * srcWidth) / srcHeight;
            }

            log.info("WIDTH  (source >> target >> resize) : " + srcWidth + " >> " + tgtSize + " >> " + resizeWidth);
            log.info("HEIGHT (source >> target >> resize) : " + srcHeight + " >> " + tgtSize + " >> " + resizeHeight);

            Image resizeImage = srcImage.getScaledInstance(resizeWidth, resizeHeight, Image.SCALE_SMOOTH);
            BufferedImage newImage = new BufferedImage(resizeWidth, resizeHeight, BufferedImage.TYPE_INT_RGB);
            Graphics g = newImage.getGraphics();
            g.drawImage(resizeImage, 0, 0, null);
            g.dispose();
            ImageIO.write(newImage, ext, tgtFile);

        } catch (Exception Ex) {
            log.info("Image ReSize Exception : " + Ex.getMessage());
        }

        return tgtFile;
    }

    /**
     * command 실행(예: mv)
     *
     * @param cmd
     */
    public static void execCommand(String[] cmd) {

        try {
            Process proc = Runtime.getRuntime().exec(cmd);
            proc.waitFor();
            if (proc.exitValue() != 0) {
                log.info("exit value was non-zero");
            }
            log.info("===== 실행완료");
        } catch (IOException ie) {
            log.info("IOException 발생");
        } catch (InterruptedException e) {
            log.info("InterruptedException 발생");
        }
    }

    public static String getFileToStringBase64(String filePath) throws Exception {

        if ("".equals(StringUtil.nvl(filePath))) return "File Path 누락";

        String filetoString = "";

        try {
            byte[] byteFile = getFileBinary(filePath);

            // base64의 라이브러리에서 encodeToString를 이용해서 byte[] 형식을 String 형식으로 변환합니다.
            filetoString = Base64.getEncoder().encodeToString(byteFile);
        } catch (Exception Ex) {
            Ex.printStackTrace();
        }

        return filetoString;
    }

    // 파일 읽어드리는 함수
    private static byte[] getFileBinary(String filepath) {
        File file = new File(filepath);
        byte[] data = new byte[(int) file.length()];

        try (FileInputStream stream = new FileInputStream(file)) {
            stream.read(data, 0, data.length);
        } catch (Throwable Ex) {
            Ex.printStackTrace();
        }

        return data;
    }

    public static String makeStringToFile(String filePath, String strBase64) {

        String rtnMsg = "fail";

        try {
            byte[] byteFile = Base64.getDecoder().decode(strBase64);

            Path path = Paths.get(filePath);
            Files.write(path, byteFile);

            rtnMsg = "success";

        } catch (Exception Ex) {
            Ex.printStackTrace();
        }

        return rtnMsg;
    }

    /*
       이미지 업무 코드별 그룹번호를 생성한다.
       */
    public String getGrpNo(String wkCd, String fileCd) {
        long grpNo = tbEscrFileRepository
                .findTopByWkCdAndFileCdOrderByGrpNoDesc(wkCd, fileCd)
                .map(file -> Long.parseLong(file.getGrpNo()) + 1)
                .orElse(0L); // 기존 그룹번호가 없을 경우 0부터 시작
        return String.valueOf(grpNo);
    }


    /**
     * 파일경로 반환 서비스
     *
     * @param : 업무코드,파일코드
     * @return : 파일경로
     */
    public static String getloadDir(String wkCd, String filCd) throws Exception {

        // 파일경로
        String dir = "";
        //업무코드분류 //IMAGE_BIZ : 업무관련이미지, IMAGE_CUST: 회원관련이미지
        if ("IMAGE_BIZ".equals(wkCd)) {
            switch (filCd) {
                //등기부등본
                case "01" -> dir = ImgConfig.IMG_PATH_BIZ + File.separator + "01";
                //경로 오류 저장경로
                default -> dir = ImgConfig.IMG_PATH_BIZ + File.separator + "99";
            }
        } else if ("IMAGE_CUST".equals(wkCd)) {
            switch (filCd) {
                //경로 오류 저장경로
                default -> dir = ImgConfig.IMG_PATH_CUST + File.separator + "99";
            }
        }
        return dir;
    }



}
