package com.data.foody.preprocessing

import org.springframework.core.io.ClassPathResource
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import kotlin.system.measureTimeMillis

lateinit var map:HashMap<String, Int>

fun main(args: Array<String>) {

    val elapsedTime = measureTimeMillis {
        readCSV()
    }
    println("Elapsed time: $elapsedTime ms")
    //   test()
}

fun test() {
    val fileName = "filtered_final.csv"
    val reader = BufferedReader(InputStreamReader(FileInputStream(fileName), "EUC-KR"))
    val set:HashSet<String> = HashSet()
    map = HashMap()
//    print(reader.readLine().split(",")[14])
    for(i in 0.. 10) {
        println(reader.readLine().split(",")[14])
    }
}

fun readCSV() {

    val resource = ClassPathResource("data/preprocessing/filtered_final1_modified_replace.csv")
    val inputStream = resource.inputStream
    val reader = BufferedReader(InputStreamReader(inputStream, "EUC-KR"))
    val set:HashSet<String> = HashSet()
    map = HashMap()
//    print(reader.readLine().split(",")[14])
    reader.useLines { lines ->
        lines.forEach { line ->
            val cols = line.split(",")
            val ingredient: List<String> = cols[14].split(" ")

            for (e in ingredient) {
                map[e] = map.getOrDefault(e, 0) + 1
            }
        }
    }

    // 값으로 내림차순 정렬
    val sortedMapDesc = map.toList().sortedByDescending { (_, value) -> value }.toMap()

    // UTF-8로 인코딩하여 오름차순 정렬된 HashMap 내용을 텍스트 파일에 저장
    val outputFileNameUTF8Asc = "output_map_utf8_asc.txt"
    BufferedWriter(OutputStreamWriter(FileOutputStream(outputFileNameUTF8Asc), "UTF-8")).use { writer ->
        for ((key, value) in sortedMapDesc) {
            writer.write("$key: $value\n")
        }
    }

    // UTF-8로 인코딩하여 내림차순 정렬된 HashMap 내용을 텍스트 파일에 저장
    val outputFileNameUTF8Desc = "output_map_utf8_desc.txt"
    BufferedWriter(OutputStreamWriter(FileOutputStream(outputFileNameUTF8Desc), "UTF-8")).use { writer ->
        for ((key, value) in sortedMapDesc) {
            writer.write("$key: $value\n")
        }
    }

    println("result : Sorted Map convert to txt names - $outputFileNameUTF8Asc, $outputFileNameUTF8Desc")
    println("########## ingredient size : ${map.size}")
//    preprocessing()
}

fun preprocessing() {
    // Step 1: 빈도수가 3 이하인 단어의 목록을 생성
    val lowFrequencyWords = map.filter { it.value <= 3 }.keys

// Step 2: 원래의 CSV 파일을 읽기
    val reader = BufferedReader(InputStreamReader(FileInputStream("final.csv"), "EUC-KR"))
    val writer = BufferedWriter(OutputStreamWriter(FileOutputStream("filtered_final.csv"), "EUC-KR"))

    reader.useLines { lines ->
        lines.forEach { line ->
            // Step 3: 재료 목록에서 빈도수가 3 이하인 단어를 제거
            val cols = line.split(",")
            val ingredients = cols[14].split(" ").filter { it !in lowFrequencyWords }

            // Step 4: 새로운 레코드 생성
            val newLine = cols.subList(0, 14) + ingredients.joinToString(" ")

            // Step 5: 새로운 레코드를 새로운 CSV 파일에 저장
            writer.write(newLine.joinToString(","))
            writer.newLine()
        }
    }
    writer.close()

}

