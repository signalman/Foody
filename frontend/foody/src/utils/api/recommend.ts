import { instance } from './instance';

export const getAllRecommendList = async () => {
	const requests = ['hybrid', 'preference', 'ingredients'].map((item) => {
		return instance.get(`/recommend/${item}`);
	});
	const response = await Promise.all(requests);
	return response;
};

export const getRecommendList = async (key: string) => {
	const response = instance.get(`/recommend/${key}`);
	return response;
};

export default getAllRecommendList;
