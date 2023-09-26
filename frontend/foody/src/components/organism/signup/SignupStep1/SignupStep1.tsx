import LargeButton from 'components/atom/LargeButton/LargeButton';
import SignupTitle from 'components/atom/SignupTitle/SignupTitle';
import UnderlineInput from 'components/atom/UnderlineInput/UnderlineInput';
import LargeButtonColor from 'constants/color';
import React from 'react';

interface SignupStep1Props {
	nickname: string;
	setNickname: (nickname: string) => void;
	nextButton: () => void;
}

function SignupStep1({ nickname, setNickname, nextButton }: SignupStep1Props) {
	const nicknameCheck = (value: string) => {
		const alphanumbericRegex = /^[a-zA-Z0-9]+$/;
		const isTrue = alphanumbericRegex.test(value);

		if (isTrue === true || value === '') {
			return true;
		}

		return false;
	};

	return (
		<div className="step1-container">
			<SignupTitle value="닉네임을 알려주세요" />
			<UnderlineInput
				maxlength={16}
				isValid={nicknameCheck}
				onChangeValue={setNickname}
				placeholder="닉네임"
				unit=""
				value={nickname}
			/>
			{!nicknameCheck && nickname.length > 0 && <p>키는 100이상 200이하로 입력해주세요.</p>}
			<LargeButton
				buttonClick={nextButton}
				imgsrc=""
				value="확인"
				buttonColor={nicknameCheck(nickname) ? LargeButtonColor.Green : LargeButtonColor.Gray}
			/>
		</div>
	);
}

export default SignupStep1;
