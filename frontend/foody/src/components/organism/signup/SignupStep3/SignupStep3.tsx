import LargeButton from 'components/atom/LargeButton/LargeButton';
import SignupTitle from 'components/atom/SignupTitle/SignupTitle';
import UnderlineInput from 'components/atom/UnderlineInput/UnderlineInput';
import BottomButtonLayout from 'components/template/BottomButtonLayout/BottomButtonLayout';
import SignupInfoTemplate from 'components/template/SignupInfoTemplate/SignupInfoTemplate';
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
		<SignupInfoTemplate>
			<SignupTitle value="나이를 알려주세요" />
			<UnderlineInput maxlength={3} isValid={ageCheck} onChangeValue={setAge} placeholder="나이" unit="" value={age} />
			<BottomButtonLayout>
				<LargeButton
					buttonClick={age.length ? nextButton : () => {}}
					imgsrc=""
					value="다음"
					buttonColor={age.length ? LargeButtonColor.Green : LargeButtonColor.Gray}
				/>
			</BottomButtonLayout>
		</SignupInfoTemplate>
	);
}

export default SignupStep3;
