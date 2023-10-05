import React, { useEffect } from 'react';
import { GetMealData, NutrientTotal } from 'types/meal';
import SubHeader from 'components/organism/SubHeader/SubHeader';
import CheckMeal from '../CheckMeal/CheckMeal';
import DetailMealTable from '../DetailMealTable/DetailMealTable';

interface DetailMealProps {
	selectedDate: string;
	meal: string;
	getData: GetMealData;
	requireData: NutrientTotal;
	setDeleteOk: React.Dispatch<React.SetStateAction<boolean>>;
	setDetailOpen: React.Dispatch<React.SetStateAction<boolean>>;
	month: string;
	day: string;
}
function DetailMeal({
	selectedDate,
	meal,
	getData,
	requireData,
	setDeleteOk,
	setDetailOpen,
	month,
	day,
}: DetailMealProps) {
	const handleMove = () => {
		setDetailOpen((prev) => !prev);
	};

	useEffect(() => {
		if (getData.foods.length === 0) {
			setDetailOpen((prev) => !prev);
		}
	});

	return (
		<div>
			<SubHeader isBack handleMove={handleMove} title={`${month}월 ${day}일 ${meal}`} />
			<CheckMeal
				dan={getData.total.protein}
				ji={getData.total.fats}
				meal={meal}
				na={getData.total.sodium}
				tan={getData.total.carbohydrates}
				mealTotal={requireData.energy}
				mealValue={getData.total.energy}
			/>
			<DetailMealTable foods={getData.foods} meal={meal} selectedDate={selectedDate} setDeleteOk={setDeleteOk} />
		</div>
	);
}

export default DetailMeal;
