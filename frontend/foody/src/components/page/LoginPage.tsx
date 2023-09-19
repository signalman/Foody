import LoginTemplate from 'components/template/LoginTemplate/LoginTemplate';
import React, { memo } from 'react';
import SocialLoginButton from 'components/atom/SocialLoginButton/SocialLoginButton';
import loginLogo from '../../assets/icons/mainLogo.png';
import googleLogo from '../../assets/icons/googleLogo.png';

const LoginPage = memo(() => {
	return (
		<LoginTemplate>
			<img src={loginLogo} alt="" />
			<SocialLoginButton value="구글 로그인 하기" logo_src={googleLogo} />
		</LoginTemplate>
	);
});

export default LoginPage;
