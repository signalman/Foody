package com.foody.bookmark.service;

import com.foody.member.entity.Member;
import com.foody.member.service.MemberService;
import com.foody.recipe.entity.Recipe;
import com.foody.recipe.service.RecipeService;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class BookmarkFacade {

    private final RecipeService recipeService;
    private final BookmarkService bookmarkService;
    private final MemberService memberService;
    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void changeStatus(Long recipeId, String email) {

        Member member = memberService.findByEmail(email);
        Recipe recipeProxy = em.getReference(Recipe.class, recipeId);

        boolean isBookmarked = bookmarkService.existsByMemberAndRecipe(member.getId(), recipeId);

        if(isBookmarked) {
            deleteBookmark(member, recipeProxy);
        }else {
            addBookmark(member, recipeProxy);
        }

    }

    private void deleteBookmark(Member member, Recipe recipeProxy) {
        bookmarkService.delete(member, recipeProxy);
    }

    private void addBookmark(Member member, Recipe recipeProxy) {
        bookmarkService.save(member, recipeProxy);
    }
}
