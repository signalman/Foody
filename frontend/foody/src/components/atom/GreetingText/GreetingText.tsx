import React from 'react';
import './GreetingText.scss';
import { useRecoilValue } from 'recoil';
import { userInfoState } from 'recoil/atoms/nutrientState';

function GreetingText() {
	const userInfo = useRecoilValue(userInfoState);

	return (
		<h1 className="greeting-text-container">
			<div>{userInfo.nickname} 님,</div>
			<div>오늘도 건강한 하루 되세요!</div>
		</h1>
	);
}

export default GreetingText;
