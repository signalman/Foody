import React, { ReactNode } from 'react';
import LayoutPadding from 'constants/Padding';
import Header from 'components/organism/Header/Header';
import LayoutBottomMargin, { LayoutTopMargin } from 'constants/Margin';
import Layout from '../Layout/Layout';
import ContentsLayout from '../ContentsLayout/ContentsLayout';

function HomeTemplate({ children }: { children: ReactNode[] }) {
	return (
		<>
			<Header />
			<Layout marginTop={LayoutTopMargin.mt10} marginBottom={LayoutBottomMargin.mbTabbar} padding={LayoutPadding.p20}>
				{/* 인사말 */}
				<ContentsLayout marginBottom={LayoutBottomMargin.mb20}>{children[0]}</ContentsLayout>

				{/* 배너 */}
				<ContentsLayout>{children[1]}</ContentsLayout>

				{/* 오늘의 식사 칼로리 정보 */}
				<ContentsLayout>{children[2]}</ContentsLayout>

				{/* 메뉴 목록 */}
				<ContentsLayout>{children[3]}</ContentsLayout>
			</Layout>
		</>
	);
}

export default HomeTemplate;
