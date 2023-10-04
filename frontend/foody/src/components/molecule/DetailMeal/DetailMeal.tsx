import React from 'react';
import { GetMealData, NutrientTotal } from 'types/meal';
import CheckMeal from '../CheckMeal/CheckMeal';
import DetailMealTable from '../DetailMealTable/DetailMealTable';

interface DetailMealProps {
	selectedDate: string;
	meal: string;
	getData: GetMealData;
	requireData: NutrientTotal;
}
function DetailMeal({ selectedDate, meal, getData, requireData }: DetailMealProps) {
	return (
		<div>
			<p>{selectedDate}</p>
			<CheckMeal
				dan={getData.total.protein}
				ji={getData.total.fats}
				meal={meal}
				na={getData.total.sodium}
				tan={getData.total.carbohydrates}
				mealTotal={requireData.energy}
				mealValue={getData.total.energy}
			/>
			<DetailMealTable foods={getData.foods} meal={meal} selectedDate={selectedDate} />
		</div>
	);
}

export default DetailMeal;
