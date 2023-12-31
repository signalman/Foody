package com.foody.bookmark.controller;

import com.foody.bookmark.dto.response.BookmarkListResponse;
import com.foody.bookmark.service.BookmarkFacade;
import com.foody.security.util.LoginInfo;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/bookmark")
@RestController
public class BookmarkController {

    private final BookmarkFacade bookmarkFacade;

    // 북마크가 있다면 제거, 없다면 추가
    @PostMapping("/{recipeId}")
    public ResponseEntity<Void> bookmarkStatusChange(@PathVariable Long recipeId, @AuthenticationPrincipal LoginInfo loginInfo) {

        log.debug(" {} request changed bookmark status", loginInfo.email());
        bookmarkFacade.changeStatus(recipeId, loginInfo.email());

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/mypage")
    public ResponseEntity<List<BookmarkListResponse>> getBookmarkList(@AuthenticationPrincipal LoginInfo loginInfo) {

        log.debug(" {} request bookmark list", loginInfo.email());
        List<BookmarkListResponse> bookmarkList = bookmarkFacade.findBookmarkByMember(loginInfo.email());

        return ResponseEntity.ok().body(bookmarkList);
    }
}
