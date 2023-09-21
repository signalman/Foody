import React from 'react';
import './HomeShortcutButtons.scss';
import useMovePage from 'hooks/useMovePage';

function HomeShortcutButtons() {
	const { movePage } = useMovePage();
	const handleMovePage = (url: string) => {
		movePage(url, null);
	};

	return (
		<div className="home-shortcut-buttons-template grid-container">
			<button type="button" className="item left" onClick={() => handleMovePage('/meal')}>
				<h4>식단</h4>
				<span className="desc">건강한 한 끼의 시작</span>
				<img src="" alt="" />
			</button>
			<button type="button" className="item right-top" onClick={() => handleMovePage('/recommend')}>
				<h4>식단 추천</h4>
				<img src="" alt="" />
			</button>
			<button type="button" className="item right-bottom" onClick={() => handleMovePage('/refri')}>
				<h4>냉장고 관리</h4>
				<img src="" alt="" />
			</button>
		</div>
	);
}

export default HomeShortcutButtons;
