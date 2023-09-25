package com.data.foody

import com.data.foody.algorithm.Similarity
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import smile.math.MathEx.round

class SimilarityTest {

    private val algorithm = Similarity()
    private val sentence1 = "토마스가 가고 싶은 나라는 콜롬비아"
    private val sentence2 = "루나가 가고 싶은 나라는 포르투갈"

    @Test
    @DisplayName("코사인유사도 계산된다")
    fun cosineSimilarity() {

        val result = round(algorithm.cosineSimilarity(sentence1, sentence2), 3)
        print(result)
        assertEquals(result, 0.60)
    }

    @Test
    @DisplayName("TF-IDF 유사도 계산된다")
    fun tfidfSimilarity() {

        val result = round(algorithm.tfidfSimilarity(sentence1, sentence2), 3)
        assertEquals(result, 0.432)

    }

    @Test
    @DisplayName("자카드유사도 계산된다")
    fun jaccardSimilarity() {

        val result = round(algorithm.jaccardSimilarity(sentence1, sentence2), 3)
        assertEquals(result, 0.429)
    }
}