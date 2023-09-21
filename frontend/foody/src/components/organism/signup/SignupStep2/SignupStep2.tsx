import React, { useState, Dispatch, SetStateAction, useEffect } from 'react';
import './SignupStep2.scss';
import Boy from 'assets/images/Boy.png';
import Girl from 'assets/images/Girl.png';
import SignupGenderItem from 'components/atom/SignupGenderItem/SignupGenderItem';
import LargeButtonColor from 'constants/color';
import SignupTitle from 'components/atom/SignupTitle/SignupTitle';
import LargeButton from 'components/atom/LargeButton/LargeButton';

interface SignupStep2Probs {
	selectGender: string;
	setSelectGender: Dispatch<SetStateAction<string>>;
	nextButton: () => void;
}

function SignupStep2({ selectGender, setSelectGender, nextButton }: SignupStep2Probs) {
	const [test, setTest] = useState<string>(selectGender);
	const [isWomanSelected, setIsWomanSelected] = useState<boolean>(true);
	const [isManSelected, setIsManSelected] = useState<boolean>(false);

	useEffect(() => {
		setSelectGender(test);

		if (test === '여성') {
			setIsWomanSelected(true);
			setIsManSelected(false);
		} else if (test === '남성') {
			setIsWomanSelected(false);
			setIsManSelected(true);
		}
	}, [test, setSelectGender, setIsWomanSelected, setIsManSelected]);

	return (
		<div>
			<SignupTitle value="성별을 선택해주세요" />
			<div className="Signup-gender-list">
				<SignupGenderItem gender="여성" imgsrc={Girl} setTest={setTest} isSelected={isWomanSelected} />
				<SignupGenderItem gender="남성" imgsrc={Boy} setTest={setTest} isSelected={isManSelected} />
			</div>
			<LargeButton buttonClick={nextButton} imgsrc="" value="다음" buttonColor={LargeButtonColor.Green} />
		</div>
	);
}

export default SignupStep2;
