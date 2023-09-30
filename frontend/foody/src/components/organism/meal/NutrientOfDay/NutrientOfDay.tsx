import React from 'react';
import './NutrientOfDay.scss';
import NutrientBar from 'components/molecule/NutrientBar/NutrientBar';

interface Nutrient {
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

interface MealTotal {
	total: Nutrient;
}

function NutrientOfDay({ total }: MealTotal) {
	const mealPageNutrient = {
		탄수화물: total.carbohydrates,
		단백질: total.protein,
		지방: total.fats,
	};

	return (
		<div>
			<NutrientBar ismore nutrient={mealPageNutrient} title="일일 영양소" />
		</div>
	);
}

export default NutrientOfDay;
