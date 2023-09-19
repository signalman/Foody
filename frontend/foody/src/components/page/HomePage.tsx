import React, { memo } from 'react';
import HomeTemplate from 'components/template/HomeTemplate/HomeTemplate';

const HomePage = memo(() => {
	return (
		<HomeTemplate>
			{/* 인사말 */}
			<>
				김싸피 님,
				<br />
				오늘도 건강한 하루 되세요!
			</>
			{/* 배너 */}
			배너
			{/* 오늘의 식사 칼로리 정보 */}
			오늘의 식사 82% 1,832kcal
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
