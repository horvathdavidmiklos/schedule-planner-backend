//package integrationtest;
//
//import integrationtest.mock.DataStructureFake;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.sql.SQLException;
//import java.time.LocalDateTime;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//
//class accountRepositoryIntegrationTest {
//    private AccountRepository accountRepository;
//
//    private static final String TEST_USER = "TEST_USER";
//    private static final String TEST_EMAIL = "test@example.com";
//    private static final String TEST_PASSWORD_HASH = "123456@!abc";
//
//    private static final String OTHER_TEST_USER = "TEST_USER2";
//    private static final String OTHER_TEST_EMAIL = "test2@example.com";
//
//
//    @BeforeEach
//    void setup(){
//        H2DbHelper.createAll(DataStructureFake.createConnection());
//        accountRepository = new AccountRepositoryImpl(new DataStructureFake());
//    }
//
//    @AfterEach
//    void setDown(){
//        H2DbHelper.dropAll(DataStructureFake.createConnection());
//    }
//
//
//    @Test void test_saveAccount() throws SQLException {
//        checkAccountUniqueness(true,TEST_EMAIL,TEST_USER);
//        checkAccountIsNullInEmptyDb();
//        accountRepository.saveUnverified(createAccount());
//        checkAccountUniqueness(false,TEST_EMAIL,TEST_USER);
//        checkSavedAccount(accountRepository.findByEmail(TEST_EMAIL));
//        checkSavedAccount(accountRepository.findByUsername(TEST_USER));
//        checkAccountUniqueness(true,OTHER_TEST_EMAIL,OTHER_TEST_USER);
//        assertThat(accountRepository.findByUsername(OTHER_TEST_USER)).isNull();
//    }
//
//    private void checkSavedAccount(Account account){
//        assertThat(account.username()).isEqualTo(TEST_USER);
//        assertThat(account.passwordHash()).isEqualTo(TEST_PASSWORD_HASH);
//    }
//
//    private void checkAccountUniqueness(boolean isUniq,String email,String username){
//        assertThat(accountRepository.isUniqueEmail(email) == isUniq).isTrue();
//        assertThat(accountRepository.isUniqueUsername(username) == isUniq).isTrue();
//    }
//
//    private void checkAccountIsNullInEmptyDb(){
//        assertThat(accountRepository.findByEmail(TEST_EMAIL)).isNull();
//        assertThat(accountRepository.findByUsername(TEST_USER)).isNull();
//    }
//
//    private NewAccount createAccount(){
//        return new NewAccount()
//                .email(TEST_EMAIL)
//                .username(TEST_USER)
//                .createdAt(LocalDateTime.now())
//                .passwordHash(TEST_PASSWORD_HASH);
//    }
//}
