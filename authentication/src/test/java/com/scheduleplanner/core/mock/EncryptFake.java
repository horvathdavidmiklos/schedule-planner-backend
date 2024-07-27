package com.scheduleplanner.core.mock;

import com.scheduleplanner.secret.Encrypt;
import mockhelper.CallChecker;

public class EncryptFake implements Encrypt {
    public CallChecker<EncryptMethod> callChecker;
    public enum EncryptMethod{
        CONVERT_TO_HASH,
        CHECK_PASSWORD
    }
    public EncryptFake() {
        callChecker = new CallChecker<>();
    }

    @Override
    public String hashPassword(String password) {
        return (String) callChecker.addCall(EncryptMethod.CONVERT_TO_HASH,password);
    }

    @Override
    public boolean checkPassword(String password, String hashed) {
        return (boolean) callChecker.addCall(EncryptMethod.CHECK_PASSWORD,password,hashed);
    }
}
