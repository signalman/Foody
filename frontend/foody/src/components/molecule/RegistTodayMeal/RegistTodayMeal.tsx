import React, { useState } from 'react';
import Tabbar from 'components/organism/Tabbar/Tabbar';
import CustomBottomSheet from '../CustomBottomSheet/CustomBottomSheet';

function RegistTodayMeal() {
	const [open, setOpen] = useState(false);

	return (
		<>
			<Tabbar setOpen={setOpen} />
			<CustomBottomSheet
				open={open}
				setOpen={() => {
					setOpen(!open);
				}}
			>
				<div>제목</div>
				<div>콘텐츠</div>
			</CustomBottomSheet>
		</>
	);
}

export default RegistTodayMeal;
