package com.skyrocket.invoicer.exception;

public class MailNotSendableException extends Exception {

    public MailNotSendableException() {
        super("could not send email. please check the email address again, otherwise contact the system administrator");
    }

}
