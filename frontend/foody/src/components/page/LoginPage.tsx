import LoginTemplate from 'components/template/LoginTemplate/LoginTemplate';
import React, { useEffect, useState } from 'react';
import SocialLoginButton from 'components/atom/SocialLoginButton/SocialLoginButton';
import login from 'utils/api/auth';
import LocalStorage from 'constants/LocalStorage';
import { setCookie } from 'constants/Cookie';
import useMovePage from 'hooks/useMovePage';
import { useLocation } from 'react-router-dom';
import loginLogo from 'assets/icons/mainLogo.png';
import googleLogo from 'assets/icons/googleLogo.png';

function LoginPage() {
	const { movePage } = useMovePage();
	const location = useLocation();

	const [user, setUser] = useState<string | null>(null);

	useEffect(() => {
		const urlParams = new URLSearchParams(location.search);

		if (!user && location.search) {
			const accesstoken = urlParams.get('accessToken');
			const refreshToken = urlParams.get('refreshToken');
			const userd = urlParams.get('user');

			if (!accesstoken || !refreshToken || !userd) {
				throw new Error('No saved Token');
			}

			setUser(userd);
			LocalStorage.setItem('accesstoken', accesstoken);

			setCookie('refreshtoken', refreshToken);
		}

		if (user === '1') {
			movePage('/home', null);
		} else if (user === '0') {
			movePage('/signup', null);
		}
	}, [user, movePage, location.search]);

	const test2 = () => {
		login();
	};

	return (
		<LoginTemplate>
			<img src={loginLogo} alt="" />
			<SocialLoginButton value="구글 로그인" logo_src={googleLogo} onclick={test2} />
		</LoginTemplate>
	);
}

export default LoginPage;
