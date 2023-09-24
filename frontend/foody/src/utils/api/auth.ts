const login = () => {
	const redirectUrl = 'https://j9c106.p.ssafy.io:8082/oauth2/authorization/google';
	window.location.href = redirectUrl;
};

export default login;
