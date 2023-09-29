package com.foody.util;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.foody.bookmark.controller.BookmarkController;
import com.foody.bookmark.repository.BookmarkRepository;
import com.foody.bookmark.service.BookmarkFacade;
import com.foody.bookmark.service.BookmarkService;
import com.foody.global.service.AmazonS3Service;
import com.foody.mbti.controller.MbtiController;
import com.foody.mbti.repository.MbtiRepository;
import com.foody.mbti.service.MbtiService;
import com.foody.member.controller.MemberController;
import com.foody.member.repository.MemberRepository;
import com.foody.member.service.MemberService;
import com.foody.recipe.repository.RecipeRepository;
import com.foody.recipe.service.RecipeService;
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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

@AutoConfigureRestDocs
@ActiveProfiles("test")
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(RestDocumentationExtension.class)
@WebMvcTest({
    MemberController.class,
    RefrigeratorsController.class,
    MbtiController.class,
    BookmarkController.class
})
public class ControllerTest {

    //setup
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private RestDocumentationContextProvider restDocumentation;

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
    @MockBean protected RecipeService recipeService;
    @MockBean protected BookmarkService bookmarkService;
    @MockBean protected BookmarkFacade bookmarkFacade;

    // Repository
    @MockBean protected MemberRepository memberRepository;
    @MockBean protected IngredientRepository ingredientRepository;
    @MockBean protected MbtiRepository mbtiRepository;
    @MockBean protected RecipeRepository recipeRepository;
    @MockBean protected BookmarkRepository bookmarkRepository;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                                      .apply(documentationConfiguration(restDocumentation))
                                      .addFilters(new CharacterEncodingFilter("UTF-8", true))
                                      .build();
    }
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
