import LoginTemplate from 'components/template/LoginTemplate/LoginTemplate';
import React, { memo } from 'react';
import SocialLoginButton from 'components/atom/SocialLoginButton/SocialLoginButton';
import login from 'utils/api/auth';
import loginLogo from '../../assets/icons/mainLogo.png';
import googleLogo from '../../assets/icons/googleLogo.png';

const LoginPage = memo(() => {
	const test2 = () => {
		// const redirectUrl = 'http://localhost/oauth2/authorization/google';
		// window.location.href = redirectUrl;
		login();
	};

	return (
		<LoginTemplate>
			<img src={loginLogo} alt="" />
			<SocialLoginButton value="구글 로그인 test2" logo_src={googleLogo} onclick={test2} />
		</LoginTemplate>
	);
});

export default LoginPage;
