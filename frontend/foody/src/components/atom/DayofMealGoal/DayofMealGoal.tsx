import React from 'react';
import './DayofMealGoal.scss';

interface DayofMealGoalProps {
	goal: number;
}
function DayofMealGoal({ goal }: DayofMealGoalProps) {
	return (
		<div className="dayofmeal-goal-box">
			<p>목표</p>
			<p>{goal}kcal</p>
		</div>
	);
}

export default DayofMealGoal;
