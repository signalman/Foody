import SubHeader from 'components/organism/SubHeader/SubHeader';
import React, { useEffect } from 'react';
import { useSetRecoilState } from 'recoil';
import tabbarState from 'recoil/atoms/tabbarState';

function BookmarkPage() {
	const setTabbar = useSetRecoilState(tabbarState);

	useEffect(() => {
		setTabbar(false);
		return () => {
			setTabbar(true);
		};
	}, [setTabbar]);

	return (
		<>
			<SubHeader isBack title="북마크 레시피" handleMove={null} />
			<div>북마크 페이지</div>
		</>
	);
}

export default BookmarkPage;
