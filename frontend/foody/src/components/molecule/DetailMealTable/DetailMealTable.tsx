import DetailImg from 'components/atom/DetailImg/DetailImg';
import DetailMealCheck from 'components/atom/DetailMealCheck/DetailMealCheck';
import DetailMealTitle from 'components/atom/DetailMealTitle/DetailMealTitle';
import React from 'react';
import { GetMealFoodData } from 'types/meal';
import './DetailMealTable.scss';
import Layout from 'components/template/Layout/Layout';
import LayoutBottomMargin, { LayoutTopMargin } from 'constants/Margin';
import LayoutPadding from 'constants/Padding';
import { deleteMealInfo } from 'utils/api/meal';

interface DetailMealTableProbs {
	foods: GetMealFoodData[];
	meal: string;
	selectedDate: string;
}

function DetailMealTable({ foods, meal, selectedDate }: DetailMealTableProbs) {
	const deleteMeal = (num: number) => {
		if (meal === '아침') {
			deleteMealInfo(selectedDate, num, 'BREAKFAST').then((response) => {
				return response;
			});
		}
		if (meal === '점심') {
			deleteMealInfo(selectedDate, num, 'LUNCH').then((response) => {
				return response;
			});
		}
		if (meal === '저녁') {
			deleteMealInfo(selectedDate, num, 'DINNER').then((response) => {
				return response;
			});
		}
		if (meal === '간식') {
			deleteMealInfo(selectedDate, num, 'SNACK').then((response) => {
				return response;
			});
		}
	};

	return (
		<Layout marginTop={LayoutTopMargin.mt20} marginBottom={LayoutBottomMargin.mbTabbar} padding={LayoutPadding.p20}>
			<div className="detail-meal-table-container">
				<p className="detail-meal-table-title">식단 상세</p>
				<Layout marginTop={LayoutTopMargin.mt20} padding={LayoutPadding.p10}>
					<div className="detail-meal-table-contents">
						{foods &&
							foods.map((data, index) => (
								<div className="detail-meal-table-box">
									<DetailImg imgsrc={data.foodImage} />
									<div className="detail-meal-table-text">
										<DetailMealTitle foodName={data.name} foodEnergy={data.nutrient.energy} nutrient={data.nutrient} />
										<DetailMealCheck onDelete={() => deleteMeal(index)} />
									</div>
								</div>
							))}
					</div>
				</Layout>
			</div>
		</Layout>
	);
}

export default DetailMealTable;
