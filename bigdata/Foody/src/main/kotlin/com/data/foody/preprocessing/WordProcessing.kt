package com.data.foody.preprocessing

import org.springframework.core.io.ClassPathResource
import java.io.BufferedReader
import java.io.InputStreamReader

fun main() {

    val list = removeStopWords()
    print(list.size)
    print(list[0])
}

fun removeStopWords(): MutableList<String> {
    val resource = ClassPathResource("data/preprocessing/stop_words.txt")
    val inputStream = resource.inputStream
    val reader = BufferedReader(InputStreamReader(inputStream, "UTF-8"))

    val wordList: MutableList<String> = mutableListOf()
    reader.useLines { lines ->
        lines.forEach { line ->
            wordList.add(line)
        }
    }

    return wordList
}