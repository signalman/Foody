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
	ingredientCategoryId?: number;
	text: string;
	img: string;
}

export interface IngredientItem {
	key: number;
	text: string;
	img: string;
	regiDate: string;
	categoryType: number;
	ingredientCategoryId: number;
	ingredientId: number;
}

export interface IngredientsList {
	categoryType: number;
	createDate: string;
	iconImg: string;
	ingredientCategoryId: number;
	ingredientId: number;
	ingredientName: string;
}

const REFI_CATEGORY_LIST: RefricategoryItem[] = [
	{
		ingredientCategoryId: 0,
		text: '모든 재료',
		img: category1,
	},
	{
		ingredientCategoryId: 1,
		text: '과일/채소',
		img: category2,
	},
	{
		ingredientCategoryId: 2,
		text: '축산물',
		img: category3,
	},
	{
		ingredientCategoryId: 3,
		text: '수산물/건어물',
		img: category5,
	},
	{
		ingredientCategoryId: 4,
		text: '음료',
		img: category4,
	},
	{
		ingredientCategoryId: 5,
		text: '간편조리식품',
		img: category7,
	},
	{
		ingredientCategoryId: 6,
		text: '치즈/유가공품',
		img: category8,
	},
	{
		ingredientCategoryId: 7,
		text: '반찬',
		img: category6,
	},
];

export const DRAWER_CATEGORY_LIST = [
	{
		ingredientCategoryId: 0,
		text: '모든 재료',
		img: category1,
	},
	{
		ingredientCategoryId: 8,
		text: '쌀/잡곡',
		img: category9,
	},
	{
		ingredientCategoryId: 9,
		text: '건강식품',
		img: category10,
	},
	{
		ingredientCategoryId: 10,
		text: '면/통조림',
		img: category11,
	},
	{
		ingredientCategoryId: 11,
		text: '과자/떡/베이커리',
		img: category12,
	},
	{
		ingredientCategoryId: 12,
		text: '소스/조미료',
		img: category13,
	},
];

interface CategoryKeyValueType {
	[key: string]: number;
}

export const CATEGORY_KEY_VALUE: CategoryKeyValueType = {
	'모든 재료': 0,
	'과일/채소': 1,
	축산물: 2,
	'수산물/건어물': 3,
	음료: 4,
	간편조리식품: 5,
	'치즈/유가공품': 6,
	반찬: 7,
	'쌀/잡곡': 8,
	건강식품: 9,
	'면/통조림': 10,
	'과자/떡/베이커리': 11,
	'소스/조미료': 12,
};

export default REFI_CATEGORY_LIST;
