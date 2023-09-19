import React, { memo } from 'react';
import HomeTemplate from 'components/template/HomeTemplate/HomeTemplate';
import GreetingText from 'components/atom/GreetingText/GreetingText';
import BannerSlider from 'components/atom/BannerSlider/BannerSlider';
import CalorieOfDay from 'components/molecule/CalorieOfDay/CalorieOfDay';

const HomePage = memo(() => {
	return (
		<HomeTemplate>
			{/* 인사말 */}
			<GreetingText />
			{/* 배너 */}
			<BannerSlider />
			{/* 오늘의 식사 칼로리 정보 */}
			<CalorieOfDay />
			{/* 메뉴 목록 */}
			<>
				<button type="button">식단</button>
				<div>
					<button type="button">식단 추천</button>
					<button type="button">냉장고 관리</button>
				</div>
			</>
		</HomeTemplate>
	);
});

export default HomePage;
