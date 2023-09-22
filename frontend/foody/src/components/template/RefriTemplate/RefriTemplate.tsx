import React, { ReactNode } from 'react';
import './RefriTemplate.scss';
import Header from 'components/organism/Header/Header';
import LayoutBottomMargin, { LayoutLeftRightMargin, LayoutTopMargin } from 'constants/Margin';
import Layout from '../Layout/Layout';
import ContentsLayout from '../ContentsLayout/ContentsLayout';

function RefriTemplate({ children }: { children: ReactNode[] }) {
	return (
		<>
			<Header />
			<Layout marginTop={LayoutTopMargin.mt10}>
				{/* 나의 냉장고 or 나의 선반 및 토글 버튼 */}
				<ContentsLayout>{children[0]}</ContentsLayout>

				{/* 재료 카테고리 */}
				<ContentsLayout marginBottom={LayoutBottomMargin.mb20}>{children[1]}</ContentsLayout>

				{/* 재료 목록 */}
				<ContentsLayout marginBottom={LayoutBottomMargin.mb0} marginLR={LayoutLeftRightMargin.m10}>
					{children[2]}
				</ContentsLayout>
			</Layout>
		</>
	);
}

export default RefriTemplate;
