import React from 'react';

interface SignupTitleProps {
	value: string;
}

function SignupTitle({ value }: SignupTitleProps) {
	return <span className="title">{value}</span>;
}

export default SignupTitle;
