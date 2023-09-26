import { CustomIngredientItemType } from 'types/refrigerator';
import { instance } from './instance';

export const getAllIngredientList = async () => {
	const response = await instance.get('/refrigerators');
	return response;
};

export const getSearchIndegredients = async (keyword: string) => {
	const response = await instance.get(`/refrigerators/ingredient?keyword=${keyword}`);
	console.log('response', response);
	return response;
};

export const getOCRReceiptIndegredients = async (uuid: string, data: string) => {
	const body = {
		images: [
			{
				format: 'jpeg',
				name: 'indegredients',
				data,
			},
		],
		requestId: uuid,
		version: 'V2',
		timestamp: Date.now(),
	};
	console.log('body', body);
	const response = await instance.post('/refrigerators/receipt', body);
	return response;
};

export const getReceiptIndegredients = async () => {
	const response = await instance.post('/refrigerators');
	return response;
};

export const createIngredientList = async (ingredients: number[], customIngredients: CustomIngredientItemType[]) => {
	const body = {
		ingredients,
		customIngredients,
	};
	console.log('body', body);
	const response = await instance.post('/refrigerators', body);
	return response;
};

export default getSearchIndegredients;
