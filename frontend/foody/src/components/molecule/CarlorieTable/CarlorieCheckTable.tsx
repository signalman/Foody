import React from 'react';
import './CarlorieCheckTable.scss';
import CalorieInTable from 'components/atom/CalorieInTable/CalorieInTable';
import { MealColor } from 'constants/color';

interface CalorieCheckTalbeProps {
	breakfast: number;
	lunch: number;
	dinner: number;
	snack: number;

	totalBreakfast: number;
	totalLunch: number;
	totalDinner: number;
	totalSnack: number;

	calorie: number;
}
function CarlorieCheckTable({
	breakfast,
	lunch,
	dinner,
	snack,
	totalBreakfast,
	totalLunch,
	totalDinner,
	totalSnack,
	calorie,
}: CalorieCheckTalbeProps) {
	return (
		<div className="carlorie-check-table">
			<div className="meal-calorie">
				<CalorieInTable meal="아침" total={totalBreakfast} value={breakfast} mealColor={MealColor.Breakfast} />
				<CalorieInTable meal="점심" total={totalLunch} value={lunch} mealColor={MealColor.Lunch} />
				<CalorieInTable meal="저녁" total={totalDinner} value={dinner} mealColor={MealColor.Dinner} />
				<CalorieInTable meal="간식" total={totalSnack} value={snack} mealColor={MealColor.Snack} />
			</div>
			<div className="total-calorie">
				<p className="total">{calorie}Kcal</p>
				<p className="get-meal">섭취</p>
			</div>
		</div>
	);
}

export default CarlorieCheckTable;
