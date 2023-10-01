import { instance } from './instance';
import { instanceMultiPart } from './instanceMultiPart';

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
	return response;
};

export const getUserMealInfo = async () => {
	const response = await instance.get('/nutrient/alltype');
	console.log(response);
	return response;
};

export const mealCamera = async (data: File) => {
	const formData = new FormData();
	formData.append('image', data);
	console.log(formData);
	const response = await instanceMultiPart.post('/detect/', formData);
	console.log(response);
	return response;
};
export default getDayofMeal;
