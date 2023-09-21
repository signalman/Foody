import React, { memo } from 'react';
import HomeTemplate from 'components/template/HomeTemplate/HomeTemplate';
import GreetingText from 'components/atom/GreetingText/GreetingText';
import BannerSlider from 'components/atom/BannerSlider/BannerSlider';
import CalorieOfDay from 'components/molecule/CalorieOfDay/CalorieOfDay';
import HomeShortcutButtons from 'components/molecule/HomeShortcutButtons/HomeShortcutButtons';

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
			<HomeShortcutButtons />
		</HomeTemplate>
	);
});

export default HomePage;
