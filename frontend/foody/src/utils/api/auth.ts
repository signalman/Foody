const login = () => {
	// const redirectUrl = process.env.REACT_APP_OAUTH_SERVER_URL || '/';
	const redirectUrl = process.env.REACT_APP_OAUTH_DEVELOP_URL || '/';
	window.location.href = redirectUrl;
};

export default login;
