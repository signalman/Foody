package com.foody.bookmark.dto.response;

import java.util.List;

public record BookmarkListResponse(
    long id,
    String name,
    List<String> ingredients
) {


}
