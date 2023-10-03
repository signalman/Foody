import React from 'react';
import './CalorieInTable.scss';
import classNames from 'classnames';
import { MealColor } from 'constants/color';

interface CalorieInTableProps {
	mealColor?: MealColor;
	meal: string;
	value: number;
	total: number;
}

function CalorieInTable({ mealColor, meal, value, total }: CalorieInTableProps) {
	return (
		<div className="meal-box">
			<div className="meal-check">
				<div className={classNames('meal-color', mealColor)} />
				<p className="meal-title">{meal}</p>
			</div>
			<div className="meal-info">
				<p className="meal-value">{value}</p>
				<p className="meal-total">/ {total}</p>
			</div>
		</div>
	);
}

export default CalorieInTable;

CalorieInTable.defaultProps = {
	mealColor: MealColor.Breakfast,
};
