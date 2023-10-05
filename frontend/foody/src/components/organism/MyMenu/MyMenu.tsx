import React, { Dispatch, useEffect } from 'react';
import './MyMenu.scss';
import classNames from 'classnames';
import { ReactComponent as Receipt } from 'assets/icons/receipt.svg';
import { CgClose } from 'react-icons/cg';
import { IoSettingsOutline } from 'react-icons/io5';
import { useRecoilValue } from 'recoil';
import { userInfoState } from 'recoil/atoms/nutrientState';
import useMovePage from 'hooks/useMovePage';
import toast from 'react-hot-toast';

function MyMenu({
	isOpenMyMenu,
	setIsOpenMyMenu,
}: {
	isOpenMyMenu: boolean;
	setIsOpenMyMenu: Dispatch<React.SetStateAction<boolean>>;
}) {
	const userInfo = useRecoilValue(userInfoState);
	const { movePage } = useMovePage();

	const containerClassNames = classNames('my-menu-container', isOpenMyMenu ? 'open' : 'close');
	const bgClassNames = classNames('my-menu-bg', isOpenMyMenu ? 'open' : 'close');

	const handleMenu = (menu: string) => {
		if (menu === 'bookmarkRecipe') {
			movePage('/bookmark', null);
			return;
		}

		if (menu === 'settings') {
			toast('준비 중인 서비스입니다!', {
				icon: '📢',
			});
		}
	};

	useEffect(() => {
		if (isOpenMyMenu) {
			document.body.classList.add('no-scroll');
		} else {
			document.body.classList.remove('no-scroll');
		}

		return () => {
			document.body.classList.remove('no-scroll');
		};
	}, [isOpenMyMenu]);

	return (
		<>
			<div className={containerClassNames}>
				<button
					className="btn-close"
					type="button"
					onClick={() => {
						setIsOpenMyMenu((prev) => !prev);
					}}
				>
					<CgClose size={24} />
				</button>
				<div className="contents-wrap">
					<div className="greeting-wrap">
						<div className="greeting-hello">환영합니다.</div>
						{userInfo && (
							<div className="greeting-nickname">
								&apos;{userInfo.nickname}&apos;
								<span> 님</span>
							</div>
						)}
					</div>
					<div className="menu-list-container">
						<ul className="menu-list">
							<li className="menu-item">
								<button type="button" onClick={() => handleMenu('bookmarkRecipe')}>
									<Receipt />
									<div>북마크 레시피</div>
								</button>
							</li>
							<li className="menu-item">
								<button type="button" onClick={() => handleMenu('settings')}>
									<IoSettingsOutline size={20} />
									<div>설정</div>
								</button>
							</li>
						</ul>
					</div>
				</div>
			</div>
			<div
				role="button"
				aria-label="메뉴 닫기"
				className={bgClassNames}
				onClick={() => setIsOpenMyMenu(false)}
				onKeyDown={() => {}}
				tabIndex={0}
			/>
		</>
	);
}

export default MyMenu;
