import ProgressBar from 'components/molecule/ProgressBar/ProgressBar';
import SignupStep1 from 'components/organism/signup/SignupStep1/SignupStep1';
import SignupStep2 from 'components/organism/signup/SignupStep2/SignupStep2';
import SignupStep3 from 'components/organism/signup/SignupStep3/SignupStep3';
import SignupStep4 from 'components/organism/signup/SignupStep4/SignupStep4';
import SignupStep5 from 'components/organism/signup/SignupStep5/SignupStep5';
import SignupTemplate from 'components/template/SignupTemplate/SignupTemplate';
import React, { useState } from 'react';

function SignupPage() {
	// 첫 번째 스텝
	const [nickname, setNickname] = useState<string>('');
	// 두 번째 스텝
	const [selectGender, setSeleteGender] = useState<string>('여성');

	// 세 번째 스텝
	const [age, setAge] = useState<string>('');
	// 네 번째 스텝
	const [height, setHeight] = useState<string>('');
	const [weight, setWeight] = useState<string>('');
	// 다섯 번째 스텝
	const [move, setMove] = useState<number>(1);

	const [index, setIndex] = useState<number>(0);
	const check2 = [true, false, false, false, false];

	const nextButton = () => {
		check2[index] = true;
		setIndex(index + 1);
	};

	return (
		<SignupTemplate>
			<ProgressBar ProgressCheck={check2} />
			<SignupStep1 nextButton={nextButton} setNickname={setNickname} nickname={nickname} />
			<SignupStep2 nextButton={nextButton} selectGender={selectGender} setSelectGender={setSeleteGender} />
			<SignupStep3 nextButton={nextButton} setAge={setAge} age={age} />
			<SignupStep4
				nextButton={nextButton}
				height={height}
				weight={weight}
				setHeight={setHeight}
				setWeight={setWeight}
			/>
			<SignupStep5 nextButton={nextButton} move={move} setMove={setMove} />
		</SignupTemplate>
	);
}

export default SignupPage;
