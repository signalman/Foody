import React from 'react';
import { BiArrowBack } from 'react-icons/bi';
import { AiOutlineClose } from 'react-icons/ai';

function SubHeader({ isBack, title }: { isBack: boolean; title: string }) {
	const handleBack = () => {
		console.log('뒤로가기');
	};

	const handleClose = () => {
		console.log('닫기');
	};

	return (
		<header className="header-container">
			<button type="button" onClick={isBack ? handleBack : handleClose}>
				{isBack ? <BiArrowBack size={16} /> : <AiOutlineClose size={16} />}
			</button>
			<h1 className="sub-header-title">{title}</h1>
		</header>
	);
}

export default SubHeader;
