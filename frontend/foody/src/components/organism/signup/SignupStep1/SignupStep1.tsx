import LargeButton from 'components/atom/LargeButton/LargeButton';
import SignupTitle from 'components/atom/SignupTitle/SignupTitle';
import UnderlineInput from 'components/atom/UnderlineInput/UnderlineInput';
import LargeButtonColor from 'constants/color';
import BottomButtonLayout from 'components/template/BottomButtonLayout/BottomButtonLayout';
import React, { useEffect, useState } from 'react';
import SignupInfoTemplate from 'components/template/SignupInfoTemplate/SignupInfoTemplate';

interface SignupStep1Props {
	nickname: string;
	setNickname: (nickname: string) => void;
	nextButton: () => void;
}

function SignupStep1({ nickname, setNickname, nextButton }: SignupStep1Props) {
	const [check, setCheck] = useState<boolean>(false);
	const nicknameCheck = (value: string) => {
		const alphanumbericRegex = /^[가-힣ㄱ-ㅎㅏ-ㅣa-zA-Z0-9]+$/;
		const isTrue = alphanumbericRegex.test(value);

		if (isTrue === true || value === '') {
			return true;
		}

		return false;
	};

	useEffect(() => {
		if (nickname.length === 0) {
			setCheck(true);
		} else {
			setCheck(false);
		}
	}, [nickname, check]);

	return (
		<SignupInfoTemplate>
			<SignupTitle value="닉네임을 알려주세요" />
			<UnderlineInput
				maxlength={16}
				isValid={nicknameCheck}
				onChangeValue={setNickname}
				placeholder="닉네임"
				unit=""
				value={nickname}
			/>
			{!nicknameCheck && nickname.length > 0 && <p>영어 또는 숫자를 포함하여 16자 이내로 입력해 주세요.</p>}
			<BottomButtonLayout>
				<LargeButton
					buttonClick={nickname.length ? nextButton : () => {}}
					imgsrc=""
					value="확인"
					buttonColor={nickname.length ? LargeButtonColor.Green : LargeButtonColor.Gray}
					disabled={check}
				/>
			</BottomButtonLayout>
		</SignupInfoTemplate>
	);
}

export default SignupStep1;
