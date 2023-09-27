package com.foody.bookmark.service;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.foody.member.entity.Member;
import com.foody.recipe.entity.Recipe;
import com.foody.recipe.service.RecipeService;
import com.foody.util.ServiceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

class BookmarkFacadeTest extends ServiceTest {

    @Autowired
    private BookmarkService bookmarkService;
    @Autowired
    private BookmarkFacade bookmarkFacade;
    @Autowired
    private RecipeService recipeService;

    @Test
    @Transactional
    @DisplayName("북마크 저장된다")
    void t1() {
        memberInfoGenerator();

        Member member = memberService.findByEmail("lkm454545@gmail.com");
        Recipe recipe = recipeService.getEntityById(128671L);

        long id = bookmarkService.save(member, recipe);

        boolean isBookmarked = bookmarkService.existsByMemberAndRecipe(member.getId(), recipe.getId());

        assertTrue(isBookmarked);

    }

    @Test
    @Transactional
    @DisplayName("북마크 하지않은 레시피 저장 된다")
    void t2() throws Exception {

    }

}