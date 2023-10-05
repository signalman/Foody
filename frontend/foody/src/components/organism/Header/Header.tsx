import React, { useState } from 'react';
import './Header.scss';
import logoImg from 'assets/icons/headerLogo.png';
import { AiOutlineMenu } from 'react-icons/ai';
import { GoBell } from 'react-icons/go';
import { Link } from 'react-router-dom';
import toast from 'react-hot-toast';
import MyMenu from '../MyMenu/MyMenu';

function Header() {
	const [newNoti, setNewNoti] = useState(false);
	const [isOpenMyMenu, setIsOpenMyMenu] = useState(false);

	const handleMenu = () => {
		setIsOpenMyMenu(true);
	};

	// TODOS: 알림이 있으면 새로운 알림이 있다고 알려주기
	const handleNotice = () => {
		toast('준비 중인 서비스입니다!', {
			icon: '📢',
		});
		setNewNoti(!newNoti);
	};

	return (
		<>
			<header className="header-container">
				<button type="button" onClick={handleMenu}>
					<AiOutlineMenu size={24} />
				</button>
				<Link to="/" className="header-logo-container">
					<img src={logoImg} alt="logo" />
				</Link>
				<button type="button" className={newNoti ? 'bell' : ''} onClick={handleNotice}>
					<GoBell size={25} />
				</button>
			</header>
			<MyMenu isOpenMyMenu={isOpenMyMenu} setIsOpenMyMenu={setIsOpenMyMenu} />
		</>
	);
}

export default Header;
