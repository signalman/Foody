import React, { ReactNode } from 'react';
import LayoutBottomMargin, { LayoutLeftRightMargin } from 'constants/Margin';
import Layout from '../Layout/Layout';
import ContentsLayout from '../ContentsLayout/ContentsLayout';

function RecipeDetailTemplate({ children }: { children: ReactNode[] }) {
	return (
		<>
			{/* 서브 타이틀 : 레시피명 */}
			{children[0]}
			<Layout marginBottom={LayoutBottomMargin.mbTabbar}>
				{/* 레시피 정보 : 이미지, 레시피명, 칼로리, 북마크, 레시피 보러 가기 */}
				<ContentsLayout marginBottom={LayoutBottomMargin.mb20}>{children[1]}</ContentsLayout>

				{/* 필요한 재료 */}
				<ContentsLayout marginLR={LayoutLeftRightMargin.m20}>{children[2]}</ContentsLayout>

				{/* 채워지는 영양소 */}
				<ContentsLayout marginLR={LayoutLeftRightMargin.m20}>{children[3]}</ContentsLayout>
			</Layout>
		</>
	);
}

export default RecipeDetailTemplate;
