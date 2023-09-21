import React from 'react';
import './SubHeader.scss';
import { BiArrowBack } from 'react-icons/bi';
import { AiOutlineClose } from 'react-icons/ai';

function SubHeader({ isBack = false, title = '' }: { isBack: boolean; title: string }) {
	const handleBack = () => {
		console.log('뒤로가기');
	};

	const handleClose = () => {
		console.log('닫기');
	};

	return (
		<header className="header-container">
			<button type="button" onClick={isBack ? handleBack : handleClose}>
				{isBack ? <BiArrowBack size={24} /> : <AiOutlineClose size={24} />}
			</button>
			<h1 className="sub-header-title">{title}</h1>
		</header>
	);
}

export default SubHeader;
