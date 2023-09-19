import React, { ReactNode } from 'react';
import LayoutPadding from 'constants/Padding';
import Header from 'components/organism/Header/Header';
import Layout from '../Layout/Layout';

function HomeTemplate({ children }: { children: ReactNode[] }) {
	return (
		<>
			<Header />
			<Layout padding={LayoutPadding.md}>
				{/* 인사말 */}
				<div>{children[0]}</div>

				{/* 배너 */}
				<div>{children[1]}</div>

				{/* 오늘의 식사 칼로리 정보 */}
				<div>{children[2]}</div>

				{/* 메뉴 목록 */}
				<div>{children[3]}</div>
			</Layout>
		</>
	);
}

export default HomeTemplate;
