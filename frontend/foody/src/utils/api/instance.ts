import axios, { AxiosError, AxiosInstance } from 'axios';
import LocalStorage from 'constants/LocalStorage';
// import SessionStorage from 'constants/storage/SessionStorage';

export const instance: AxiosInstance = axios.create({
	baseURL: process.env.REACT_APP_SERVER_BASE_URL,
	headers: {
		'Content-Type': 'application/json',
	},
});

// Axios 요청시 인터셉트
instance.interceptors.request.use((req) => {
	const accessToken = LocalStorage.getItem('accessToken');
	if (accessToken) {
		req.headers.authorization = `Bearer ${accessToken}`;
	}

	return req;
});

// Axios 응답시 인터셉트
instance.interceptors.response.use(
	(response) => response,
	(error: AxiosError) => {
		if (error.response?.status === 401) {
			// 401 에러 처리 로직
			window.location.href = '/auth';
			LocalStorage.removeItem('accessToken');
			// SessionStorage.initUser();
		}
		return Promise.reject(error);
	},
);

export const openviduInstance: AxiosInstance = axios.create({
	baseURL: process.env.REACT_APP_SERVER_BASE_URL,
	headers: {
		'Content-Type': 'application/json',
	},
});
