const login = () => {
	fetch('oauth2/authorization/google', {
		redirect: 'manual',
	}).then((response) => {
		// // URL에서 쿼리 파라미터 파싱
		// const urlParams = new URLSearchParams(new URL(response.url).search);

		// const accessToken = urlParams.get('accessToken');
		// const refreshToken = urlParams.get('refreshToken');
		// // 토큰을 사용하거나 저장하는 로직
		console.log(response);
		const urlParams = new URLSearchParams(window.location.search);
		const accessToken = urlParams.get('accessToken');
		const refreshToken = urlParams.get('refreshToken');
		console.log(accessToken);
		console.log(refreshToken);
	});
};

export default login;
