import LoginTemplate from 'components/template/LoginTemplate/LoginTemplate';
import React, { memo, useCallback, useEffect } from 'react';
import SocialLoginButton from 'components/atom/SocialLoginButton/SocialLoginButton';
import login from 'utils/api/auth';
import LocalStorage from 'constants/LocalStorage';
import { setCookie } from 'constants/Cookie';
import useMovePage from 'hooks/useMovePage';
import loginLogo from '../../assets/icons/mainLogo.png';
import googleLogo from '../../assets/icons/googleLogo.png';

const LoginPage = memo(() => {
	const { movePage } = useMovePage();

	const fetchRedirectLocation = useCallback(() => {
		fetch('/login/oauth2/code/google', {
			redirect: 'manual',
		}).then((response) => {
			if (response.status === 200) {
				const urlParams = new URLSearchParams(window.location.search);
				const accessToken = urlParams.get('accessToken');
				const refreshToken = urlParams.get('refreshToken');
				const user = urlParams.get('user');

				if (!accessToken || !refreshToken || !user) {
					throw new Error('No saved Token');
				}

				LocalStorage.setItem('accesstoken', accessToken);
				setCookie('refreshtoken', refreshToken);

				if (user === '1') {
					movePage('/home', null);
				} else if (user === '0') {
					movePage('/signup', null);
				}
			}
		});
	}, [movePage]);

	const test2 = () => {
		login();
	};

	useEffect(() => {
		const test = window.location.search;

		if (test !== '') {
			fetchRedirectLocation();
		}
	}, [fetchRedirectLocation]);

	return (
		<LoginTemplate>
			<img src={loginLogo} alt="" />
			<SocialLoginButton value="구글 로그인 test2" logo_src={googleLogo} onclick={test2} />
		</LoginTemplate>
	);
});

export default LoginPage;
