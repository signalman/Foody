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
        assertEquals(1620, result.size)
        assertTrue(result.contains("조금"))
    }
}