import React from 'react';
import './Tabbar.scss';
import TabbarItem from 'components/atom/TabbarItem/TabbarItem';
import TABBER_MENUS from 'constants/TabbarList';
import { BiPlus } from 'react-icons/bi';
import { useRecoilState } from 'recoil';
import tabbarState, { quickTodayMealRegiState } from 'recoil/atoms/tabbarState';
import useTabbarRender from 'hooks/useTabbarRender';

function Tabbar() {
	const [quickTodayMealRegiOn, setQuickTodayMealRegiOn] = useRecoilState(quickTodayMealRegiState);
	const [tabbarOn, setTabbarOn] = useRecoilState(tabbarState);

	if (useTabbarRender() && tabbarOn) {
		return (
			<nav className="tabbar-container">
				<ul>
					<TabbarItem menu={TABBER_MENUS[0]} />
					<TabbarItem menu={TABBER_MENUS[1]} />
					<li className="indicator">
						<button
							type="button"
							onClick={() => {
								setTabbarOn(false);
								setQuickTodayMealRegiOn(!quickTodayMealRegiOn);
							}}
						>
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

	return <div />;
}

export default Tabbar;
