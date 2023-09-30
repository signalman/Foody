import { instance } from './instance';

const getDayofMeal = async (date: string) => {
	const response = await instance.get(`/mealplan?date=${date}`);
	return response;
};

export const recentMeal = async () => {
	const response = await instance.get('mealplan/recent');
	return response;
};

export const getDaoyofNutrient = async () => {
	const response = await instance.get('/nutrient/');
	console.log(response);
	return response;
};
export default getDayofMeal;
