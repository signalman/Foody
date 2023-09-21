import LargeButton from 'components/atom/LargeButton/LargeButton';
import SignupTitle from 'components/atom/SignupTitle/SignupTitle';
import UnderlineInput from 'components/atom/UnderlineInput/UnderlineInput';
import LargeButtonColor from 'constants/color';
import React, { useEffect, useState } from 'react';

interface SignupStep4Props {
	height: string;
	weight: string;
	setHeight: (weight: string) => void;
	setWeight: (height: string) => void;
	nextButton: () => void;
}

function SignupStep4({ height, weight, setHeight, setWeight, nextButton }: SignupStep4Props) {
	const [check, setCheck] = useState(false);

	useEffect(() => {
		if (height.length > 0 && weight.length > 0) {
			setCheck(true);
		} else {
			setCheck(false);
		}
	}, [height, weight]);

	return (
		<div className="step3-container">
			<SignupTitle value="키와 체중을 알려주세요" />
			<UnderlineInput onChangeValue={setHeight} placeholder="키" unit="" value={height} />
			<UnderlineInput onChangeValue={setWeight} placeholder="체중" unit="" value={weight} />
			<LargeButton
				buttonClick={nextButton}
				imgsrc=""
				value="확인"
				buttonColor={check ? LargeButtonColor.Green : LargeButtonColor.Gray}
			/>
		</div>
	);
}

export default SignupStep4;
