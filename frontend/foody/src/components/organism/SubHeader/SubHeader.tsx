import React from 'react';
import './SubHeader.scss';
import { BiArrowBack } from 'react-icons/bi';
import { AiOutlineClose } from 'react-icons/ai';
import useMovePage from 'hooks/useMovePage';

function SubHeader({
	isBack = false,
	title = '',
	handleMove = null,
}: {
	isBack: boolean;
	title: string;
	handleMove: null | (() => void);
}) {
	const { goBack } = useMovePage();

	const handleBack = () => {
		if (!handleMove) {
			goBack();
			return;
		}
		handleMove();
	};

	const handleClose = () => {
		if (!handleMove) {
			console.log('닫기');
			return;
		}
		handleMove();
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
