package com.scheduleplanner.core.mock;

import com.scheduleplanner.common.gateway.email.EmailConnector;
import com.scheduleplanner.common.gateway.email.EmailData;

public class EmailConnectorFake  implements EmailConnector {
    public  EmailData inputEmailData;
    @Override
    public void send(EmailData email) {
        inputEmailData = email;
    }
}
