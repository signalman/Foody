package com.foody.bookmark.service;

import com.foody.bookmark.entity.Bookmark;
import com.foody.bookmark.repository.BookmarkRepository;
import com.foody.member.entity.Member;
import com.foody.recipe.entity.Recipe;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class BookmarkService {

    private final BookmarkRepository bookmarkRepository;

    @Transactional(readOnly = true)
    public boolean existsByMemberAndRecipe(Long memberId, Long recipeId) {

        return bookmarkRepository.existsBookmarkByMemberIdAndRecipeId(memberId, recipeId);
    }

    @Transactional
    public long save(Member member, Recipe recipeProxy) {

        return bookmarkRepository.save(Bookmark.builder()
                                               .recipe(recipeProxy)
                                               .member(member)
                                               .build())
                                 .getId();
    }

    @Transactional
    public void delete(Member member, Recipe recipeProxy) {

        Bookmark bookmark = bookmarkRepository.findByMemberIdAndRecipeId(member.getId(),
                                                  recipeProxy.getId())
                                              .orElseThrow();

        bookmarkRepository.delete(bookmark);
    }
}
