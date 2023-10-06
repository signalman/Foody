import ProgressBar from 'components/molecule/ProgressBar/ProgressBar';
import SignupStep1 from 'components/organism/signup/SignupStep1/SignupStep1';
import SignupStep2 from 'components/organism/signup/SignupStep2/SignupStep2';
import SignupStep3 from 'components/organism/signup/SignupStep3/SignupStep3';
import SignupStep4 from 'components/organism/signup/SignupStep4/SignupStep4';
import SignupStep5 from 'components/organism/signup/SignupStep5/SignupStep5';
import SignupTemplate from 'components/template/SignupTemplate/SignupTemplate';
import useMovePage from 'hooks/useMovePage';
import React, { useState } from 'react';
import { getSignupInformation } from 'utils/api/auth';
import SubHeader from 'components/organism/SubHeader/SubHeader';

function SignupPage() {
	const { movePage } = useMovePage();
	// 첫 번째 스텝
	const [nickname, setNickname] = useState<string>('');
	// 두 번째 스텝
	const [gender, setGender] = useState<number>(2);

	// 세 번째 스텝
	const [age, setAge] = useState<string>('');
	// 네 번째 스텝
	const [height, setHeight] = useState<string>('');
	const [weight, setWeight] = useState<string>('');
	// 다섯 번째 스텝
	const [activityLevel, setActivityLevel] = useState<number>(1);

	// 프로그레스 바 설정
	const [index, setIndex] = useState<number>(1);

	const [bar, setBar] = useState([true, false, false, false, false]);

	const nextButton = () => {
		const updateBar = [...bar];
		setIndex(index + 1);
		updateBar[index] = true;
		setBar(updateBar);
	};

	const backButton = () => {
		const nextIdx = index - 1;
		const updateBar = [...bar];

		if (nextIdx === 0) {
			movePage('/login', null);
		}

		setIndex(nextIdx);
		updateBar[nextIdx] = false;
		setBar(updateBar);
	};

	const handleSignup = () => {
		const information = {
			nickname,
			height: parseInt(height, 10),
			weight: parseInt(weight, 10),
			gender,
			age: parseInt(age, 10),
			activityLevel,
		};

		getSignupInformation(information).then(() => {
			movePage('/fbti', null);
		});
	};

	return (
		<SignupTemplate>
			<SubHeader isBack title="" handleMove={backButton} />
			<ProgressBar ProgressCheck={bar} />
			{index === 1 && <SignupStep1 nextButton={nextButton} setNickname={setNickname} nickname={nickname} />}
			{index === 2 && <SignupStep2 nextButton={nextButton} selectGender={gender} setSelectGender={setGender} />}
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
			{index === 5 && <SignupStep5 nextButton={handleSignup} move={activityLevel} setMove={setActivityLevel} />}
		</SignupTemplate>
	);
}

export default SignupPage;
