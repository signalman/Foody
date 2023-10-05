import React, { memo, useCallback, useEffect } from 'react';
import HomeTemplate from 'components/template/HomeTemplate/HomeTemplate';
import GreetingText from 'components/atom/GreetingText/GreetingText';
import BannerSlider from 'components/atom/BannerSlider/BannerSlider';
import CalorieOfDay from 'components/molecule/CalorieOfDay/CalorieOfDay';
import HomeShortcutButtons from 'components/molecule/HomeShortcutButtons/HomeShortcutButtons';
import { useRecoilValue, useSetRecoilState } from 'recoil';
import nutrientState, {
	breakfastState,
	dinnerState,
	lunchState,
	snackState,
	userInfoState,
} from 'recoil/atoms/nutrientState';
import { getDaoyofNutrient, getUserMealInfo } from 'utils/api/meal';
import { NutrientTotal } from 'types/meal';
import { getUserInfo } from 'utils/api/auth';
import tabbarState from 'recoil/atoms/tabbarState';

const HomePage = memo(() => {
	const tabberOn = useRecoilValue(tabbarState);
	const setNutrientData = useSetRecoilState(nutrientState);
	const setBreakfastState = useSetRecoilState(breakfastState);
	const setLunchState = useSetRecoilState(lunchState);
	const setDinnerState = useSetRecoilState(dinnerState);
	const setSnackState = useSetRecoilState(snackState);
	const setUserInfo = useSetRecoilState(userInfoState);

	const getData = useCallback(() => {
		getDaoyofNutrient().then((response) => {
			const responseData: NutrientTotal = response.data;

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

		getUserInfo().then((res) => {
			setUserInfo({
				nickname: res.data.nickname,
			});
		});
	}, [setBreakfastState, setDinnerState, setLunchState, setNutrientData, setSnackState, setUserInfo]);

	useEffect(() => {
		getData();
	}, [
		getData,
		setBreakfastState,
		setDinnerState,
		setLunchState,
		setNutrientData,
		setSnackState,
		setUserInfo,
		tabberOn,
	]);

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
