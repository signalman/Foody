package com.data.foody.preprocessing

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

class WordProcessingKtTest {

    @Test
    @DisplayName("불용어 읽어들이기 성공한다")
    fun t1() {
        val result = removeStopWords()
        assertEquals(1620, result.size) // 예를 들어 10개의 불용어가 있다고 가정
        assertEquals("조금", result[0]) // 첫 번째 불용어가 'the'라고 가정
    }
}