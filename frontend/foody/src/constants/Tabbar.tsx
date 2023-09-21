import { AiOutlineHome, AiFillHome } from 'react-icons/ai';
import { BiBarChartSquare, BiSolidBarChartSquare } from 'react-icons/bi';
import { IoReceiptOutline, IoReceiptSharp } from 'react-icons/io5';
import { IconType } from 'react-icons/lib';

export interface ITabbarItem {
	icon: IconType;
	activeIcon: IconType;
	menu: string;
	url: string;
}

const TABBER_MENUS: ITabbarItem[] = [
	{
		icon: AiOutlineHome,
		activeIcon: AiFillHome,
		menu: '홈',
		url: 'home',
	},
	{
		icon: BiBarChartSquare,
		activeIcon: BiSolidBarChartSquare,
		menu: '식단',
		url: 'meal',
	},
	{
		icon: BiBarChartSquare,
		activeIcon: BiSolidBarChartSquare,
		menu: '냉장고',
		url: 'refri',
	},
	{
		icon: IoReceiptOutline,
		activeIcon: IoReceiptSharp,
		menu: '식단 추천',
		url: 'recommend',
	},
];

export default TABBER_MENUS;
