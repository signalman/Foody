const login = () => {
	const redirectUrl = 'http://localhost/oauth2/authorization/google';
	window.location.href = redirectUrl;
};

export default login;
