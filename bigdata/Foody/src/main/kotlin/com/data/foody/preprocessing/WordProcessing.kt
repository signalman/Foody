package com.data.foody.preprocessing

import org.springframework.core.io.ClassPathResource
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.FileOutputStream
import java.io.InputStreamReader
import java.io.OutputStreamWriter

fun main() {

    //val list = removeStopWords()
    // readAndFilterRecipe(list)

    val map = getReplaceWords()
    readAndReplaceRecipe(map)

}

fun readAndReplaceRecipe(map: HashMap<String, String> ) {
    val resource = ClassPathResource("data/preprocessing/filtered_final1_modified_stopwords.csv")
    val inputStream = resource.inputStream
    val reader = BufferedReader(InputStreamReader(inputStream, "EUC-KR"))

    val writer = BufferedWriter(OutputStreamWriter(FileOutputStream("C:\\Users\\SSAFY\\Desktop\\특화 빅데이터\\S09P22C106\\bigdata\\Foody\\src\\main\\resources\\data\\preprocessing\\filtered_final1_modified_replace.csv"), "EUC-KR"))

    reader.useLines { lines ->
        lines.forEach { line ->
            val cols = line.split(",")
            val ingredients = cols[14].split(" ").map { map.getOrDefault(it, it) }
            val replacedIngredients = ingredients.joinToString(" ")
            val modifiedLine = cols.subList(0, 14).joinToString(",") + ",$replacedIngredients"

            writer.write(modifiedLine)
            writer.newLine()
        }
    }

    writer.close()
}

fun removeStopWords(): Set<String> {
    val resource = ClassPathResource("data/preprocessing/stop_words.txt")
    val inputStream = resource.inputStream
    val reader = BufferedReader(InputStreamReader(inputStream, "UTF-8"))

    val stopWords = mutableSetOf<String>()
    reader.useLines { lines ->
        lines.forEach { line ->
            stopWords.add(line)
        }
    }

    return stopWords
}

fun readAndFilterRecipe(stopWords: Set<String>) {
    val resource = ClassPathResource("data/preprocessing/filtered_final1.csv")
    val inputStream = resource.inputStream
    val reader = BufferedReader(InputStreamReader(inputStream, "EUC-KR"))

    val writer = BufferedWriter(OutputStreamWriter(FileOutputStream("C:\\Users\\SSAFY\\Desktop\\특화 빅데이터\\S09P22C106\\bigdata\\Foody\\src\\main\\resources\\data\\preprocessing\\filtered_final1_modified_stopwords.csv"), "EUC-KR"))

    reader.useLines { lines ->
        lines.forEach { line ->
            val cols = line.split(",")
            val ingredients = cols[14].split(" ").filter { it !in stopWords }
            val filteredIngredients = ingredients.joinToString(" ")
            val modifiedLine = cols.subList(0, 14).joinToString(",") + ",$filteredIngredients"

            writer.write(modifiedLine)
            writer.newLine()
        }
    }

    writer.close()
}

fun getReplaceWords(): HashMap<String, String> {

    val resource = ClassPathResource("data/preprocessing/replace.txt")
    val inputStream = resource.inputStream
    val reader = BufferedReader(InputStreamReader(inputStream, "UTF-8"))

    val map:HashMap<String, String> = HashMap()

    reader.useLines { lines ->
        lines.forEach { line ->
            val cols = line.split(" ", limit = 2)
            if(cols.size == 1) println(cols[0])
            map[cols[0].trim()] = cols[1].trim()
        }
    }

    return map
}

