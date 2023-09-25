package com.data.foody

import com.data.foody.algorithm.Similarity
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import smile.math.MathEx.round

class SimilarityTest {

    private val algorithm = Similarity()

    @Test
    @DisplayName("코사인유사도 계산된다")
    fun cosineSimilarity() {

        val sentence1 = "토마스가 가고 싶은 나라는 콜롬비아"
        val sentence2 = "루나가 가고 싶은 나라는 포르투갈"

        val result = round(algorithm.cosineSimilarity(sentence1, sentence2), 2)
        print(result)
        assertEquals(result, 0.6)
    }

}