import React, { ButtonHTMLAttributes } from 'react';
import './SocialLoginButton.scss';

interface SocialBtnProps extends ButtonHTMLAttributes<HTMLButtonElement> {
	value: string;
	onclick: () => void;
}

interface ImgProps {
	logo_src: string;
}

function SocialLoginButton({ value, logo_src, onclick }: SocialBtnProps & ImgProps) {
	return (
		<button type="button" className="social-value" onClick={onclick}>
			<img className="img-size" src={logo_src} alt="" /> {value}
		</button>
	);
}

export default SocialLoginButton;
