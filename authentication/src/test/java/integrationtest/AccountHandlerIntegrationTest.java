package integrationtest;

import com.sheduleplanner.common.entity.Account;
import com.sheduleplanner.common.entity.NewAccount;
import integrationtest.mock.DataStructureFake;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.time.LocalDateTime;
import com.scheduleplanner.gateway.store.AccountHandler;

import static org.assertj.core.api.Assertions.assertThat;


class AccountHandlerIntegrationTest {
    private AccountHandler accountHandler;

    private static final String TEST_USER = "TEST_USER";
    private static final String TEST_EMAIL = "test@example.com";
    private static final String TEST_PASSWORD_HASH = "123456@!abc";

    private static final String OTHER_TEST_USER = "TEST_USER2";
    private static final String OTHER_TEST_EMAIL = "test2@example.com";


    @BeforeEach
    void setup(){
        H2DbHelper.createAll(DataStructureFake.createConnection());
        accountHandler = new AccountHandler(new DataStructureFake());
    }

    @AfterEach
    void setDown(){
        H2DbHelper.dropAll(DataStructureFake.createConnection());
    }


    @Test
    void test_saveAccount() throws SQLException {
        checkAccountUniqueness(true,TEST_EMAIL,TEST_USER);
        checkAccountIsNullInEmptyDb();
        accountHandler.save(createAccount());
        checkAccountUniqueness(false,TEST_EMAIL,TEST_USER);
        checkSavedAccount(accountHandler.findByEmail(TEST_EMAIL));
        checkSavedAccount(accountHandler.findByUsername(TEST_USER));
        checkAccountUniqueness(true,OTHER_TEST_EMAIL,OTHER_TEST_USER);
        assertThat(accountHandler.findByUsername(OTHER_TEST_USER)).isNull();
    }

    private void checkSavedAccount(Account account){
        assertThat(account.username()).isEqualTo(TEST_USER);
        assertThat(account.passwordHash()).isEqualTo(TEST_PASSWORD_HASH);
    }

    private void checkAccountUniqueness(boolean isUniq,String email,String username){
        assertThat(accountHandler.isUniqueEmail(email) == isUniq).isTrue();
        assertThat(accountHandler.isUniqueUsername(username) == isUniq).isTrue();
    }

    private void checkAccountIsNullInEmptyDb(){
        assertThat(accountHandler.findByEmail(TEST_EMAIL)).isNull();
        assertThat(accountHandler.findByUsername(TEST_USER)).isNull();
    }

    private NewAccount createAccount(){
        return new NewAccount()
                .email(TEST_EMAIL)
                .username(TEST_USER)
                .createdAt(LocalDateTime.now())
                .passwordHash(TEST_PASSWORD_HASH);
    }
}
