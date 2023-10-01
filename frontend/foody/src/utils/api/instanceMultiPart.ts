import axios, { AxiosError, AxiosInstance } from 'axios';
import LocalStorage from 'constants/LocalStorage';
import toast from 'react-hot-toast';

export const instanceMultiPart: AxiosInstance = axios.create({
	// baseURL: process.env.REACT_APP_DEVELOP_BASE_URL,
	baseURL: process.env.REACT_APP_SERVER_BASE_URL,
	headers: {
		'Content-Type': 'multipart/form-data',
	},
});

// Axios 요청시 인터셉트
instanceMultiPart.interceptors.request.use((req) => {
	const accessToken = LocalStorage.getItem('accesstoken');
	if (accessToken) {
		req.headers.authorization = `Bearer ${accessToken}`;
	}

	return req;
});

// Axios 응답시 인터셉트
instanceMultiPart.interceptors.response.use(
	(response) => response,
	async (error: AxiosError) => {
		// if (error.response?.status === 401) {
		if (error.response?.status === 500) {
			toast.error('세션이 만료되었습니다. 다시 로그인 해주세요.');
			// JWT 만료 오류 처리
			const refreshToken = LocalStorage.getItem('refreshtoken');
			if (refreshToken) {
				try {
					// Refresh Token을 사용하여 새로운 Access Token을 요청
					const refreshResponse = await axios.post('/api/refresh-token', { refreshToken });
					if (refreshResponse.status === 200) {
						// 새로운 Access Token을 받았을 경우, 저장하고 이전 요청을 재시도
						const newAccessToken = refreshResponse.data.access_token;
						LocalStorage.setItem('accesstoken', newAccessToken);

						// 재시도하기 위해 이전 요청을 복제
						const originalRequest = error.config;
						if (originalRequest !== undefined) {
							originalRequest.headers.authorization = `Bearer ${newAccessToken}`;
							return await axios(originalRequest);
						}
					}
				} catch (refreshError) {
					console.error('Refresh Token을 사용하여 새로운 Access Token을 가져올 수 없습니다.', refreshError);
				}
			}

			// Refresh Token이 없거나 요청이 실패한 경우 로그인 페이지로 리다이렉트
			window.location.href = '/login';
			LocalStorage.removeItem('accesstoken');
		}

		return Promise.reject(error);
	},
);

export default instanceMultiPart;
