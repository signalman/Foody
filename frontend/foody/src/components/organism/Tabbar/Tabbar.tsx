import React, { Dispatch } from 'react';
import './Tabbar.scss';
import TabbarItem from 'components/atom/TabbarItem/TabbarItem';
import TABBER_MENUS from 'constants/Tabbar';
import { BiPlus } from 'react-icons/bi';

function Tabbar({ setOpen }: { setOpen: Dispatch<React.SetStateAction<boolean>> }) {
	const handleAddDayMeal = () => {
		console.log('클릭');
		setOpen((prev) => !prev);
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
