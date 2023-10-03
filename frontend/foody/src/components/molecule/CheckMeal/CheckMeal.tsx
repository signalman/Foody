import MealChart from 'components/atom/MealChart/MealChart';
import React from 'react';
import MealNutrientSimpleInfo from '../MealNutrientSimpleInfo/MealNutrientSimpleInfo';
import './CheckMeal.scss';

interface CheckMealProps {
	meal: string;
	mealTotal: number;
	mealValue: number;
	tan: number;
	dan: number;
	ji: number;
	na: number;
}
function CheckMeal({ meal, mealTotal, mealValue, tan, dan, ji, na }: CheckMealProps) {
	return (
		<div className="check-meal-box">
			<div className="check-meal-title">
				<div className="check-meal-sub-box">
					<p>{meal} 식단</p>
					<p className="check-meal-title-cal">{mealValue}kcal</p>
				</div>
				<div>
					<button type="button">hello</button>
				</div>
			</div>
			<div className="check-meal-content">
				<MealChart meal={meal} total={mealTotal} value={mealValue} />
				<MealNutrientSimpleInfo meal={meal} tan={tan} dan={dan} ji={ji} na={na} />
			</div>
		</div>
	);
}

export default CheckMeal;
