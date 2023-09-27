import formatDate from 'utils/common/formatDate';
import ReceiptListType from 'types/receipt';
import { CustomIngredientItemType } from 'types/refrigerator';

export const DUMMY_INGREDIENTS_ITEM_INFO_LIST = [
	{
		title: '재료명',
		value: '토마토',
	},
	{
		title: '등록일',
		value: formatDate(new Date()),
	},
];

export const DUMMY_RECEIPT_LIST: ReceiptListType = {
	value: ['맥주', '광어회', '낙지탕탕이'],
	boundingPolys: [
		{
			vertices: [
				{
					x: 121.0,
					y: 126.0,
				},
				{
					x: 150.0,
					y: 126.0,
				},
				{
					x: 150.0,
					y: 143.0,
				},
				{
					x: 121.0,
					y: 143.0,
				},
			],
		},
		{
			vertices: [
				{
					x: 122.0,
					y: 143.0,
				},
				{
					x: 184.0,
					y: 143.0,
				},
				{
					x: 184.0,
					y: 160.0,
				},
				{
					x: 122.0,
					y: 160.0,
				},
			],
		},
		{
			vertices: [
				{
					x: 186.0,
					y: 144.0,
				},
				{
					x: 203.0,
					y: 144.0,
				},
				{
					x: 203.0,
					y: 161.0,
				},
				{
					x: 186.0,
					y: 161.0,
				},
			],
		},
	],
};

export const DUMMY_CUSTOM_INGREDIENT_LIST: CustomIngredientItemType[] = [
	{
		ingredientCategoryId: 1,
		ingredientName: '수박',
	},
	{
		ingredientCategoryId: 2,
		ingredientName: '사과',
	},
];

export default DUMMY_CUSTOM_INGREDIENT_LIST;
