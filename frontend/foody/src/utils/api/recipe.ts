import { instance } from './instance';

export const getSearchRecipe = async (keyword: string) => {
	const response = await instance.get(`/recipe?keyword=${keyword}`);
	return response;
};

export const getRecipeDetail = async (id: number) => {
	const response = await instance.get(`/recipe/${id}`);
	return response;
};

export const setBookmark = async (id: number) => {
	const response = await instance.post(`/bookmark/${id}`);
	return response;
};

export const getBookmarkList = async () => {
	const response = await instance.get('/bookmark/mypage');
	return response;
};

export default getSearchRecipe;
