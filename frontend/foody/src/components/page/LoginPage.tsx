import LoginTemplate from 'components/template/LoginTemplate/LoginTemplate';
import React, { memo } from 'react';
import SocialLoginButton from 'components/atom/SocialLoginButton/SocialLoginButton';
import loginLogo from '../../assets/icons/mainLogo.png';
import googleLogo from '../../assets/icons/googleLogo.png';

const LoginPage = memo(() => {
	const test1 = () => {
		const redirectUrl = 'http://localhost:8082/api/oauth2/authorization/google';
		window.location.href = redirectUrl;
	};

	const test2 = () => {
		const redirectUrl = 'http://localhost:8082/api/login/oauth2/authorization/google';
		window.location.href = redirectUrl;
	};

	const test3 = () => {
		const redirectUrl = 'http://localhost:8082/oauth2/authorization/google';
		window.location.href = redirectUrl;
	};

	const test4 = () => {
		const redirectUrl = 'http://localhost:8082/localhost/login/oauth2/authorization/google';
		window.location.href = redirectUrl;
	};

	return (
		<LoginTemplate>
			<img src={loginLogo} alt="" />
			<SocialLoginButton value="구글 로그인 test1" logo_src={googleLogo} onclick={test1} />
			<SocialLoginButton value="구글 로그인 test2" logo_src={googleLogo} onclick={test2} />
			<SocialLoginButton value="구글 로그인 test3" logo_src={googleLogo} onclick={test3} />
			<SocialLoginButton value="구글 로그인 test4" logo_src={googleLogo} onclick={test4} />
		</LoginTemplate>
	);
});

export default LoginPage;
