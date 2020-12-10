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
        helper.setFrom("O-Pass");

        helper.setSubject("Welcome To O-Pass App");
        helper.setText("<h4>\n" +
                "Welcome aboard, our journey to a healthy workplace. Use The App To O-Pass App To Record Attendance." +
                "Thank you for being part of O-Pass Success Story. \n" +
                "</h4>\n" +

                " <a class=\"col\"\n" +
                "    href='https://play.google.com/store/apps/details?id=com.overrideeg.apps.opass&pcampaignid=pcampaignidMKT-Other-global-all-co-prtnr-py-PartBadge-Mar2515-1'><img alt='Get it on Google Play' height=\"50px\" \n" +
                "    src='https://play.google.com/intl/en_us/badges/static/images/badges/en_badge_web_generic.png'/></a>  \n"+
                "  <a class=\"col\"\n" +
                "    href='https://apps.apple.com/tr/app/o-pass/id1530717293'><img alt='Get it on App Store' height=\"40px\" style=\"height: 41px; padding-top: 7px;\"\n" +
                "    src=\"https://system.o-pass.app/assets/images/Download_on_the_App_Store_Badge_US-UK_RGB_blk_092917.svg\"/></a> \n" +
                "<img src=\"https://system.o-pass.app/assets/images/Asset%201.png\" width=\"50%\">\n" +
                "<p>\n" +
                "    O-Pass is an Anti-Coronavirus HR Platform. It helps reduce the risk of COVID-19. \n" +
                "    Stay safe. \n" +
                "    O-Pass Support\n" +
                "    support@o-pass.app\n" +
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
