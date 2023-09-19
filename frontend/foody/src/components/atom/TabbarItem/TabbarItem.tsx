import React, { useEffect, useState } from 'react';
import './TabbarItem.scss';
import { ITabbarItem } from 'constants/Tabbar';
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
				<span>{isActive ? <menu.activeIcon size={24} /> : <menu.icon size={24} />}</span>
				<span>{menu.menu}</span>
			</Link>
		</li>
	);
}

export default TabbarItem;
