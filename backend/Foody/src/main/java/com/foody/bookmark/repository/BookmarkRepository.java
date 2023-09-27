package com.foody.bookmark.repository;

import com.foody.member.entity.Member;
import com.foody.bookmark.entity.Bookmark;
import com.foody.recipe.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

    boolean existsBookmarkByMemberAndRecipe(Member member, Recipe recipe);

}
