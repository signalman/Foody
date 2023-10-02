import React, { memo, useEffect } from 'react';
import HomeTemplate from 'components/template/HomeTemplate/HomeTemplate';
import GreetingText from 'components/atom/GreetingText/GreetingText';
import BannerSlider from 'components/atom/BannerSlider/BannerSlider';
import CalorieOfDay from 'components/molecule/CalorieOfDay/CalorieOfDay';
import HomeShortcutButtons from 'components/molecule/HomeShortcutButtons/HomeShortcutButtons';
import { useSetRecoilState } from 'recoil';
import nutrientState, { breakfastState, dinnerState, lunchState, snackState } from 'recoil/atoms/nutrientState';
import { getDaoyofNutrient, getUserMealInfo } from 'utils/api/meal';
import { NutrientTotal } from 'types/meal';

const HomePage = memo(() => {
	const setNutrientData = useSetRecoilState(nutrientState);
	const setBreakfastState = useSetRecoilState(breakfastState);
	const setLunchState = useSetRecoilState(lunchState);
	const setDinnerState = useSetRecoilState(dinnerState);
	const setSnackState = useSetRecoilState(snackState);

	useEffect(() => {
		getDaoyofNutrient().then((response) => {
			const responseData: NutrientTotal = response.data;
			console.log(responseData);
			setNutrientData({
				calcium: responseData.calcium,
				carbohydrates: responseData.carbohydrates,
				dietaryFiber: responseData.dietaryFiber,
				energy: responseData.energy,
				fats: responseData.fats,
				iron: responseData.iron,
				protein: responseData.protein,
				sodium: responseData.sodium,
				vitaminA: responseData.vitaminA,
				vitaminC: responseData.vitaminC,
			});
		});

		getUserMealInfo().then((response) => {
			setBreakfastState(response.data.breakfast);
			setLunchState(response.data.lunch);
			setDinnerState(response.data.dinner);
			setSnackState(response.data.snack);
		});
	}, [setBreakfastState, setDinnerState, setLunchState, setNutrientData, setSnackState]);

	return (
		<HomeTemplate>
			{/* 인사말 */}
			<GreetingText />
			{/* 배너 */}
			<BannerSlider />
			{/* 오늘의 식사 칼로리 정보 */}
			<CalorieOfDay />
			{/* 메뉴 목록 */}
			<HomeShortcutButtons />
		</HomeTemplate>
	);
});

export default HomePage;
