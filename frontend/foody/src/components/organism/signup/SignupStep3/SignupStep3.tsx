import LargeButton from 'components/atom/LargeButton/LargeButton';
import SignupTitle from 'components/atom/SignupTitle/SignupTitle';
import UnderlineInput from 'components/atom/UnderlineInput/UnderlineInput';
import LargeButtonColor from 'constants/color';
import React from 'react';

interface SignupStep3Props {
	age: string;
	setAge: (age: string) => void;
	nextButton: () => void;
}

function SignupStep3({ age, setAge, nextButton }: SignupStep3Props) {
	const ageCheck = (value: string) => {
		const numbericRegex = /^[0-9]+$/;
		const isTrue = numbericRegex.test(value);

		if (isTrue === true || value === '') {
			return true;
		}

		return false;
	};

	return (
		<div className="step3-container">
			<SignupTitle value="나이를 알려주세요" />
			<UnderlineInput maxlength={3} isValid={ageCheck} onChangeValue={setAge} placeholder="나이" unit="" value={age} />
			<LargeButton
				buttonClick={nextButton}
				imgsrc=""
				value="다음"
				buttonColor={ageCheck(age) ? LargeButtonColor.Green : LargeButtonColor.Gray}
			/>
		</div>
	);
}

export default SignupStep3;
