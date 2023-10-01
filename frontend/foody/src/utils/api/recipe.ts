import { instance } from './instance';

export const getSearchRecipe = async (keyword: string) => {
	const response = await instance.get(`/recipe?keyword=${keyword}`);
	console.log('response', response);
	return response;
};

export default getSearchRecipe;
