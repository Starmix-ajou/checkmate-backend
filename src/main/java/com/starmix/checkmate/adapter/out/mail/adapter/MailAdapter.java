package com.starmix.checkmate.adapter.out.mail.adapter;

import com.starmix.checkmate.adapter.out.mail.type.MailType;
import com.starmix.checkmate.application.port.out.mail.MailPort;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

@RequiredArgsConstructor
@Component
public class MailAdapter implements MailPort {

    private final JavaMailSender javaMailSender;
    private final SpringTemplateEngine templateEngine;

    @Override
    @Async
    public void send(String address, MailType mailType, Context context) {
        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            InternetAddress sender = new InternetAddress("checkmate", "checkmate");
            message.setFrom(sender);
            message.setRecipients(MimeMessage.RecipientType.TO, address);
            message.setSubject(mailType.getTitle());
            message.setText(templateEngine.process(mailType.getTemplate(), context), "utf-8", "html");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        javaMailSender.send(message);
    }
}