import React, { ReactNode, createElement } from 'react';
import './FloatingMenuItem.scss';
import FloatingMenuList, { FloatingMenuListIcons } from 'constants/floatingMenuList';
import { IconBaseProps, IconType } from 'react-icons/lib';
import { useRecoilState } from 'recoil';
import tabbarState from 'recoil/atoms/tabbarState';

function renderIcon(icon: IconType, size: number): ReactNode {
	return createElement(icon, { size } as IconBaseProps); // 아이콘을 ReactNode으로 래핑합니다.
}

function FloatingMenuItem({
	item,
	onMenuSelect,
}: {
	item: keyof typeof FloatingMenuList;
	onMenuSelect: (item: string) => void;
}) {
	const [tabbarOn, setTabbarOn] = useRecoilState(tabbarState);

	return (
		<li className="floating-menu-item-container">
			<button
				type="button"
				onClick={() => {
					console.log(item);
					onMenuSelect(item);
					setTabbarOn(!tabbarOn);
				}}
			>
				{renderIcon(FloatingMenuListIcons[item], 30)}
				<div className="menu-title">{FloatingMenuList[item]}</div>
			</button>
		</li>
	);
}

export default FloatingMenuItem;
