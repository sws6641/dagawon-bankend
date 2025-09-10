package com.bankle.common.asis.component;//package kr.co.anbu.component;
//
//import kr.co.anbu.utils.FileCustUtils;
//import lombok.extern.slf4j.Slf4j;
//
//import javax.crypto.*;
//import javax.crypto.spec.IvParameterSpec;
//import java.io.*;
//import java.security.InvalidAlgorithmParameterException;
//import java.security.InvalidKeyException;
//import java.security.NoSuchAlgorithmException;
//
//@Slf4j
//public class FileEncoder {
//
//    private SecretKey secretKey;
//    private Cipher cipher;
//
//    public FileEncoder(SecretKey secretKey, String transformation) throws NoSuchAlgorithmException, NoSuchPaddingException {
//        this.secretKey = secretKey;
//        this.cipher = Cipher.getInstance(transformation);
//    }
//
//    /**
//     * 파일 암호화
//     * @param storedImgFilePath
//     * @param storedEncFilePath
//     * @throws InvalidKeyException
//     * @throws IOException
//     */
//    public void encrypt(String storedImgFilePath, String storedEncFilePath) throws InvalidKeyException, IOException{
//
//        byte[] bytes = FileCustUtils.readFileToByteArray(new File(storedImgFilePath));
//        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
//        byte[] iv = cipher.getIV();
//
//        try (FileOutputStream fileOut = new FileOutputStream(storedEncFilePath);
//            CipherOutputStream cipherOutputStream = new CipherOutputStream(fileOut, cipher)){
//
//            fileOut.write(iv);
//            cipherOutputStream.write(bytes);
//        }
//    }
//
//    /**
//     * 파일 복호화
//     * @param fileName
//     * @return
//     * @throws IOException
//     * @throws InvalidKeyException
//     * @throws InvalidAlgorithmParameterException
//     */
//    public byte[] decrypt(String fileName) throws IOException, InvalidKeyException, InvalidAlgorithmParameterException{
//        try(FileInputStream fileIn = new FileInputStream(fileName)){
//            byte[] fileIv = new byte[16];
//            fileIn.read(fileIv);
//
//            cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(fileIv));
//
//            try(
//                    CipherInputStream cipherInputStream = new CipherInputStream(fileIn, cipher);
//                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//                    ){
//
//                int length;
//                byte[] buffer = new byte[4096];
//                while((length = cipherInputStream.read(buffer, 0, buffer.length)) != -1){
//                    byteArrayOutputStream.write(buffer, 0, length);
//                }
//
//                byteArrayOutputStream.flush();
//
//                return byteArrayOutputStream.toByteArray();
//            }
//        }
//    }
//
//}
