package com.foody.bookmark.service;

import com.foody.bookmark.dto.response.BookmarkListResponse;
import com.foody.bookmark.entity.Bookmark;
import com.foody.mbti.entity.Mbti;
import com.foody.member.entity.Member;
import com.foody.member.service.MemberService;
import com.foody.recipe.dto.IngredientUnit;
import com.foody.recipe.entity.Recipe;
import com.foody.recipe.entity.RecipeCategory;
import com.foody.recipe.util.RecipeUtils;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
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

        boolean isBookmarked = existsByMemberAndRecipe(member.getId(), recipeId);

        if(isBookmarked) {
            deleteBookmark(member, recipe);
        }else {
            addBookmark(member, recipe);
        }

    }

    private void deleteBookmark(Member member, Recipe recipe) {
        bookmarkService.delete(member, recipe);
        updatePreference(member, recipe, -3);
    }

    private void addBookmark(Member member, Recipe recipe) {
        bookmarkService.save(member, recipe);
        updatePreference(member, recipe, 3);
    }

    private void updatePreference(Member member, Recipe recipe, int value) {

        Mbti mbti = member.getMbti();
        String foodMethod = recipe.getFoodMethod().equals("기타")? "기타조리" : recipe.getFoodMethod();
        String foodSituation = recipe.getFoodSituation();
        String foodIngredients = recipe.getFoodIngredients();
        String foodTypes = recipe.getFoodTypes();

        List<String> preferenceList = List.of(foodMethod, foodSituation, foodIngredients, foodTypes);

        preferenceList.stream()
            .map(String::valueOf)
            .map(RecipeCategory::fromKoreanName)
            .forEach(preference -> preference.applyPreference(mbti, value));
    }

    public boolean existsByMemberAndRecipe(Long memberId, Long recipeId) {
        return bookmarkService.existsByMemberAndRecipe(memberId, recipeId);
    }

    public boolean existsByMemberEmailAndRecipe(String email, Long recipeId) {

        Member member = memberService.findByEmail(email);
        return existsByMemberAndRecipe(member.getId(), recipeId);
    }

    @Transactional(readOnly = true)
    public List<BookmarkListResponse> findBookmarkByMember(String email) {

        Member member = memberService.findByEmail(email);
        List<Bookmark> bookmarkList = bookmarkService.findBookmarkListByMember(member);

        return bookmarkList.stream()
                           .map(bookmark -> {
                               List<String> ingredientNames = Objects.requireNonNull(
                                                                         RecipeUtils.formatIngredients(bookmark.getRecipe()
                                                                                                               .getIngredient()))
                                                                     .stream()
                                                                     .map(IngredientUnit::name)
                                                                     .collect(Collectors.toList());

                               return new BookmarkListResponse(
                                   bookmark.getRecipe().getId(),
                                   bookmark.getRecipe().getName(),
                                   ingredientNames
                               );
                           })
                           .collect(Collectors.toList());
    }
}
