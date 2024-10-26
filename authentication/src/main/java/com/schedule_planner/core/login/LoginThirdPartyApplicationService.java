package com.schedule_planner.core.login;
import com.schedule_planner.core.login.dto.ThirdPartyApplicationDataDto;
import com.schedule_planner.core.login.dto.TokenOutDto;
import com.schedule_planner.store.Account;
import com.schedule_planner.store.AccountService;
import com.schedule_planner.util.image.ImageDownloader;
import com.schedule_planner.util.security.token.TokenExpirationTime;
import com.schedule_planner.util.security.token.TokenPurpose;
import com.schedule_planner.util.security.token.TokenService;

import java.time.LocalDateTime;
import java.util.Optional;

public class LoginThirdPartyApplicationService {
    private final AccountService accountService;
    private final TokenService tokenService;
    private final ImageDownloader imageDownloader;

    public LoginThirdPartyApplicationService(AccountService accountService,
                                             TokenService tokenService,
                                             ImageDownloader imageDownloader) {
        this.accountService = accountService;
        this.tokenService = tokenService;
        this.imageDownloader = imageDownloader;
    }

    public TokenOutDto runService(ThirdPartyApplicationDataDto thirdPartyApplicationDataDto) {
        final var optionalAccount = findAccount(thirdPartyApplicationDataDto.email());
        optionalAccount.orElseGet(() -> createNewAccount(thirdPartyApplicationDataDto));
        return new TokenOutDto(tokenService.generateToken(thirdPartyApplicationDataDto.username(), TokenExpirationTime.ONE_YEAR, TokenPurpose.LOGIN));
    }

    private Account createNewAccount(ThirdPartyApplicationDataDto thirdPartyApplicationDataDto) {
        final var account = new Account()
                .createdAt(LocalDateTime.now())
                .email(thirdPartyApplicationDataDto.email())
                .username(thirdPartyApplicationDataDto.username())
                .isVerified(true)
                .nickname(thirdPartyApplicationDataDto.nickname())
                .profilePicture(getProfilePicture(thirdPartyApplicationDataDto.profilePictureUrl()));
        accountService.save(account);
        return account;
    }

    private Optional<Account> findAccount(String email) {
        return accountService.findByEmail(email);
    }

    private byte[] getProfilePicture(String url) {
        try {
            return  imageDownloader.downloadImageAsBytes(url);
        } catch (Exception e) {
            return null;
        }
    }
}
