import React from 'react';
import './FloatingMenu.scss';
import FloatingMenuList from 'constants/floatingMenuList';
import FloatingMenuItem from '../FloatingMenuItem/FloatingMenuItem';

function FloatingMenu({
	menuList,
	onMenuSelect,
}: {
	menuList: (keyof typeof FloatingMenuList)[];
	onMenuSelect: (menu: string) => void;
}) {
	return (
		<ul className="floating-menu-list">
			{menuList.map((item) => (
				<FloatingMenuItem key={item} item={item} onMenuSelect={onMenuSelect} />
			))}
		</ul>
	);
}

export default FloatingMenu;
