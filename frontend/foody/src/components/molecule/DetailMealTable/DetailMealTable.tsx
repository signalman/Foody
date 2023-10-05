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
import toast from 'react-hot-toast';

interface DetailMealTableProbs {
	foods: GetMealFoodData[];
	meal: string;
	selectedDate: string;
	setDeleteOk: React.Dispatch<React.SetStateAction<boolean>>;
}

function DetailMealTable({ foods, meal, selectedDate, setDeleteOk }: DetailMealTableProbs) {
	const deleteMeal = (num: number) => {
		let time = 'BREAKFAST';

		if (meal === '아침') {
			time = 'BREAKFAST';
		}
		if (meal === '점심') {
			time = 'LUNCH';
		}
		if (meal === '저녁') {
			time = 'DINNER';
		}
		if (meal === '간식') {
			time = 'SNACK';
		}

		deleteMealInfo(selectedDate, num, time).then((response) => {
			setDeleteOk(true);
			console.log('response', response);
			toast.success('식단이 삭제 되었습니다.');
		});
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
