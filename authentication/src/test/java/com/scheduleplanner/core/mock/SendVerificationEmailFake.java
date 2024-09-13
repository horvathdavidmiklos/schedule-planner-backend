package com.scheduleplanner.core.mock;

import com.scheduleplanner.core.createaccount.SendVerificationEmail;
import com.scheduleplanner.core.createaccount.SendVerificationEmailImpl;
import mockhelper.CallChecker;

public class SendVerificationEmailFake implements SendVerificationEmail {
    public enum  Method{
        SEND;
    }
    public CallChecker<Method> callChecker;
    public SendVerificationEmailFake() {
        callChecker = new CallChecker<>();
    }

    @Override
    public void send(String username, String emailAddress, String verificationLink) {
        callChecker.addCall(Method.SEND, username, emailAddress, verificationLink);
    }
}
