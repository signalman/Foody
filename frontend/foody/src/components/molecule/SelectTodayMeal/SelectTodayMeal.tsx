import Layout from 'components/template/Layout/Layout';
import LayoutBottomMargin, { LayoutTopMargin } from 'constants/Margin';
import LayoutPadding from 'constants/Padding';
import './SelectTodayMeal.scss';
import React, { useState } from 'react';
import MealSearch from 'components/organism/meal/MealSearch/MealSearch';

interface SelectTodayMealProps {
	selectedData: string;
}
function SelectTodayMeal({ selectedData }: SelectTodayMealProps) {
	const mealList = ['아침', '점심', '저녁', '간식'];
	const [meal, setMeal] = useState<string>('');
	const [open, setOpen] = useState<boolean>(false);

	const mealSelectClick = (data: string) => {
		setOpen(true);
		setMeal(data);
	};

	if (open === true && meal) {
		return <MealSearch setOpen={setOpen} meal={meal} selectedDate={selectedData} isplus />;
	}

	return (
		<Layout marginTop={LayoutTopMargin.mt30} marginBottom={LayoutBottomMargin.mb0} padding={LayoutPadding.p20}>
			<div className="regist-today-meal-div">
				<p className="regist-today-meal-p">오늘의 식단 추가</p>
			</div>
			<div className="select-today-meal-container">
				<div className="select-today-meal-sub">
					<button className="select-today-meal-btn" type="button" onClick={() => mealSelectClick(mealList[0])}>
						{mealList[0]}
					</button>
					<button className="select-today-meal-btn" type="button" onClick={() => mealSelectClick(mealList[1])}>
						{mealList[1]}
					</button>
				</div>
				<div className="select-today-meal-sub">
					<button className="select-today-meal-btn" type="button" onClick={() => mealSelectClick(mealList[2])}>
						{mealList[2]}
					</button>
					<button className="select-today-meal-btn" type="button" onClick={() => mealSelectClick(mealList[3])}>
						{mealList[3]}
					</button>
				</div>
			</div>
		</Layout>
	);
}

export default SelectTodayMeal;
