import React, { useEffect } from 'react';
import SubHeader from 'components/organism/SubHeader/SubHeader';
import Layout from 'components/template/Layout/Layout';
import { LayoutTopMargin } from 'constants/Margin';
import LayoutPadding from 'constants/Padding';
import { useSetRecoilState } from 'recoil';
import tabbarState from 'recoil/atoms/tabbarState';
import BookmarkList from 'components/molecule/BookmarkList/BookmarkList';

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
			<Layout padding={LayoutPadding.p10} marginTop={LayoutTopMargin.mt10}>
				<BookmarkList />
			</Layout>
		</>
	);
}

export default BookmarkPage;
