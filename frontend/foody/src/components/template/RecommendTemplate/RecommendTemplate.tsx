import React, { ReactNode } from 'react';
import './RecommendTemplate.scss';
import Header from 'components/organism/Header/Header';
import LayoutBottomMargin, { LayoutLeftRightMargin, LayoutTopMargin } from 'constants/Margin';
import Layout from '../Layout/Layout';
import ContentsLayout from '../ContentsLayout/ContentsLayout';

function RecommendTemplate({ children }: { children: ReactNode[] }) {
	return (
		<>
			<Header />
			<Layout marginTop={LayoutTopMargin.mt10} marginBottom={LayoutBottomMargin.mbTabbar}>
				{/* 검색 */}
				<ContentsLayout marginBottom={LayoutBottomMargin.mb20} marginLR={LayoutLeftRightMargin.m10}>
					{children[0]}
				</ContentsLayout>

				{/* OO 님의 입맛에 딱 맞는 음식 */}
				<ContentsLayout>{children[1]}</ContentsLayout>

				{/* OO 님의 식단과 비슷한 음식 */}
				<ContentsLayout>{children[2]}</ContentsLayout>

				{/* 직접 만들어 보세요! */}
				<ContentsLayout>{children[3]}</ContentsLayout>

				{/* 믿고 먹는 FOODY의 음식 컬렉션 */}
				<ContentsLayout>{children[4]}</ContentsLayout>
			</Layout>
		</>
	);
}

export default RecommendTemplate;
