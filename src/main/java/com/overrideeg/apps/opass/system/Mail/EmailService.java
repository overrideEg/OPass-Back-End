package com.overrideeg.apps.opass.system.Mail;


import com.overrideeg.apps.opass.io.entities.User;
import com.overrideeg.apps.opass.io.entities.company;
import com.overrideeg.apps.opass.io.entities.employee;
import com.overrideeg.apps.opass.system.Connection.TenantResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class EmailService {
    @Autowired
    TenantResolver tenantResolver;
    @Autowired
    private JavaMailSender javaMailSender;

    public void sendEmail ( String to, String subject, String Text ) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(to);
        msg.setSubject(subject);
        msg.setBcc("alkholyabdelrahman@gmail.com");
        msg.setText(Text);
        javaMailSender.send(msg);
    }


    public void sendInvitationEmail ( Long companyId, User user, employee employee ) throws MessagingException {
        MimeMessage msg = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg, true);
        company company = new company();
        if (companyId != 0) {
            company = tenantResolver.findCompanyForTenantId(companyId);
        } else {
            company.setId(companyId);
        }

        helper.setTo(employee.getEmail());
        helper.setBcc("alkholyabdelrahman@gmail.com");
        helper.setSubject("No Reply");
        helper.setText("<h4>\n" +
                "Welcome aboard, our journey to a healthy workplace. Use this section to modify your profile." +
                "Thank you for being part of our Wholistic Wellbeing. \n" +
                "</h4>\n" +

                "<h4>\n" +
                "    Please Click <a href =" + "https://ewj-portal.com/#/auth/register?company=" + company.getId() + "&employee=" + employee.getId() + "&User=" + user.getId() + ">Here</a> and Stay safe \n" +
                "</h4>\n" +
                "\n" +
                "<img src=\"https://i.ibb.co/hDyRhpY/ewj-logo-final.png\" width=\"50%\">\n" +
                "<p>\n" +
                "    Employee Wellness Journey is an Anti-Coronavirus Platform. It helps reduce the risk of COVID-19. \n" +
                "    Stay safe. \n" +
                "    EWJ-Portal Support\n" +
                "    support@ewj-portal.com\n" +
                "    \n" +
                "</p>", true);
        javaMailSender.send(msg);
    }

    public void resetPassword ( User byUserName, String tempCode ) throws MessagingException {
        MimeMessage msg = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg, true);

        helper.setTo(byUserName.getEmail());
        helper.setBcc("alkholyabdelrahman@gmail.com");
        helper.setSubject("Reset EWJ Account Password");
        helper.setText("<h4>\n" +
                "    Welcome to Employee Wellness Journey Solutions\n" +
                "</h4>\n" +
                "<h4>\n" +
                "    You are requested to reset password, this message contains Code To Reset Password its expires within 5 Minutes\n" +
                "</h4>" +
                "<h1> Your Temp Code is : (" + tempCode + ")</h1>\n" +
                "\n" +
                "<img src=\"https://i.ibb.co/hDyRhpY/ewj-logo-final.png\" width=\"50%\">\n" +
                "<p>\n" +
                "    Employee Wellness Journey is an Anti-Coronavirus Platform. It helps reduce the risk of COVID-19. \n" +
                "    Stay safe. \n" +
                "    EWJ-Portal Support\n" +
                "    support@ewj-portal.com\n" +
                "    \n" +
                "</p>", true);
        javaMailSender.send(msg);
    }
}
