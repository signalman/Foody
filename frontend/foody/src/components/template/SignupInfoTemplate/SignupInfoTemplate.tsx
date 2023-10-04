import React, { ReactNode } from 'react';
import './SignupInfoTemplate.scss';

function SignupInfoTemplate({ children }: { children: ReactNode[] }) {
	return (
		<div>
			<div className="signup-title">{children[0]}</div>
			<div className="input-wrap">{children[1]}</div>
			<div className="input-wrap">{children[2]}</div>
			<div className="input-wrap">{children[3]}</div>
			<div>{children[4]}</div>
			<div>{children[5]}</div>
			<div>{children[6]}</div>
			<div>{children[7]}</div>
		</div>
	);
}

export default SignupInfoTemplate;
