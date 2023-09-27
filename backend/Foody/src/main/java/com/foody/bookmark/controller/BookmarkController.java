package com.foody.bookmark.controller;

import com.foody.bookmark.service.BookmarkService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/bookmark")
@RestController
public class BookmarkController {

    private final BookmarkService bookmarkService;
}
