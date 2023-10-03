import { RegistSendData } from 'types/meal';
import { instance } from './instance';
import { instanceYolo } from './instanceYolo';
import { instanceMeal } from './instanceMeal';

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

export const getMealNutrient = async (data: string) => {
	const response = await instance.get(`food?name=${data}`);
	return response;
};

export const getSearchMeal = async (data: string) => {
	const response = await instance.get(`food/autocomplete?query=${data}`);
	console.log(response);
	return response;
};

export const mealCamera = async (data: File) => {
	const formData = new FormData();
	formData.append('image', data);
	const response = await instanceYolo.post('/detect', formData);
	console.log(response);
	return response;
};

export const postRegistMeal = async (data: RegistSendData, img: File | null, subimgs: File[] | null) => {
	const formData = new FormData();
	const json = JSON.stringify(data);
	const jsonBlob = new Blob([json], { type: 'application/json' });
	formData.append('mealPlanRequest', jsonBlob);
	if (img !== null) {
		formData.append('mealImage', img);
	}

	if (subimgs !== null) {
		subimgs.forEach((file) => {
			formData.append(`foodImages`, file, file.name); // 각 파일에 고유한 이름 지정
		});
	}
	const response = await instanceMeal.post('/mealplan/food', formData);
	return response;
};

export const postRegistMealText = async (data: RegistSendData) => {
	const formData = new FormData();
	const json = JSON.stringify(data);
	const jsonBlob = new Blob([json], { type: 'application/json' });
	formData.append('mealPlanRequest', jsonBlob);

	const response = await instanceMeal.post('/mealplan/food/text', formData);
	return response;
};
export default getDayofMeal;
