export interface Meal {
	total: NutrientTotal;
	foods?: {
		name: string;
		nutrient: NutrientTotal;
	}[];
	imgUrl: string;
	time: string;
}

export interface NutrientTotal {
	energy: number;
	carbohydrates: number;
	protein: number;
	dietaryFiber: number;
	calcium: number;
	sodium: number;
	iron: number;
	fats: number;
	vitaminA: number;
	vitaminC: number;
}

export interface RegistMeal {
	name: string;
	nutrientRequest: NutrientTotal;
}

export interface RegistSendData {
	type: string;
	date: string;
	foodRequestList: RegistMeal[];
}

export interface GetMealData {
	total: NutrientTotal;
	foods: getMealFoodData[];
	imgUrl: string;
	time: string;
}

export interface GetMealFoodData {
	foodImage: string;
	name: string;
	nutrient: NutrientTotal;
}
