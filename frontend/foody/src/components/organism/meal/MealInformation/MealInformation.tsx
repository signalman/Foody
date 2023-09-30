import React from 'react';
import './MealInformation.scss';
import DayofMealPart from 'components/molecule/DayofMealPart/DayofMealPart';

interface Meal {
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

interface DailyMeals {
	breakfast: Meal;
	lunch: Meal;
	dinner: Meal;
	snack: Meal;
}

function MealInformation({ breakfast, lunch, dinner, snack }: DailyMeals) {
	console.log(breakfast);
	console.log(lunch);
	console.log(dinner);
	console.log(snack);
	return (
		<div className="meal-information-box">
			<p className="meal-information-title">일일 식단 정보</p>
			<div className="meal-information">
				<DayofMealPart goal={373} meal="아침" value={200} />
				<DayofMealPart goal={373} meal="점심" value={200} />
				<DayofMealPart goal={373} meal="저녁" value={200} />
				<DayofMealPart goal={373} meal="간식" value={200} />
			</div>
		</div>
	);
}

export default MealInformation;
