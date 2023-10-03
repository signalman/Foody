import React from 'react';
import './HomeShortcutButtons.scss';
import useMovePage from 'hooks/useMovePage';
import mainBabIcon from 'assets/icons/mainBabIcon.png';
import mainMealRecommendIcon from 'assets/icons/mainMealRecommendIcon.png';
import mainRefriIcon from 'assets/icons/mainRefriIcon.png';

function HomeShortcutButtons() {
	const { movePage } = useMovePage();
	const handleMovePage = (url: string) => {
		movePage(url, null);
	};

	return (
		<div className="home-shortcut-buttons-template grid-container">
			<button type="button" className="item left" onClick={() => handleMovePage('/meal')}>
				<div className="text-wrap">
					<h4>식단</h4>
					<span className="desc">건강한 한 끼의 시작</span>
				</div>
				<img src={mainBabIcon} alt="" />
			</button>
			<button type="button" className="item right-top" onClick={() => handleMovePage('/recommend')}>
				<img src={mainMealRecommendIcon} alt="" />
				<h4>레시피</h4>
			</button>
			<button type="button" className="item right-bottom" onClick={() => handleMovePage('/refri')}>
				<img src={mainRefriIcon} alt="" />
				<h4>냉장고 관리</h4>
			</button>
		</div>
	);
}

export default HomeShortcutButtons;
