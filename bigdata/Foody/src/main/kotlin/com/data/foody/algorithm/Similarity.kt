package com.data.foody.algorithm

import smile.math.MathEx.*
import smile.math.distance.JaccardDistance
import smile.nlp.bag
import smile.nlp.df
import smile.nlp.vectorize

class Similarity {

    fun cosineSimilarity(refrigerator: String, ingredients: String): Double {
        val (bag1, bag2) = prepareBags(refrigerator, ingredients)
        val (vector1, vector2) = vectorizeBags(bag1, bag2)
        return cosineSimilarity(vector1, vector2)
    }

    fun tfidfSimilarity(refrigerator: String, ingredients: String): Double {
        val (bag1, bag2) = prepareBags(refrigerator, ingredients)
        val (vector1, vector2) = vectorizeBags(bag1, bag2)

        val documentFrequencies = df(bag1.keys.union(bag2.keys).toList(), listOf(bag1, bag2))
        val tfidfVectors = listOf(vector1, vector2).map { tfidf(it, 2, documentFrequencies) }

        return cosineSimilarity(tfidfVectors[0], tfidfVectors[1])
    }

    private fun cosineSimilarity(vec1: DoubleArray, vec2: DoubleArray): Double {

        val dotProduct = dot(vec1, vec2)
        val normA = norm(vec1)
        val normB = norm(vec2)

        return if (normA > 0.0 && normB > 0.0) dotProduct / (normA * normB) else 1.0
    }

    private fun tfidf(bag: DoubleArray, n: Int, df: IntArray): DoubleArray {
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

    fun jaccardSimilarity(refrigerator: String, ingredients: String): Double {
        val set1 = refrigerator.split(" ").toSet()
        val set2 = ingredients.split(" ").toSet()

        val jaccard = JaccardDistance.d(set1, set2)

        return 1 - jaccard
    }

    private fun prepareBags(refrigerator: String, ingredients: String): Pair<Map<String, Int>, Map<String, Int>> {
        val bag1 = refrigerator.bag(filter = " ", stemmer = null)
        val bag2 = ingredients.bag(filter = " ", stemmer = null)
        return bag1 to bag2
    }

    private fun vectorizeBags(bag1: Map<String, Int>, bag2: Map<String, Int>): Pair<DoubleArray, DoubleArray> {
        val terms = bag1.keys.union(bag2.keys).toTypedArray()
        return vectorize(terms, bag1) to vectorize(terms, bag2)
    }

}