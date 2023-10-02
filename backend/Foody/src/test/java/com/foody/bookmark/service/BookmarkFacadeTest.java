package com.foody.bookmark.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.foody.bookmark.dto.response.BookmarkListResponse;
import com.foody.mbti.entity.Mbti;
import com.foody.mbti.repository.MbtiRepository;
import com.foody.member.entity.Member;
import com.foody.member.repository.MemberRepository;
import com.foody.recipe.entity.Recipe;
import com.foody.recipe.service.RecipeService;
import com.foody.util.ServiceTest;
import java.util.List;
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
    @Autowired
    private MbtiRepository mbtiRepository;
    @Autowired
    private MemberRepository memberRepository;

    @Test
    @Transactional
    @DisplayName("북마크 저장된다")
    void t1() {
        memberInfoGenerator();

        Member member = memberService.findByEmail("lkm454545@gmail.com");
        Recipe recipe = recipeService.getEntityById(128671L);

        long id = bookmarkService.save(member, recipe);

        boolean isBookmarked = bookmarkService.existsByMemberAndRecipe(member.getId(),
            recipe.getId());

        assertTrue(isBookmarked);

    }

    @Test
    @Transactional
    @DisplayName("북마크 하지않은 레시피 저장 된다")
    void t2() throws Exception {
        memberInfoGenerator();

        Member member = memberService.findByEmail("lkm454545@gmail.com");
        long recipeId = 128671L;

        bookmarkFacade.changeStatus(recipeId, "lkm454545@gmail.com");

        boolean isBookmarked = bookmarkService.existsByMemberAndRecipe(member.getId(), recipeId);

        assertTrue(isBookmarked);
    }

    @Test
    @Transactional
    @DisplayName("북마크한 레시피 삭제 된다")
    void t3() throws Exception {
        memberInfoGenerator();

        Member member = memberService.findByEmail("lkm454545@gmail.com");
        long recipeId = 128671L;

        bookmarkFacade.changeStatus(recipeId, "lkm454545@gmail.com");
        bookmarkFacade.changeStatus(recipeId, "lkm454545@gmail.com");

        boolean isBookmarked = bookmarkService.existsByMemberAndRecipe(member.getId(), recipeId);

        assertFalse(isBookmarked);
    }

    @Test
    @Transactional
    @DisplayName("북마크 리스크 조회된다")
    void t4() throws Exception {
        memberInfoGenerator();

        Member member = memberService.findByEmail("lkm454545@gmail.com");
        long recipeId = 128671L;

        bookmarkFacade.changeStatus(recipeId, "lkm454545@gmail.com");

        List<BookmarkListResponse> bookmarkList = bookmarkFacade.findBookmarkByMember(
            member.getEmail());

        System.out.println(bookmarkList.get(0));

        assertEquals(1, bookmarkList.size());
    }

    @Test
    @Transactional
    @DisplayName("레시피 조회 시, 선호도 변경된다")
    void t5() throws Exception {

        Mbti mbti = new Mbti(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
        mbtiRepository.save(mbti);

        Member member = Member.builder()
                              .email("sangwon@google.com")
                              .mbti(mbti)
                              .build();
        memberRepository.save(member);

        long recipeId = 128671L;
        bookmarkFacade.changeStatus(recipeId, "sangwon@google.com");

        mbti = member.getMbti();
        System.out.println(mbti.getLowCook());
        System.out.println(mbti.getHighCook());
        assertThat(mbti).satisfies(
            pre -> assertThat(pre.getHighCook()).isEqualTo(3),
            pre -> assertThat(pre.getConvenienceFood()).isEqualTo(3),
            pre -> assertThat(pre.getProcessedFood()).isEqualTo(3),
            pre -> assertThat(pre.getDessert()).isEqualTo(3)
        );


    }

    @Test
    @Transactional
    @DisplayName("선호도 변경시 조리방법 기타 기타조리로 치환된다")
    void t6() throws Exception {

        Mbti mbti = new Mbti(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
        mbtiRepository.save(mbti);

        Member member = Member.builder()
                              .email("sangwon@google.com")
                              .mbti(mbti)
                              .build();
        memberRepository.save(member);

        long recipeId = 221097L;
        bookmarkFacade.changeStatus(recipeId, "sangwon@google.com");

        mbti = member.getMbti();
        assertThat(mbti).satisfies(
            pre -> assertThat(pre.getEtcCook()).isEqualTo(3),
            pre -> assertThat(pre.getEtcFood()).isEqualTo(0)
        );


    }

}