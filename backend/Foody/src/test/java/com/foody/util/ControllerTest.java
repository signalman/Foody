package com.foody.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.foody.global.service.AmazonS3Service;
import com.foody.mbti.controller.MbtiController;
import com.foody.mbti.repository.MbtiRepository;
import com.foody.mbti.service.MbtiService;
import com.foody.member.controller.MemberController;
import com.foody.member.repository.MemberRepository;
import com.foody.member.service.MemberService;
import com.foody.nutrient.controller.NutrientController;
import com.foody.nutrient.dto.response.NutrientResponse;
import com.foody.nutrient.repository.NutrientRepository;
import com.foody.nutrient.service.NutrientService;
import com.foody.refrigerators.controller.RefrigeratorsController;
import com.foody.refrigerators.repository.IngredientRepository;
import com.foody.refrigerators.service.RefrigeratorsService;
import com.foody.security.service.CustomUserDetailService;
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
    RefrigeratorsController.class,
    MbtiController.class,
    NutrientController.class
})
public class ControllerTest {
    // Util
    @Autowired
    protected MockMvc mockMvc;
    @Autowired protected ObjectMapper objectMapper;

    @MockBean protected JwtProvider jwtProvider;
    @MockBean protected AmazonS3Service amazonS3Service;
    @MockBean protected CustomUserDetailService customUserDetailService;

    @Value("${jwt.token.secret}")
    protected String secretKey;

    // Service
    @MockBean protected MemberService memberService;
    @MockBean protected RefrigeratorsService refrigeratorsService;
    @MockBean protected MbtiService mbtiService;
    @MockBean protected NutrientService nutrientService;

    // Repository
    @MockBean protected MemberRepository memberRepository;
    @MockBean protected IngredientRepository ingredientRepository;
    @MockBean protected MbtiRepository mbtiRepository;
    @MockBean protected NutrientRepository nutrientRepository;

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
