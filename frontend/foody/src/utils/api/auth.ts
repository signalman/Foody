const login = () => {
	// const redirectUrl = process.env.REACT_APP_OAUTH_SERVER_URL || '/';
	// const redirectUrl = process.env.REACT_APP_OAUTH_DEVELOPE_URL || '/';
	const redirectUrl = 'https://j9c106.p.ssafy.io/oauth2/authorization/google';
	window.location.href = redirectUrl;
};

export default login;
