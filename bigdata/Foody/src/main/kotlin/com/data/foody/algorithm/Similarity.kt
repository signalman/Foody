package com.data.foody.algorithm

import smile.math.MathEx.*
import smile.math.distance.JaccardDistance
import smile.nlp.bag
import smile.nlp.df
import smile.nlp.vectorize

class Similarity {

    fun cosineSimilarity(refrigerator: String, ingredients: String): Double {

        // Bag of Words 생성
        val bag1 = refrigerator.bag(filter = " ", stemmer = null)
        val bag2 = ingredients.bag(filter = " ", stemmer = null)

        val terms = bag1.keys.union(bag2.keys).toTypedArray()

        val vector1 = vectorize(terms, bag1)
        val vector2 = vectorize(terms, bag2)

        return cosineSimilarity(vector1, vector2)
    }

    fun TfIdfSimilarity(refrigerator: String, ingredients: String): Double {

        // Bag of Words 생성
        val bag1 = refrigerator.bag(filter = " ", stemmer = null)
        val bag2 = ingredients.bag(filter = " ", stemmer = null)

        val terms = bag1.keys.union(bag2.keys).toTypedArray()

        val vector1 = vectorize(terms, bag1)
        val vector2 = vectorize(terms, bag2)

        val corpus = listOf(bag1, bag2)

        val documentFrequencies = df(terms.toList(), corpus)

        val corp = listOf(vector1, vector2)

        val tfidfVectors = corp.map { bag ->
            tfidf(bag, corpus.size, documentFrequencies)
        }

        return cosineSimilarity(tfidfVectors[0], tfidfVectors[1])
    }

    fun cosineSimilarity(vec1: DoubleArray, vec2: DoubleArray): Double {

        val dotProduct = dot(vec1, vec2)
        val normA = norm(vec1)
        val normB = norm(vec2)

        println("dotProduct : $dotProduct")
        println("normA : $normA")
        println("normA : $normB")
        return if (normA > 0.0 && normB > 0.0) dotProduct / (normA * normB) else 1.0
    }

    fun tfidf(bag: DoubleArray, n: Int, df: IntArray): DoubleArray {
        val maxtf = bag.maxOrNull() ?: 0.0
        val features = DoubleArray(bag.size)
        for (i in features.indices) {
            val idf = if (df[i] > 0) log((n + 1.0) / (df[i] + 1.0)) + 1.0 else 0.0
            features[i] = (bag[i] / maxtf) * idf
        }
        val norm = norm(features)
        for (i in features.indices) {
            features[i] /= norm
        }
        return features
    }

    fun <T> jaccardSimilarity(refrigerator: String, ingredients: String): Double {
        val set1 = refrigerator.split(" ").toSet()
        val set2 = ingredients.split(" ").toSet()

        val jaccard = JaccardDistance.d(set1, set2)

        return 1 - jaccard
    }

}