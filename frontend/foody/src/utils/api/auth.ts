import { SignupInformationParams } from 'types/user';
import { instance } from './instance';

const login = () => {
	// const redirectUrl = process.env.REACT_APP_OAUTH_DEVELOPE_URL || '/';
	const redirectUrl = process.env.REACT_APP_OAUTH_SERVER_URL || '/';
	// const redirectUrl = 'https://j9c106.p.ssafy.io/oauth2/authorization/google';
	// console.log(redirectUrl);
	window.location.href = redirectUrl;
};

export const getSignupInformation = async (data: SignupInformationParams) => {
	const body = {
		nickname: data.nickname,
		height: data.height,
		weight: data.weight,
		gender: data.gender,
		age: data.age,
		activityLevel: data.activityLevel,
	};

	const response = await instance.post('/member/join', body);
	return response;
};

export const getMBTIImage = async () => {
	const response = await instance.get('/mbti/');
	console.log(response);
	return response;
};

export const resultMBTI = async (data: number[]) => {
	const body = {
		results: data,
	};
	const response = await instance.post('/mbti/create', body);
	return response;
};
export default login;
