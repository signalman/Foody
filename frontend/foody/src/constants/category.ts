import category1 from 'assets/images/category/1.png';
import category2 from 'assets/images/category/2.png';
import category3 from 'assets/images/category/3.png';
import category4 from 'assets/images/category/4.png';
import category5 from 'assets/images/category/5.png';
import category6 from 'assets/images/category/6.png';
import category7 from 'assets/images/category/7.png';
import category8 from 'assets/images/category/8.png';
import category9 from 'assets/images/category/9.png';
import category10 from 'assets/images/category/10.png';
import category11 from 'assets/images/category/11.png';
import category12 from 'assets/images/category/12.png';
import category13 from 'assets/images/category/13.png';

export interface RefriCategoryList {
	categoryList: RefricategoryItem[];
}

export interface RefricategoryItem {
	text: string;
	img: string;
}

export interface IngridientItem {
	key: number;
	text: string;
	img: string;
	regiDate: Date;
}

const REFI_CATEGORY_LIST: RefricategoryItem[] = [
	{
		text: '모든 재료',
		img: category1,
	},
	{
		text: '과일/채소',
		img: category2,
	},
	{
		text: '축산물',
		img: category3,
	},
	{
		text: '음료',
		img: category4,
	},
	{
		text: '수산물/건어물',
		img: category5,
	},
	{
		text: '반찬',
		img: category6,
	},
	{
		text: '간편조리식품',
		img: category7,
	},
	{
		text: '치즈/유가공품',
		img: category8,
	},
];

export const DRAWER_CATEGORY_LIST = [
	{
		text: '모든 재료',
		img: category1,
	},
	{
		text: '쌀/잡곡',
		img: category9,
	},
	{
		text: '건강식품',
		img: category10,
	},
	{
		text: '면/통조림',
		img: category11,
	},
	{
		text: '과자/떡/베이커리',
		img: category12,
	},
	{
		text: '소스/조미료',
		img: category13,
	},
];

export default REFI_CATEGORY_LIST;
