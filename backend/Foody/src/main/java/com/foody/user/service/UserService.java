package com.foody.user.service;

import com.foody.global.exception.ErrorCode;
import com.foody.security.util.JwtCreater;
import com.foody.user.controller.UserController;
import com.foody.user.dto.request.UserJoinRequest;
import com.foody.user.dto.request.UserSignupRequest;
import com.foody.user.dto.response.TokenResponse;
import com.foody.user.entity.User;
import com.foody.user.exception.UserException;
import com.foody.user.repository.UserRepository;
import java.io.IOException;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserController userController;
    private final JwtCreater jwtCreater;
    @Value("${jwt.token.secret}")
    private String secretKey;

    @Transactional(readOnly = true)
    public void isNicknameDuplicated(String nickname) {
        boolean userExists = userRepository.existsByNickname(nickname);

        if(userExists) {
            throw new UserException(ErrorCode.NICKNAME_DUPLICATED);
        }
    }

    @Transactional(readOnly = true)
    public void isEmailDuplicated(String email) {
        boolean userExists = userRepository.existsByEmail(email);

        if(userExists) {
            throw new UserException(ErrorCode.EMAIL_DUPLICATED);
        }
    }

    @Transactional
    public void loginUser(HttpServletResponse response, Authentication authentication, OAuth2User oAuth2User)
        throws IOException {
        Map<String, Object> attributes = oAuth2User.getAttributes();
        String email = (String) attributes.get("email");

        log.info("{} user request login",email);

        // 가입된 이메일 없음 -> findByEmail에서 처리
        User user = findByEmail(email);

        String accessToken = jwtCreater.createAccessToken(user.getId(), email, secretKey);
        String refreshToken = jwtCreater.createRefreshToken(email, secretKey);

        TokenResponse tokenResponse = new TokenResponse(user.getId(), accessToken, refreshToken);
        // 토큰 반환
        userController.test();
        response.sendRedirect("/");

    }

    @Transactional
    public void signUpUser(HttpServletResponse response, Authentication authentication, OAuth2User oAuth2User) {
        Map<String, Object> attributes = oAuth2User.getAttributes();
        String email = (String) attributes.get("email");
        String profileImg = (String) attributes.get("picture");

        log.info("{} signup request", email);
        log.info("{} ",profileImg);
        // 이메일 중복검사
        isEmailDuplicated(email);
        UserSignupRequest userSignupRequest = new UserSignupRequest(email, profileImg);

        User user = User.signupUser(userSignupRequest);

        userRepository.save(user);

        // 추가정보 입력 url로 보낼 예정인데

    }

    @Transactional(readOnly = true)
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                               .orElseThrow(() -> new UserException(ErrorCode.EMAIL_NOT_FOUND));
    }


}
