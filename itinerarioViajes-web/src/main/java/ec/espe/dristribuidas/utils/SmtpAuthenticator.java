/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.espe.dristribuidas.utils;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 *
 * @author ale
 */
public class SmtpAuthenticator extends Authenticator {

    private String Username;
    private String PassWord;
    
    public SmtpAuthenticator() {

        super();
        Username = "saiv.informacion@gmail.com";
        PassWord = "saiv2015";
    }

    @Override
    public PasswordAuthentication getPasswordAuthentication() {

        if ((Username != null) && (Username.length() > 0) && (PassWord != null)
                && (PassWord.length() > 0)) {

            return new PasswordAuthentication(Username, PassWord);
        }

        return null;
    }
}
