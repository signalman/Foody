import React, { useState } from 'react';
import { HiOutlineChevronRight } from 'react-icons/hi';
import './DetailMealTitle.scss';
import { NutrientTotal } from 'types/meal';
import MealDetailNutrient from 'components/organism/meal/MealDetailNutrient/MealDetailNutrient';

interface DetailMealTitleProps {
	foodName: string;
	foodEnergy: number;
	nutrient: NutrientTotal;
}
function DetailMealTitle({ foodName, foodEnergy, nutrient }: DetailMealTitleProps) {
	const [open, setOpen] = useState<boolean>(false);

	const openMealInfo = () => {
		setOpen(true);
	};

	return (
		<div className="detail-meal-title-container">
			<p>{foodName}</p>
			<button type="button" onClick={openMealInfo} className="detail-meal-title-btn">
				<p>{foodEnergy}kcal</p>
				<HiOutlineChevronRight size={10} />
			</button>
			{open && <MealDetailNutrient name={foodName} nutrient={nutrient} open={open} setOpen={setOpen} />}
		</div>
	);
}

export default DetailMealTitle;
