import ProgressBar from 'components/molecule/ProgressBar/ProgressBar';
import SignupStep1 from 'components/organism/signup/SignupStep1/SignupStep1';
import React, { useState } from 'react';

function SignupPage() {
	const [value, setValue] = useState('');
	const check = [true, true, false, false, false];

	return (
		<div>
			<ProgressBar ProgressCheck={check} />
			<SignupStep1 setValue={setValue} value={value} />
		</div>
	);
}

export default SignupPage;
