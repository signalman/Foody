import React from 'react';
import { GetMealFoodData } from 'types/meal';

interface DetailMealTableProbs {
	foods: GetMealFoodData[];
}

function DetailMealTable({ foods }: DetailMealTableProbs) {
	return (
		<div className="detail-meal-table-container">
			<p>식단 상세</p>
			<div className="detail-meal-table-contents">{foods && foods.map((data) => <div>{data.name}</div>)}</div>
		</div>
	);
}

export default DetailMealTable;
