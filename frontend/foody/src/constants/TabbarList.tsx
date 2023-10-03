import { ReactComponent as Home } from 'assets/icons/home.svg';
import { ReactComponent as HomeActive } from 'assets/icons/homeActive.svg';
import { ReactComponent as Receipt } from 'assets/icons/receipt.svg';
import { ReactComponent as ReceiptActive } from 'assets/icons/receiptActive.svg';
import { ReactComponent as Meal } from 'assets/icons/meal.svg';
import { ReactComponent as MealActive } from 'assets/icons/mealActive.svg';
import { ReactComponent as Refrigerator } from 'assets/icons/refrigerator.svg';
import { ReactComponent as RefrigeratorActive } from 'assets/icons/refrigeratorActive.svg';
import { FC, SVGProps } from 'react';

export interface ITabbarItem {
	icon: FC<SVGProps<SVGSVGElement>>;
	activeIcon: FC<SVGProps<SVGSVGElement>>;
	menu: string;
	url: string;
}

const TABBER_MENUS: ITabbarItem[] = [
	{
		icon: Home,
		activeIcon: HomeActive,
		menu: '홈',
		url: 'home',
	},
	{
		icon: Meal,
		activeIcon: MealActive,
		menu: '식단',
		url: 'meal',
	},
	{
		icon: Refrigerator,
		activeIcon: RefrigeratorActive,
		menu: '냉장고',
		url: 'refri',
	},
	{
		icon: Receipt,
		activeIcon: ReceiptActive,
		menu: '레시피',
		url: 'recommend',
	},
];

export default TABBER_MENUS;
