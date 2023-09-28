export interface IngredientSearchItem {
	key: long; // 재료 ID
	text: string; // 재료명
}

export interface IngredientSearchResultItem {
	ingredientId: long; // 재료 ID
	ingredientName: string; // 재료명
}

export interface CustomIngredientItemType {
	ingredientCategoryId: number;
	ingredientName: string;
}
