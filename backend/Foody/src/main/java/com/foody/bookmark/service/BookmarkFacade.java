package com.foody.bookmark.service;

import com.foody.member.entity.Member;
import com.foody.member.service.MemberService;
import com.foody.recipe.entity.Recipe;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class BookmarkFacade {

    private final BookmarkService bookmarkService;
    private final MemberService memberService;
    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void changeStatus(Long recipeId, String email) {

        Member member = memberService.findByEmail(email);
        Recipe recipe = em.getReference(Recipe.class, recipeId);

        boolean isBookmarked = bookmarkService.existsByMemberAndRecipe(member.getId(), recipeId);

        if(isBookmarked) {
            deleteBookmark(member, recipe);
        }else {
            addBookmark(member, recipe);
        }

    }

    private void deleteBookmark(Member member, Recipe recipe) {
        bookmarkService.delete(member, recipe);
    }

    private void addBookmark(Member member, Recipe recipe) {
        bookmarkService.save(member, recipe);
    }
}
