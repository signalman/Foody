import LargeButton from 'components/atom/LargeButton/LargeButton';
import SignupTitle from 'components/atom/SignupTitle/SignupTitle';
import UnderlineInput from 'components/atom/UnderlineInput/UnderlineInput';
import LargeButtonColor from 'constants/color';
import React, { useEffect, useState } from 'react';

interface SignupStep1Props {
	nickname: string;
	setNickname: (nickname: string) => void;
	nextButton: () => void;
}

function SignupStep1({ nickname, setNickname, nextButton }: SignupStep1Props) {
	const [check, setCheck] = useState(false);

	useEffect(() => {
		if (nickname.length > 0) {
			setCheck(true);
		} else {
			setCheck(false);
		}
	}, [nickname]);

	return (
		<div className="step1-container">
			<SignupTitle value="닉네임을 알려주세요" />
			<UnderlineInput onChangeValue={setNickname} placeholder="닉네임" unit="" value={nickname} />
			<LargeButton
				buttonClick={nextButton}
				imgsrc=""
				value="확인"
				buttonColor={check ? LargeButtonColor.Green : LargeButtonColor.Gray}
			/>
		</div>
	);
}

export default SignupStep1;
