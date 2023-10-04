import React from 'react';
import { HiOutlineChevronRight } from 'react-icons/hi';

interface DetailMealTitleProps {
	foodName: string;
	foodEnergy: number;
	openFoodInfo: () => void;
}
function DetailMealTitle({ foodName, foodEnergy, openFoodInfo }: DetailMealTitleProps) {
	return (
		<div className="detail-meal-title-container">
			<p>{foodName}</p>
			<button type="button" onClick={openFoodInfo} className="detail-meal-title-btn">
				<p>{foodEnergy}</p>
				<HiOutlineChevronRight size={16} />
			</button>
		</div>
	);
}

export default DetailMealTitle;
