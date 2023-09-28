import { CustomIngredientItemType } from 'types/refrigerator';
import { instance } from './instance';

export const getAllIngredientList = async () => {
	const response = await instance.get('/refrigerators');
	return response;
};

export const getSearchIngredients = async (keyword: string) => {
	const response = await instance.get(`/refrigerators/ingredient?keyword=${keyword}`);
	console.log('response', response);
	return response;
};

export const getOCRReceiptIngredients = async (uuid: string, data: string, format = 'jpeg') => {
	const body = {
		images: [
			{
				format,
				name: 'ingredient receipt',
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

export const getReceiptIngredients = async () => {
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

export const deleteIngredient = async (ingredient: number) => {
	const response = await instance.delete(`/refrigerators/${ingredient}`);
	return response;
};

export default getSearchIngredients;
