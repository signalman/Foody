import LargeButton from 'components/atom/LargeButton/LargeButton';
import SignupTitle from 'components/atom/SignupTitle/SignupTitle';
import LargeButtonColor from 'constants/color';
import React, { useState } from 'react';

interface SignupStep5Props {
	move: number;
	setMove: (move: number) => void;
	nextButton: () => void;
}

function SignupStep5({ move, setMove, nextButton }: SignupStep5Props) {
	const [buttonData, setButtonData] = useState<number>(move);

	const handleHardmove = () => {
		setButtonData(1);
	};

	const handleNormalmove = () => {
		setButtonData(2);
	};

	const handleSoftmove = () => {
		setButtonData(3);
	};

	const handleButton = () => {
		nextButton();
		setMove(buttonData);
	};

	return (
		<div className="step5-container">
			<SignupTitle value="하루 활동량을 알려주세요" />
			<LargeButton
				imgsrc=""
				buttonClick={handleHardmove}
				value="많이 움직여요"
				buttonColor={buttonData === 1 ? LargeButtonColor.Green : LargeButtonColor.Gray}
			/>
			<LargeButton
				imgsrc=""
				buttonClick={handleNormalmove}
				value="적당히 움직여요"
				buttonColor={buttonData === 2 ? LargeButtonColor.Green : LargeButtonColor.Gray}
			/>
			<LargeButton
				imgsrc=""
				buttonClick={handleSoftmove}
				value="잘 움직이지 않아요"
				buttonColor={buttonData === 3 ? LargeButtonColor.Green : LargeButtonColor.Gray}
			/>
			<LargeButton
				buttonClick={handleButton}
				imgsrc=""
				value="다음"
				buttonColor={buttonData ? LargeButtonColor.Green : LargeButtonColor.Gray}
			/>
		</div>
	);
}

export default SignupStep5;
