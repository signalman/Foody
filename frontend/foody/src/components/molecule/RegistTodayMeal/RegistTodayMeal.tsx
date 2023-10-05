import React from 'react';
import './RegistTodayMeal.scss';
import Tabbar from 'components/organism/Tabbar/Tabbar';
import { useRecoilState, useRecoilValue } from 'recoil';
import tabbarState, { quickTodayMealRegiState } from 'recoil/atoms/tabbarState';
import CustomBottomSheet from '../CustomBottomSheet/CustomBottomSheet';
import SelectTodayMeal from '../SelectTodayMeal/SelectTodayMeal';

function RegistTodayMeal() {
	// TODOS: 오늘 식단 값 받아와서(없으면 빈 값) 추가할 수 있도록 바텀시트 띄워주기
	const [quickTodayMealRegiOn, setQuickTodayMealRegiOn] = useRecoilState(quickTodayMealRegiState);
	const tabbarOn = useRecoilValue(tabbarState);
	const today = new Date();
	const year = today.getFullYear();
	const month = String(today.getMonth() + 1).padStart(2, '0');
	const day = String(today.getDate()).padStart(2, '0');

	const sendDate = `${year}-${month}-${day}`;
	return (
		<>
			{tabbarOn && <Tabbar />}

			<CustomBottomSheet
				open={quickTodayMealRegiOn}
				setOpen={() => {
					setQuickTodayMealRegiOn(!quickTodayMealRegiOn);
				}}
			>
				<SelectTodayMeal selectedData={sendDate} />
			</CustomBottomSheet>
		</>
	);
}

export default RegistTodayMeal;
