import BarChart from 'components/atom/BarChart/BarChart';
import DayofMealGoal from 'components/atom/DayofMealGoal/DayofMealGoal';
import DayofMealPercentage from 'components/atom/DayofMealPercentage/DayofMealPercentage';
import DayofMealTitle from 'components/atom/DayofMealTitle/DayofMealTitle';
import MealButton from 'components/atom/MealButton/MealButton';
import { BarColor } from 'constants/color';
import './DayofMealPart.scss';
import React, { Dispatch } from 'react';

interface DayofMealPartProps {
	meal: string;
	goal: number;
	value: number;
	imgsrc: string;
	setDetailOpen: Dispatch<React.SetStateAction<boolean>>;
	setSearchOpen: Dispatch<React.SetStateAction<boolean>>;
	setMeal: Dispatch<React.SetStateAction<string>>;
}
function DayofMealPart({ setDetailOpen, setSearchOpen, setMeal, meal, goal, value, imgsrc }: DayofMealPartProps) {
	const selectMove = () => {
		if (imgsrc) {
			setDetailOpen(true);
		} else {
			setSearchOpen(true);
		}
		setMeal(meal);
	};

	return (
		<div className="dayofmeal-part">
			<MealButton imgsrc={imgsrc} buttonClick={selectMove} />
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
