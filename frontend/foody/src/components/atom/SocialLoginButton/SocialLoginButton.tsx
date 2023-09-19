import React, { ButtonHTMLAttributes } from 'react';
import './SocialLoginButton.scss';

interface SocialBtnProps extends ButtonHTMLAttributes<HTMLButtonElement> {
	value: string;
}

interface ImgProps {
	logo_src: string;
}

function SocialLoginButton({ value, logo_src }: SocialBtnProps & ImgProps) {
	return (
		<button type="button" className="social-value">
			<img className="img-size" src={logo_src} alt="" /> {value}
		</button>
	);
}

export default SocialLoginButton;
