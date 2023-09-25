import DonutChart from 'components/atom/DonutChart/DonutChart';
import CarlorieCheckTable from 'components/molecule/CarlorieTable/CarlorieCheckTable';
import React from 'react';
import './MealTable.scss';

function MealTable() {
	const data = {
		breakfast: 340,
		lunch: 482,
		dinner: 544,
		snack: 424,

		totalBreakfast: 373,
		totalLunch: 487,
		totalDinner: 487,
		totalSnack: 233,

		calorie: 2300,
	};

	const total = data.breakfast + data.lunch + data.dinner + data.snack;

	return (
		<div className="meal-table">
			<h3>OO님의 식단표</h3>
			<div className="meal-data">
				<DonutChart
					breakfast={data.breakfast}
					lunch={data.lunch}
					dinner={data.dinner}
					snack={data.snack}
					testBreakfast={data.totalBreakfast}
					testLunch={data.totalLunch}
					testDinner={data.totalDinner}
					testSnack={data.totalSnack}
					carolie={data.calorie}
				/>
				<CarlorieCheckTable
					breakfast={data.breakfast}
					lunch={data.lunch}
					dinner={data.dinner}
					snack={data.snack}
					totalBreakfast={data.totalBreakfast}
					totalLunch={data.totalLunch}
					totalDinner={data.totalDinner}
					totalSnack={data.totalSnack}
					calorie={total}
				/>
			</div>
		</div>
	);
}

export default MealTable;
