package com.timesoccer247.Spring_TimeSoccer247.service;


import com.timesoccer247.Spring_TimeSoccer247.entity.User;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class EmailService {
    private final JavaMailSender javaMailSender;
    private final SpringTemplateEngine templateEngine;

    @Async
    public void sendEmail(String to, String subject, Map<String, Object> model, String templateName){
        MimeMessage message = this.javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            Context context = new Context();
            context.setVariables(model);

            String htmlContent = templateEngine.process(templateName, context);

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlContent, true);

            javaMailSender.send(message);
        }
        catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }


    public void sendVerificationCode(User user, String verificationCode) {
        if (user != null && verificationCode != null) {

            Map<String, Object> model = new HashMap<>();
            model.put("name", user.getName());
            model.put("verificationCode", verificationCode);
            model.put("expirationTime", "10 phút");

            this.sendEmail(user.getEmail(),
                    "Mã xác nhận của bạn để khôi phục mật khẩu",
                    model,
                    "verification"
            );
        }
    }

}
