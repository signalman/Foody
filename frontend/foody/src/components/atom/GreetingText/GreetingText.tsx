import React from 'react';
import './GreetingText.scss';

function GreetingText() {
	const nickname = '김싸피';

	return (
		<h1 className="greeting-text-container">
			<div>{nickname} 님,</div>
			<div>오늘도 건강한 하루 되세요!</div>
		</h1>
	);
}

export default GreetingText;
