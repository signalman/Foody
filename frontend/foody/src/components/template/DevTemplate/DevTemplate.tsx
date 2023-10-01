import React, { useState } from 'react';
import './DevTemplate.scss';
import LayoutBottomMargin from 'constants/Margin';
import Header from 'components/organism/Header/Header';
import MealDetailNutrient from 'components/organism/meal/MealDetailNutrient/MealDetailNutrient';
import CheckMeal from 'components/molecule/CheckMeal/CheckMeal';
import { useRecoilValue } from 'recoil';
import nutrientState from 'recoil/atoms/nutrientState';
import MealCamera from 'components/atom/MealCamera/MealCamera';
import Layout from '../Layout/Layout';

function DevTemplate() {
	const nutrientData = useRecoilValue(nutrientState);
	const [open, setOpen] = useState<boolean>(false);
	const test = () => {
		setOpen(true);
	};

	if (open === true) {
		return <MealCamera />;
	}
	return (
		<Layout marginBottom={LayoutBottomMargin.mbTabbar}>
			<Header />
			<CheckMeal dan={3.1} ji={5.4} meal="아침" na={413.53} tan={3.1} mealTotal={373} mealValue={400} />
			<MealDetailNutrient />
			<p>test : {nutrientData.energy}</p>
			<p>{nutrientData.fats}</p>
			<button type="button" onClick={test}>
				테스트입니다!
			</button>
		</Layout>
	);
}

export default DevTemplate;
