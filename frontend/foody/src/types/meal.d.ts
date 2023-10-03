export interface Meal {
	total: {
		calcium: number;
		carbohydrates: number;
		dietaryFiber: number;
		energy: number;
		fats: number;
		iron: number;
		protein: number;
		sodium: number;
		vitaminA: number;
		vitaminC: number;
	};
	foods?: {
		name: string;
		nutrient: {
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
		};
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
	nutrientRequest: {
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
	};
}

export interface RegistSendData {
	type: string;
	date: string;
	foodRequestList: RegistMeal[];
}
