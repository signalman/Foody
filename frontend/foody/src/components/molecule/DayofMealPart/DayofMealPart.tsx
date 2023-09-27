import BarChart from 'components/atom/BarChart/BarChart';
import DayofMealGoal from 'components/atom/DayofMealGoal/DayofMealGoal';
import DayofMealPercentage from 'components/atom/DayofMealPercentage/DayofMealPercentage';
import DayofMealTitle from 'components/atom/DayofMealTitle/DayofMealTitle';
import MealButton from 'components/atom/MealButton/MealButton';
import { BarColor } from 'constants/color';
import './DayofMealPart.scss';
import React from 'react';

interface DayofMealPartProps {
	meal: string;
	goal: number;
	value: number;
}
function DayofMealPart({ meal, goal, value }: DayofMealPartProps) {
	const buttontest = () => {
		console.log(1);
	};
	return (
		<div className="dayofmeal-part">
			<MealButton buttonClick={buttontest} />
			<div className="dayofmeal-content">
				<DayofMealTitle meal={meal} value={value} />
				<BarChart barcolor={BarColor.Null} total={goal} value={value} />
				<div className="dayofmeal-content-goal">
					<DayofMealGoal goal={goal} />
					<DayofMealPercentage goal={goal} value={value} />
				</div>
			</div>
		</div>
	);
}

export default DayofMealPart;
