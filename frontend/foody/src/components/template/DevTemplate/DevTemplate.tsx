import React from 'react';
import './DevTemplate.scss';
import LayoutBottomMargin from 'constants/Margin';
import Header from 'components/organism/Header/Header';
import Layout from '../Layout/Layout';

function DevTemplate() {
	return (
		<Layout marginBottom={LayoutBottomMargin.mbTabbar}>
			<Header />
		</Layout>
	);
}

export default DevTemplate;
