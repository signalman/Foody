import React, { useState } from 'react';
import './RegistTodayMeal.scss';
import Tabbar from 'components/organism/Tabbar/Tabbar';
import classNames from 'classnames';
import CustomBottomSheet from '../CustomBottomSheet/CustomBottomSheet';

function RegistTodayMeal() {
	// TODOS: 오늘 식단 값 받아와서(없으면 빈 값) 추가할 수 있도록 바텀시트 띄워주기
	const [open, setOpen] = useState(false);

	return (
		<>
			<div className={classNames(open && 'regist-today-meal-tabbar-container')}>
				<Tabbar setOpen={setOpen} />
			</div>

			<CustomBottomSheet
				open={open}
				setOpen={() => {
					setOpen(!open);
				}}
			>
				<div>식단 추가</div>
				<div>
					Lorem ipsum dolor sit amet consectetur, adipisicing elit. Voluptatem, dolores deleniti quos tenetur quibusdam
					eos id totam quod aut suscipit adipisci quam mollitia laborum et aliquam, repellat perspiciatis hic saepe?
				</div>
			</CustomBottomSheet>
		</>
	);
}

export default RegistTodayMeal;
