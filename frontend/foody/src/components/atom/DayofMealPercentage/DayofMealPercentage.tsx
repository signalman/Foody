import React from 'react';
import './DayofMealPercentage.scss';

interface DayofMealPercentageProps {
	goal: number;
	value: number;
}
function DayofMealPercentage({ value, goal }: DayofMealPercentageProps) {
	const percent = ((value / goal) * 100).toFixed(2);

	return (
		<div>
			<p className="dayofmeal-percent">{percent} %</p>
		</div>
	);
}

export default DayofMealPercentage;
