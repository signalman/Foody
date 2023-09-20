import React from 'react';
import './Tabbar.scss';
import TabbarItem from 'components/atom/TabbarItem/TabbarItem';
import TABBER_MENUS from 'constants/Tabbar';
import { BiPlus } from 'react-icons/bi';

function Tabbar() {
	const handleAddDayMeal = () => {
		alert('오늘의 식단 추가');
	};

	return (
		<nav className="tabbar-container">
			<ul>
				<TabbarItem menu={TABBER_MENUS[0]} />
				<TabbarItem menu={TABBER_MENUS[1]} />
				<li className="indicator">
					<button type="button" onClick={handleAddDayMeal}>
						<BiPlus className="plus-icon" size={30} />
					</button>
				</li>
				<div className="blank-item" />
				<TabbarItem menu={TABBER_MENUS[2]} />
				<TabbarItem menu={TABBER_MENUS[3]} />
			</ul>
		</nav>
	);
}

export default Tabbar;
