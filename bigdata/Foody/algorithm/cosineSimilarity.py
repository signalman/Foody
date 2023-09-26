import pandas as pd
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.metrics.pairwise import cosine_similarity
import time


def get_top_recipes_with_time(ingredients: str, data: pd.DataFrame, top_k: int = 5) -> tuple:
    start_time = time.time()

    vectorizer = TfidfVectorizer()
    tfidf_matrix = vectorizer.fit_transform(data['ingredients_concat'])
    ingredients_vector = vectorizer.transform([ingredients])
    cosine_similarities = cosine_similarity(ingredients_vector, tfidf_matrix).flatten()
    top_indices = cosine_similarities.argsort()[-top_k:][::-1]

    end_time = time.time()
    elapsed_time = end_time - start_time

    top_recipes = [(data.iloc[index]['recipe_id'], cosine_similarities[index]) for index in top_indices]

    return top_recipes, elapsed_time


if __name__ == "__main__":
    recipe_data = pd.read_csv('assets/recipe_information.csv')
    recipe_data_cleaned = recipe_data.dropna(subset=['ingredients_concat'])
    test_ingredients = "어묵 김 당면 양파 당근"  # Modify as needed
    top_recipes, elapsed_time = get_top_recipes_with_time(test_ingredients, recipe_data_cleaned)
    print("Top Recipes:", top_recipes)
    print("Elapsed Time:", elapsed_time)
