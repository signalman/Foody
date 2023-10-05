import axios, { AxiosError, AxiosInstance } from 'axios';
import LocalStorage from 'constants/LocalStorage';

export const instanceMeal: AxiosInstance = axios.create({
	// baseURL: process.env.REACT_APP_DEVELOP_BASE_URL,
	baseURL: process.env.REACT_APP_SERVER_BASE_URL,
	headers: {
		'Content-Type': 'multipart/form-data',
	},
});

// Axios 요청시 인터셉트
instanceMeal.interceptors.request.use((req) => {
	const accessToken = LocalStorage.getItem('accesstoken');
	if (accessToken) {
		req.headers.authorization = `Bearer ${accessToken}`;
	}

	return req;
});

// Axios 응답시 인터셉트
instanceMeal.interceptors.response.use(
	(response) => response,
	async (error: AxiosError) => {
		if (error.response?.status === 500) {
			console.error('식단 오류입니다.');
			try {
				// 재시도하기 위해 이전 요청을 복제
				const originalRequest = error.config;
				if (originalRequest !== undefined) {
					return await axios(originalRequest);
				}
			} catch (refreshError) {
				console.error('Refresh Token을 사용하여 새로운 Access Token을 가져올 수 없습니다.', refreshError);
			}
		}

		// 요청이 실패한 경우 리다이렉트
		window.location.href = '/';

		return Promise.reject(error);
	},
);

export default instanceMeal;
