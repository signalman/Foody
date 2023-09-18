import React, { useState } from 'react';
import './Header.scss';
import logoImg from 'assets/icons/headerLogo.png';
import { AiOutlineMenu } from 'react-icons/ai';
import { GoBell } from 'react-icons/go';

function Header() {
	const [newNoti, setNewNoti] = useState(false);

	const handleMenu = () => {
		alert('마이페이지');
	};

	// TODOS: 알림이 있으면 새로운 알림이 있다고 알려주기
	const handleNotice = () => {
		alert('알림');
		setNewNoti(!newNoti);
	};

	return (
		<header className="header-container">
			<button type="button" onClick={handleMenu}>
				<AiOutlineMenu size={16} />
			</button>
			<div className="header-logo-container">
				<img src={logoImg} alt="logo" />
			</div>
			<button type="button" className={newNoti ? 'bell' : ''} onClick={handleNotice}>
				<GoBell size={17} />
			</button>
		</header>
	);
}

export default Header;
