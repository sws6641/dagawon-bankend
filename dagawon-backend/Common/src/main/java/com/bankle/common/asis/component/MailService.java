//package com.bankle.common.asis.component;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.core.io.FileSystemResource;
//import org.springframework.mail.MailException;
//import org.springframework.mail.MailSender;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.MimeMessageHelper;
//import org.springframework.mail.javamail.MimeMessagePreparator;
//import org.springframework.stereotype.Component;
//
//import java.io.File;
//
//@Component
//@RequiredArgsConstructor
//public class MailService {
//
//    private final JavaMailSender mailSender;
//
////    @Value("${spring.mail.username}")
//    String sendFrom;
//
//    public boolean sendMail(String to, String subject, String body){
//
//        MimeMessagePreparator preparator = mimeMessage -> {
//            final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true,"UTF-8");
//
//            message.setTo(to);
//            message.setFrom(sendFrom);
//            message.setSubject(subject);
//            message.setText(body, true);     //true: html 형식 사용
//
//        };
//
//        try{
//            mailSender.send(preparator);
//        }catch(MailException e){
//            return false;
//        }
//
//        return true;
//    }
//
//    public boolean sendMailWithAttach(String to, String subject, String body, String fileToAttach){
//
//        MimeMessagePreparator preparator = mimeMessage -> {
//            final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true,"UTF-8");
//
//            message.setTo(to);
//            message.setFrom(sendFrom);
//            message.setSubject(subject);
//            message.setText(body, true);     //true: html 형식 사용
//
//            FileSystemResource file = new FileSystemResource(new File(fileToAttach));
//            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
//            helper.addAttachment("logo.jpg", file);
//        };
//
//        try{
//            mailSender.send(preparator);
//        }catch(MailException e){
//            return false;
//        }
//
//        return true;
//    }
//
//    public String setName(String mailContent, String name){
//        return mailContent.replace("##name##", name);
//    }
//}
