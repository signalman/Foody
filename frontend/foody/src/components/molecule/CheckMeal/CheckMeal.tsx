import React from 'react';
import './CheckMeal.scss';
import Layout from 'components/template/Layout/Layout';
import LayoutBottomMargin, { LayoutTopMargin } from 'constants/Margin';
import LayoutPadding from 'constants/Padding';
import MealChart from 'components/atom/MealChart/MealChart';
import MealNutrientSimpleInfo from '../MealNutrientSimpleInfo/MealNutrientSimpleInfo';

interface CheckMealProps {
	meal: string;
	mealTotal: number;
	mealValue: number;
	tan: number;
	dan: number;
	ji: number;
	na: number;
}
function CheckMeal({ meal, mealTotal, mealValue, tan, dan, ji, na }: CheckMealProps) {
	return (
		<Layout marginTop={LayoutTopMargin.mt30} marginBottom={LayoutBottomMargin.mb20} padding={LayoutPadding.p20}>
			<div className="check-meal-box">
				<div className="check-meal-title">
					<div className="check-meal-sub-box">
						<p>{meal} 식단</p>
						<p className="check-meal-title-cal">{mealValue}kcal</p>
					</div>
				</div>
				<Layout marginTop={LayoutTopMargin.mt20} padding={LayoutPadding.p10}>
					<div className="check-meal-content">
						<MealChart meal={meal} total={mealTotal} value={mealValue} />
						<MealNutrientSimpleInfo meal={meal} tan={tan} dan={dan} ji={ji} na={na} />
					</div>
				</Layout>
			</div>
		</Layout>
	);
}

export default CheckMeal;
