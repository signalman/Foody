import LargeButton from 'components/atom/LargeButton/LargeButton';
import SignupTitle from 'components/atom/SignupTitle/SignupTitle';
import UnderlineInput from 'components/atom/UnderlineInput/UnderlineInput';
import LargeButtonColor from 'constants/color';
import React, { useEffect, useState } from 'react';

interface SignupStep3Props {
	age: string;
	setAge: (age: string) => void;
	nextButton: () => void;
}

function SignupStep3({ age, setAge, nextButton }: SignupStep3Props) {
	const [check, setCheck] = useState(false);

	useEffect(() => {
		if (age.length > 0) {
			setCheck(true);
		} else {
			setCheck(false);
		}
	}, [age]);

	return (
		<div className="step3-container">
			<SignupTitle value="나이를 알려주세요" />
			<UnderlineInput onChangeValue={setAge} placeholder="나이" unit="" value={age} />
			<LargeButton
				buttonClick={nextButton}
				imgsrc=""
				value="다음"
				buttonColor={check ? LargeButtonColor.Green : LargeButtonColor.Gray}
			/>
		</div>
	);
}

export default SignupStep3;
