package com.data.foody.preprocessing

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class WordProcessingKtTest {

    @Test
    @DisplayName("불용어 읽어들이기 성공한다")
    fun t1() {
        val result = removeStopWords()
        assertEquals(1614, result.size)
        assertTrue(result.contains("조금"))
    }

    @Test
    @DisplayName("split limit처리 성공한다")
    fun t2() {
        val str = "하나 두울 셋"

        val arr = str.split(" ", limit = 2)
        print(arr)
        assertTrue(arr.size == 2)
    }
}