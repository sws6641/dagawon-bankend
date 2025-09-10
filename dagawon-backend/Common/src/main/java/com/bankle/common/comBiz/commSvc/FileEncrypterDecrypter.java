package com.bankle.common.comBiz.commSvc;

import lombok.extern.slf4j.Slf4j;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.io.*;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;


@Slf4j
public class FileEncrypterDecrypter {

    private final SecretKey secretKey;
    private final Cipher cipher;

    public FileEncrypterDecrypter(SecretKey secretKey) throws NoSuchAlgorithmException, NoSuchPaddingException {
        this.secretKey = secretKey;
        this.cipher = Cipher.getInstance("AES/CBC/PKCS5Padding"); // AES 알고리즘 및 CBC 모드 사용
    }



    public void encrypt(String storedImgFilePath, String sotredEncFilePath) throws InvalidKeyException, FileNotFoundException, IOException {

        byte[] tiffBytes = org.apache.commons.io.FileUtils.readFileToByteArray(new File(storedImgFilePath));

        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] iv = cipher.getIV();

        try (FileOutputStream fileOut = new FileOutputStream(sotredEncFilePath);
             CipherOutputStream cipherOut = new CipherOutputStream(fileOut, cipher)) {
            fileOut.write(iv);
            // cipherOut.write(images.get(0).getBytes());
            cipherOut.write(tiffBytes);
        }

    }

    public byte[] decrypt(String fileName) throws FileNotFoundException, IOException, InvalidKeyException, InvalidAlgorithmParameterException {
        try (FileInputStream fileIn = new FileInputStream(fileName)) {
            byte[] fileIv = new byte[16];
            fileIn.read(fileIv);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(fileIv));

            try (
                    CipherInputStream cipherIn = new CipherInputStream(fileIn, cipher);
                    // InputStreamReader inputReader = new InputStreamReader(cipherIn);
                    // BufferedReader reader = new BufferedReader(inputReader)

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ) {
                int len;
                byte[] buffer = new byte[4096];
                while ((len = cipherIn.read(buffer, 0, buffer.length)) != -1) {
                    baos.write(buffer, 0, len);
                }
                baos.flush();

                return baos.toByteArray();
            }
        } catch (FileNotFoundException fnfe) {
            log.debug(" # {} 파일을 찾지 못함", fileName);
            return new ByteArrayOutputStream().toByteArray();
        }
    }

}
