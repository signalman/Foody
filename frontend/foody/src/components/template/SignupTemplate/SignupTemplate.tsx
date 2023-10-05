import React, { ReactNode } from 'react';
import './SignupTemplate.scss';
import LayoutPadding from 'constants/Padding';
import LayoutBottomMargin, { LayoutTopMargin } from 'constants/Margin';
import Layout from '../Layout/Layout';
import ContentsLayout from '../ContentsLayout/ContentsLayout';

function SignupTemplate({ children }: { children: ReactNode[] }) {
	return (
		<>
			{children[0]}
			<Layout marginTop={LayoutTopMargin.mt10} marginBottom={LayoutBottomMargin.mbTabbar} padding={LayoutPadding.p20}>
				<ContentsLayout>{children[1]}</ContentsLayout>
				<ContentsLayout>{children[2]}</ContentsLayout>
				<ContentsLayout>{children[3]}</ContentsLayout>
				<ContentsLayout>{children[4]}</ContentsLayout>
				<ContentsLayout>{children[5]}</ContentsLayout>
				<ContentsLayout>{children[6]}</ContentsLayout>
			</Layout>
		</>
	);
}

export default SignupTemplate;
