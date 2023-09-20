import LargeButton from 'components/atom/LargeButton/LargeButton';
import SignupTitle from 'components/atom/SignupTitle/SignupTitle';
import UnderlineInput from 'components/atom/UnderlineInput/UnderlineInput';
import LargeButtonColor from 'constants/color';
import React, { useEffect, useState } from 'react';

interface SignupStep1Props {
	value: string;
	setValue: (value: string) => void;
}

function SignupStep1({ value, setValue }: SignupStep1Props) {
	const [check, setCheck] = useState(false);

	useEffect(() => {
		if (value.length > 0) {
			setCheck(true);
		} else {
			setCheck(false);
		}
	}, [value]);

	return (
		<div className="step1-container">
			<SignupTitle value="닉네임을 알려주세요" />
			<UnderlineInput onChangeValue={setValue} placeholder="닉네임" unit="" value={value} />
			<LargeButton imgsrc="" value="확인" buttonColor={check ? LargeButtonColor.Green : LargeButtonColor.Gray} />
		</div>
	);
}

export default SignupStep1;
