// import SubHeader from 'components/organism/SubHeader/SubHeader';
import React, { ReactNode } from 'react';
import LayoutBottomMargin, { LayoutTopMargin } from 'constants/Margin';
import LayoutPadding from 'constants/Padding';
import Layout from '../Layout/Layout';
import ContentsLayout from '../ContentsLayout/ContentsLayout';
import './FoodMBTITemplate.scss';

function FoodMBTITemplate({ children }: { children: ReactNode[] }) {
	return (
		<Layout marginTop={LayoutTopMargin.mt10} marginBottom={LayoutBottomMargin.mbTabbar} padding={LayoutPadding.p20}>
			<div className="food-MBTI-count">{children[0]}</div>
			<ContentsLayout>{children[1]}</ContentsLayout>
			<ContentsLayout>{children[2]}</ContentsLayout>
		</Layout>
	);
}

export default FoodMBTITemplate;
