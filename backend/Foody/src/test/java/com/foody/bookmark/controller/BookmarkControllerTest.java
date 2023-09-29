package com.foody.bookmark.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.foody.bookmark.dto.response.BookmarkListResponse;
import com.foody.security.util.LoginInfo;
import com.foody.util.ControllerTest;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

class BookmarkControllerTest extends ControllerTest {

    private final String BASEURL = "/api/v1/bookmark";

    @Test
    @DisplayName("나의 북마크 목록 조회된다")
    void memberShouldGetBookmarkList() throws Exception {

        List<BookmarkListResponse> mockList = new ArrayList<>();
        List<String> ingredients = List.of("김치", "돼지고기", "대파", "고추가루");
        BookmarkListResponse bookmarkListResponse = new BookmarkListResponse(123456, "너무 맛있는 김치찌개", ingredients);
        mockList.add(bookmarkListResponse);

        when(bookmarkFacade.findBookmarkByMember(any())).thenReturn(mockList);

        LoginInfo loginInfo = new LoginInfo("lkm454545@gmail.com");
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(new TestingAuthenticationToken(loginInfo, null));
        SecurityContextHolder.setContext(securityContext);


        mockMvc.perform(
            get(BASEURL + "/mypage")
                .with(authentication(new TestingAuthenticationToken(loginInfo, null)))
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
            .andDo(
                document("/bookmark/list",
                    preprocessResponse(prettyPrint()),
                    responseFields(
                        fieldWithPath("[].id").description("레시피 ID"),
                        fieldWithPath("[].name").description("레시피 이름"),
                        fieldWithPath("[].ingredients").description("레시피 재료 목록")
                    )
                )
            );
    }

}