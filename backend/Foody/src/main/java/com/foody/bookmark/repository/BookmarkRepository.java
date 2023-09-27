package com.foody.bookmark.repository;

import com.foody.bookmark.entity.Bookmark;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

    boolean existsBookmarkByMemberIdAndRecipeId(Long memberId, Long recipeId);

    Optional<Bookmark> findByMemberIdAndRecipeId(Long memberId, Long recipeId);

}
