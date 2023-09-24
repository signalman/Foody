package com.foody.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.foody.member.controller.MemberController;
import com.foody.member.repository.MemberRepository;
import com.foody.member.service.MemberService;
import com.foody.refrigerator.controller.RefrigeratorControllerTest;
import com.foody.security.util.JwtProvider;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import javax.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureRestDocs
@ActiveProfiles("test")
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest({
    MemberController.class,
    RefrigeratorControllerTest.class
})
public class ControllerTest {
    // Util
    @Autowired
    protected MockMvc mockMvc;
    @Autowired protected ObjectMapper objectMapper;

    @MockBean protected JwtProvider jwtProvider;

    @Value("${jwt.token.secret}")
    protected String secretKey;

    // Service
    @MockBean protected MemberService memberService;

    // Repository
    @MockBean protected MemberRepository memberRepository;

    protected String createToken(String email, String secretKey) {

        final long accessTokenExpireTimeMs = 3600000L; // 1시간
        final long refreshTokenExpireTimeMs = 1209600000L; // 2주일

        Claims claims = Jwts.claims();
        claims.put("email", email);

        Date now = new Date();
        Date accessTokenExpiration = new Date(now.getTime() + accessTokenExpireTimeMs);
        Date refreshTokenExpiration = new Date(now.getTime() + refreshTokenExpireTimeMs);

        return Jwts.builder()
                   .setClaims(claims)
                   .setIssuedAt(now)
                   .setExpiration(accessTokenExpiration)
                   .signWith(
                       Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)),
                       SignatureAlgorithm.HS256)
                   .compact();

    }
}
