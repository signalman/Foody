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

	// 프로그레스 바 설정
	const [index, setIndex] = useState<number>(1);

	const [bar, setBar] = useState([true, false, false, false, false]);

	const nextButton = () => {
		const updateBar = [...bar];
		setIndex(index + 1);
		updateBar[index] = true;
		setBar(updateBar);
	};

	return (
		<SignupTemplate>
			<ProgressBar ProgressCheck={bar} />
			{index === 1 && <SignupStep1 nextButton={nextButton} setNickname={setNickname} nickname={nickname} />}
			{index === 2 && (
				<SignupStep2 nextButton={nextButton} selectGender={selectGender} setSelectGender={setSeleteGender} />
			)}
			{index === 3 && <SignupStep3 nextButton={nextButton} setAge={setAge} age={age} />}
			{index === 4 && (
				<SignupStep4
					nextButton={nextButton}
					height={height}
					weight={weight}
					setHeight={setHeight}
					setWeight={setWeight}
				/>
			)}
			{index === 5 && <SignupStep5 nextButton={nextButton} move={move} setMove={setMove} />}
			<span>
				닉네임 : {nickname} 성별 : {selectGender} 나이 : {age} 키 : {height} 몸무게 : {weight} 활동량 : {move}
				인덱스 : {index}
			</span>
		</SignupTemplate>
	);
}

export default SignupPage;
