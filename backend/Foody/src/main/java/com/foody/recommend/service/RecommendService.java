package com.foody.recommend.service;

import com.foody.recommend.dto.response.RecommendItem;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class RecommendService {

    public List<RecommendItem> findRecommendItemByIngredients(String email) {

        return null;
    }
}
