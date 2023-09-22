import CarlorieChart from 'components/molecule/CarlorieChart/CarlorieChart';
import CarlorieCheckTable from 'components/molecule/CarlorieTable/CarlorieCheckTable';
import React from 'react';

function MealTable() {
	return (
		<div className="meal-table">
			<h3>OO님의 식단표</h3>
			<div>
				<CarlorieChart />
				<CarlorieCheckTable />
			</div>
		</div>
	);
}

export default MealTable;
