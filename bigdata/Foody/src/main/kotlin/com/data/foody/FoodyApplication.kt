package com.data.foody

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class FoodyApplication

fun main(args: Array<String>) {
	runApplication<FoodyApplication>(*args)
}
