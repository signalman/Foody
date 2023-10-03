import LargeButton from 'components/atom/LargeButton/LargeButton';
import SignupTitle from 'components/atom/SignupTitle/SignupTitle';
import UnderlineInput from 'components/atom/UnderlineInput/UnderlineInput';
import LargeButtonColor from 'constants/color';
import React from 'react';

interface SignupStep4Props {
	height: string;
	weight: string;
	setHeight: (weight: string) => void;
	setWeight: (height: string) => void;
	nextButton: () => void;
}

function SignupStep4({ height, weight, setHeight, setWeight, nextButton }: SignupStep4Props) {
	const heightCheck = (value: string) => {
		const numbericRegex = /^[0-9]+$/;
		const isNumber = numbericRegex.test(value);
		let isTrue1 = false;

		if (isNumber === true || value === '') {
			isTrue1 = true;
		}

		return isTrue1;
	};

	const weightCheck = (value: string) => {
		const numbericRegex = /^[0-9]+$/;
		const isNumber = numbericRegex.test(value);
		let isTrue1 = false;

		if (isNumber === true || value === '') {
			isTrue1 = true;
		}

		return isTrue1;
	};

	const handleNextStep = () => {
		const intHeight = parseInt(height, 10);
		const intWeight = parseInt(weight, 10);

		let isTrue1 = false;
		let isTrue2 = false;

		if (intHeight >= 80 && intHeight <= 200) {
			isTrue1 = true;
		}

		if (intWeight >= 80 && intWeight <= 200) {
			isTrue2 = true;
		}

		if (isTrue1 === true && isTrue2 === true) {
			return true;
		}

		return false;
	};

	return (
		<div className="step3-container">
			<SignupTitle value="키와 체중을 알려주세요" />
			<UnderlineInput
				maxlength={3}
				isValid={heightCheck}
				onChangeValue={setHeight}
				placeholder="키"
				unit=""
				value={height}
			/>
			{/* {!heightCheck && height.length > 0 && <p>키는 100이상 200이하로 입력해주세요.</p>} */}
			<UnderlineInput
				maxlength={3}
				isValid={weightCheck}
				onChangeValue={setWeight}
				placeholder="체중"
				unit=""
				value={weight}
			/>
			{/* {!weightCheck && weight.length > 0 && <p>몸무게는 40이상 200이하로 입력해주세요.</p>} */}
			<LargeButton
				buttonClick={handleNextStep() ? nextButton : () => {}}
				imgsrc=""
				value="확인"
				buttonColor={handleNextStep() ? LargeButtonColor.Green : LargeButtonColor.Gray}
			/>
		</div>
	);
}

export default SignupStep4;
