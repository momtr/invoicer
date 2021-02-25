package com.skyrocket.invoicer.util.mail;

import lombok.Data;

import javax.mail.Address;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class RecipientList {

    private List<String> recipients;
    private List<String> blindCopies;

    public RecipientList() {
        recipients = new ArrayList<>();
        blindCopies = new ArrayList<>();
    }

    public void addRecipient(String email) {
        if(checkEmail(email)) {
            recipients.add(email);
        }
    }

    public void addBlindRecipient(String email) {
        if(checkEmail(email)) {
            blindCopies.add(email);
        }
    }

    public List<String> getRecipients() {
        return recipients;
    }

    public List<String> getBlindRecipients() {
        return blindCopies;
    }

    public Address[] getRecipientsAsAddresses() throws AddressException {
        Address[] addresses = new Address[recipients.size()];
        for(int i = 0; i < recipients.size(); i++) {
            addresses[i] = new InternetAddress(recipients.get(i));
        }
        return addresses;
    }

    public Address[] getBlindRecipientsAsAddresses() throws AddressException {
        Address[] addresses = new Address[blindCopies.size()];
        for(int i = 0; i < blindCopies.size(); i++) {
            addresses[i] = new InternetAddress(blindCopies.get(i));
        }
        return addresses;
    }

    /**
     * returns whether true/false whether the email address is valid or not (structure)
     * @param email the email to check
     * @return boolean indicating if the email is valid in terms of structure
     */
    private boolean checkEmail(String email) {
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
            return true;
        } catch (AddressException ex) {
            return false;
        }
    }

}
