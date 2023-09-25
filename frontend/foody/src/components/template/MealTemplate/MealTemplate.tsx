import React, { ReactNode } from 'react';
import LayoutPadding from 'constants/Padding';
import Header from 'components/organism/Header/Header';
import LayoutBottomMargin, { LayoutTopMargin } from 'constants/Margin';
import Layout from '../Layout/Layout';
import ContentsLayout from '../ContentsLayout/ContentsLayout';

function MealTemplate({ children }: { children: ReactNode[] }) {
	return (
		<>
			<Header />
			<Layout marginTop={LayoutTopMargin.mt10} marginBottom={LayoutBottomMargin.mbTabbar} padding={LayoutPadding.p20}>
				<ContentsLayout>{children[0]}</ContentsLayout>
				<ContentsLayout>{children[1]}</ContentsLayout>
				<ContentsLayout>{children[2]}</ContentsLayout>
				<ContentsLayout>{children[3]}</ContentsLayout>
			</Layout>
		</>
	);
}

export default MealTemplate;
