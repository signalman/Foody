import React, { useEffect, useState } from 'react';
import './TabbarItem.scss';
import { ITabbarItem } from 'constants/TabbarList';
import { Link, useLocation } from 'react-router-dom';

function TabbarItem({ menu }: { menu: ITabbarItem }) {
	const [isActive, setIsActive] = useState<boolean>(false);
	const location = useLocation();

	useEffect(() => {
		setIsActive(location.pathname.includes(`/${menu.url}`));
	}, [location.pathname, menu.url]);

	return (
		<li className="tabbar-item-container">
			<Link to={`/${menu.url}`}>
				{isActive ? <menu.activeIcon /> : <menu.icon />}
				{menu.menu}
			</Link>
		</li>
	);
}

export default TabbarItem;
