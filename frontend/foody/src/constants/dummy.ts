import testImg1 from 'assets/icons/Apple.svg';
import formatDate from 'utils/common/formatDate';
import ReceiptListType from 'types/receipt';
import { IngridientItem } from './category';

const DUMMY_INGREDIENTS_LIST: IngridientItem[] = [
	{
		key: 0,
		img: testImg1,
		text: '사과',
		regiDate: new Date(),
	},
	{
		key: 1,
		img: testImg1,
		text: '토마토',
		regiDate: new Date(),
	},
	{
		key: 2,
		img: testImg1,
		text: '노루궁댕이버섯',
		regiDate: new Date(),
	},
	{
		key: 3,
		img: testImg1,
		text: '사과',
		regiDate: new Date(),
	},
	{
		key: 4,
		img: testImg1,
		text: '토마토',
		regiDate: new Date(),
	},
	{
		key: 5,
		img: testImg1,
		text: '노루궁댕이버섯',
		regiDate: new Date(),
	},
	{
		key: 6,
		img: testImg1,
		text: '사과',
		regiDate: new Date(),
	},
	{
		key: 7,
		img: testImg1,
		text: '토마토',
		regiDate: new Date(),
	},
	{
		key: 8,
		img: testImg1,
		text: '노루궁댕이버섯',
		regiDate: new Date(),
	},
	{
		key: 9,
		img: testImg1,
		text: '사과',
		regiDate: new Date(),
	},
	{
		key: 10,
		img: testImg1,
		text: '토마토',
		regiDate: new Date(),
	},
	{
		key: 11,
		img: testImg1,
		text: '노루궁댕이버섯',
		regiDate: new Date(),
	},
	{
		key: 12,
		img: testImg1,
		text: '사과',
		regiDate: new Date(),
	},
	{
		key: 13,
		img: testImg1,
		text: '토마토',
		regiDate: new Date(),
	},
	{
		key: 14,
		img: testImg1,
		text: '노루궁댕이버섯',
		regiDate: new Date(),
	},
];

export const DUMMY_INGREDIENTS_LIST2: IngridientItem[] = [
	{
		key: 0,
		img: testImg1,
		text: '사과',
		regiDate: new Date(),
	},
	{
		key: 1,
		img: testImg1,
		text: '토마토',
		regiDate: new Date(),
	},
	{
		key: 2,
		img: testImg1,
		text: '노루궁댕이버섯',
		regiDate: new Date(),
	},
	{
		key: 3,
		img: testImg1,
		text: '사과',
		regiDate: new Date(),
	},
	{
		key: 4,
		img: testImg1,
		text: '토마토',
		regiDate: new Date(),
	},
	{
		key: 5,
		img: testImg1,
		text: '노루궁댕이버섯',
		regiDate: new Date(),
	},
];

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
	value: ['맥주', 'asd', 'asf'],
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

export default DUMMY_INGREDIENTS_LIST;
