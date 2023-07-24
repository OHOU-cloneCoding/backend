package com.project.ohouclonecoding.email;

public interface MailSendService {
    void makeRandomNumber();

    String joinEmail(String email);

    void mailSend(String message, String email, String title);
}
