import DonutChart from 'components/atom/DonutChart/DonutChart';
import CarlorieCheckTable from 'components/molecule/CarlorieTable/CarlorieCheckTable';
import React from 'react';
import './MealTable.scss';
import { Meal, NutrientTotal } from 'types/meal';
import { useRecoilValue } from 'recoil';
import nutrientState, { breakfastState, dinnerState, lunchState, snackState } from 'recoil/atoms/nutrientState';

interface DailyMeals {
	breakfast: Meal;
	lunch: Meal;
	dinner: Meal;
	snack: Meal;
	total: NutrientTotal;
}

function MealTable({ breakfast, lunch, dinner, snack, total }: DailyMeals) {
	const nutriState = useRecoilValue(nutrientState);
	const breakState = useRecoilValue(breakfastState);
	const lunState = useRecoilValue(lunchState);
	const dinState = useRecoilValue(dinnerState);
	const snaState = useRecoilValue(snackState);

	return (
		<div className="meal-table">
			<h3>OO님의 식단표</h3>
			<div className="meal-data">
				<DonutChart
					breakfast={breakfast.total.energy}
					lunch={lunch.total.energy}
					dinner={dinner.total.energy}
					snack={snack.total.energy}
					testBreakfast={breakState.energy}
					testLunch={lunState.energy}
					testDinner={dinState.energy}
					testSnack={snaState.energy}
					carolie={nutriState.energy}
				/>
				<CarlorieCheckTable
					breakfast={breakfast.total.energy}
					lunch={lunch.total.energy}
					dinner={dinner.total.energy}
					snack={snack.total.energy}
					totalBreakfast={breakState.energy}
					totalLunch={lunState.energy}
					totalDinner={dinState.energy}
					totalSnack={snaState.energy}
					calorie={total.energy}
				/>
			</div>
		</div>
	);
}

export default MealTable;
