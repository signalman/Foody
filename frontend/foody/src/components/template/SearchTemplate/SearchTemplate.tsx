import React, { ReactNode } from 'react';
import './SearchTemplate.scss';
import LayoutPadding from 'constants/Padding';
import LayoutBottomMargin, { LayoutLeftRightMargin, LayoutTopMargin } from '../../../constants/Margin';
import Layout from '../Layout/Layout';
import ContentsLayout from '../ContentsLayout/ContentsLayout';
import FloatingLayout from '../FloatingLayout/FloatingLayout';

function SearchTemplate({ children }: { children: ReactNode[] }) {
	return (
		<Layout marginTop={LayoutTopMargin.mt10} padding={LayoutPadding.p10}>
			{/* 인풋 */}
			<ContentsLayout marginBottom={LayoutBottomMargin.mb20}>{children[0]}</ContentsLayout>

			{/* 직접 입력하기 버튼 */}
			<ContentsLayout marginBottom={LayoutBottomMargin.mb20}>{children[1]}</ContentsLayout>

			{/* 선택된 재료 */}
			<ContentsLayout marginBottom={LayoutBottomMargin.mb0} marginLR={LayoutLeftRightMargin.m10}>
				{children[2]}
			</ContentsLayout>

			{/* 검색 결과 */}
			<ContentsLayout marginBottom={LayoutBottomMargin.mb0} marginLR={LayoutLeftRightMargin.m10}>
				{children[3]}
			</ContentsLayout>

			{/* Floating 메뉴 */}
			<FloatingLayout>{children[4]}</FloatingLayout>

			{/* 검색/등록 버튼 */}
			<ContentsLayout marginBottom={LayoutBottomMargin.mb0}>{children[5]}</ContentsLayout>
		</Layout>
	);
}

export default SearchTemplate;
