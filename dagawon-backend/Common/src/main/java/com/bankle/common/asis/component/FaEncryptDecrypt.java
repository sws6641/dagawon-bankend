package com.bankle.common.asis.component;

import java.security.MessageDigest;
import java.security.Security;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FaEncryptDecrypt {
    
    public FaEncryptDecrypt() {
        Security.addProvider(new BouncyCastleProvider());   
    }
 
    private static String bytesToHex(byte[] hash) {
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < hash.length; i++) {
        String hex = Integer.toHexString(0xff & hash[i]);
        if(hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }
 
    
    public String makeHMAC(String PassPhase, String plainText) {
        
        byte[] hmac = null;
        
        try {
            
            byte[] secretKey = PassPhase.getBytes();
            SecretKeySpec signingKey = new SecretKeySpec(secretKey, "HmacSHA256");
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(signingKey);
            String base64 = Base64.getEncoder().encodeToString(plainText.getBytes("UTF-8")); // 문의 할 내용
            hmac = mac.doFinal(base64.getBytes("UTF-8"));
            return bytesToHex(hmac);            
                                
        } catch (Exception e) {
            
            log.debug(" # PassPhase  : [{}]",  PassPhase);
            log.debug(" # plainText  : [{}]",  plainText);
            log.debug(" # hmac       : [{}]",  Base64.getEncoder().encodeToString(hmac));
            log.debug(" # bytesToHex : [{}]",  bytesToHex(hmac));    
            
            return "Failed";
        }
    }
    

    
    public String encrypt(String PassPhase, String planeText) {
        
        byte[] salt      = null;
        String plaintext = "";
        
        try {                                           
            //PassPhase UTF-8로 변환 하여 Hash 데이터를 만든다.         
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            salt = digest.digest(PassPhase.getBytes("UTF-8"));
                    
            //PBEKeySpec 객체로 PassPhase와 hash 사용해서 65535번 수행하여 32byte의 Key와 IV를 생성한다. 
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            //384 means 32 + 16 bytes in bits. key : 256 bit + 128bit = 384
            PBEKeySpec pbeKeySpec = new PBEKeySpec(PassPhase.toCharArray(), salt, 2478, 384);
            SecretKey secretKey = factory.generateSecret(pbeKeySpec);           
            byte[] key = new byte[32];
            byte[] iv = new byte[16];       
            System.arraycopy(secretKey.getEncoded(), 0, key, 0, 32);
            System.arraycopy(secretKey.getEncoded(), 32, iv, 0, 16);
            
            SecretKeySpec secret = new SecretKeySpec(key, "Rijndael");  
                        
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");
            AlgorithmParameterSpec ivSpec = new IvParameterSpec(iv);

            cipher.init(Cipher.ENCRYPT_MODE, secret, ivSpec);
                
            byte[] result = cipher.doFinal(planeText.getBytes("UTF-8"));            
                    
            //Encrypt 확인 코드
            cipher.init(Cipher.DECRYPT_MODE, secret, new IvParameterSpec(iv));          
            plaintext = new String(cipher.doFinal(result), "UTF-8");            
            
            return Base64.getEncoder().encodeToString(result);
            
        } catch (Exception e) {
            
            log.debug(" # PassPhase  : [{}]",  PassPhase);
            log.debug(" # plainText  : [{}]",  planeText);
            log.debug(" # salt       : [{}]",  salt);
            log.debug(" # plaintext  : [{}]",  plaintext);               
            
            return "Failed";
        }               
    }   
    
    public String Decrypt(String PassPhase, String planeText) {
        
        byte[] salt = null;
        
        try {       
            
            //PassPhase UTF-8로 변환 하여 Hash 데이터를 만든다.         
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            salt = digest.digest(PassPhase.getBytes("UTF-8"));
                    
            //PBEKeySpec 객체로 PassPhase와 hash 사용해서 65535번 수행하여 32byte의 Key와 IV를 생성한다. 
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            //384 means 32 + 16 bytes in bits. key : 256 bit + 128bit = 384
            PBEKeySpec pbeKeySpec = new PBEKeySpec(PassPhase.toCharArray(), salt, 2478, 384);
            SecretKey secretKey = factory.generateSecret(pbeKeySpec);
            byte[] key = new byte[32];
            byte[] iv = new byte[16];       
            System.arraycopy(secretKey.getEncoded(), 0, key, 0, 32);
            System.arraycopy(secretKey.getEncoded(), 32, iv, 0, 16);
            
            SecretKeySpec secret = new SecretKeySpec(key, "Rijndael");  
            
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");   
            AlgorithmParameterSpec ivSpec = new IvParameterSpec(iv);
            cipher.init(Cipher.DECRYPT_MODE, secret, ivSpec);
            
            planeText = planeText.replace("\"", "");
            
            byte[] planeBytes = Base64.getDecoder().decode(planeText);          
            String result = new String(cipher.doFinal(planeBytes), "UTF-8");            
            
            //log.debug("==FaRequestService==result : " + result);

            return result;  
            
        } catch (Exception e) {

            log.debug(" # PassPhase  : [{}]",  PassPhase);
            log.debug(" # plainText  : [{}]",  planeText);
            log.debug(" # salt       : [{}]",  salt);
            return "Failed";
        }               
    }
}
