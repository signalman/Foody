import React from 'react';
import './SignupTitle.scss';

interface SignupTitleProps {
	value: string;
}

function SignupTitle({ value }: SignupTitleProps) {
	return <h2 className="title">{value}</h2>;
}

export default SignupTitle;
