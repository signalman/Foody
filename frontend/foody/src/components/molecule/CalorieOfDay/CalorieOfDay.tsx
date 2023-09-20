import React from 'react';
import './CalorieOfDay.scss';
import HomeCalorieState from 'components/atom/HomeCalorieState/HomeCalorieState';

function CalorieOfDay() {
	return (
		<div className="calorie-of-day-container">
			<h4>오늘의 식사</h4>
			<HomeCalorieState />
		</div>
	);
}

export default CalorieOfDay;
