package com.scheduleplanner.core.mock;

import com.scheduleplanner.core.verifiedaccount.VerifyAccountBusinessLogic;
import com.scheduleplanner.encrypt.TokenService;
import com.scheduleplanner.store.AccountService;

public class VerifyAccountBusinessLogicFake extends VerifyAccountBusinessLogic {
    public String urlEncodeTokenInput;
    public String userNameInput;

    public VerifyAccountBusinessLogicFake() {
        super(null, null);
    }

    @Override
    public void runService(String urlEncodedToken, String userName) {
        urlEncodeTokenInput = urlEncodedToken;
        userNameInput = userName;
    }
}
