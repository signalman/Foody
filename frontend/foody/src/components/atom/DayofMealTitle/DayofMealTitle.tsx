import React from 'react';
import './DayofMealTitle.scss';

interface DayofMealTitleProps {
	meal: string;
	value: number;
}

function DayofMealTitle({ meal, value }: DayofMealTitleProps) {
	return (
		<div className="dayofmeal-title-box">
			<p>{meal}</p>
			<p>{value > 0 ? value : '---'} kcal</p>
		</div>
	);
}

export default DayofMealTitle;
