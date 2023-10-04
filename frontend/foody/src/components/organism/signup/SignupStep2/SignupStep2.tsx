import React, { useState, Dispatch, SetStateAction, useEffect } from 'react';
import './SignupStep2.scss';
import Boy from 'assets/images/Boy.png';
import Girl from 'assets/images/Girl.png';
import SignupGenderItem from 'components/atom/SignupGenderItem/SignupGenderItem';
import LargeButtonColor from 'constants/color';
import SignupTitle from 'components/atom/SignupTitle/SignupTitle';
import LargeButton from 'components/atom/LargeButton/LargeButton';
import BottomButtonLayout from 'components/template/BottomButtonLayout/BottomButtonLayout';
import SignupInfoTemplate from 'components/template/SignupInfoTemplate/SignupInfoTemplate';

interface SignupStep2Probs {
	selectGender: number;
	setSelectGender: Dispatch<SetStateAction<number>>;
	nextButton: () => void;
}

function SignupStep2({ selectGender, setSelectGender, nextButton }: SignupStep2Probs) {
	const [gender, setGender] = useState<number>(selectGender);
	const [isWomanSelected, setIsWomanSelected] = useState<boolean>(true);
	const [isManSelected, setIsManSelected] = useState<boolean>(false);

	useEffect(() => {
		setSelectGender(gender);

		if (gender === 2) {
			setIsWomanSelected(true);
			setIsManSelected(false);
		} else if (gender === 1) {
			setIsWomanSelected(false);
			setIsManSelected(true);
		}
	}, [setSelectGender, setIsWomanSelected, setIsManSelected, gender]);

	return (
		<SignupInfoTemplate>
			<SignupTitle value="성별을 선택해주세요" />
			<div className="Signup-gender-list">
				<SignupGenderItem gender="여성" imgsrc={Girl} setTest={setGender} isSelected={isWomanSelected} />
				<SignupGenderItem gender="남성" imgsrc={Boy} setTest={setGender} isSelected={isManSelected} />
			</div>
			<BottomButtonLayout>
				<LargeButton buttonClick={nextButton} imgsrc="" value="다음" buttonColor={LargeButtonColor.Green} />
			</BottomButtonLayout>
		</SignupInfoTemplate>
	);
}

export default SignupStep2;
