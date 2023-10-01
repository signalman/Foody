import { instance } from './instance';

export const getRecommendList = async (keyword: string) => {
	const response = await instance.get(`/recommend?keyword=${keyword}`);
	console.log('response', response);
	return response;
};

export default getRecommendList;
