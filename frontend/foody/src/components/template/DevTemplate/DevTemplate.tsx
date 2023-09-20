import React from 'react';
import './DevTemplate.scss';
import LargeButton from 'components/atom/LargeButton/LargeButton';
import ToggleSwitch from 'components/atom/ToggleSwitch/ToggleSwitch';
import LargeButtonColor from 'constants/color';
import LayoutPadding from 'constants/Padding';
import ProgressBar from 'components/molecule/ProgressBar/ProgressBar';
import Layout from '../Layout/Layout';
import GoogleLogo from '../../../assets/icons/googleLogo.png';

function DevTemplate() {
	// const check = false;
	const check = [true, true, false, false, false];

	return (
		<Layout padding={LayoutPadding.p20}>
			<LargeButton imgsrc="" value="hi" buttonColor={LargeButtonColor.Green} />
			<LargeButton imgsrc={GoogleLogo} value="hi" buttonColor={LargeButtonColor.Gray} />
			<ToggleSwitch isRefri onText="냉장고" offText="선반" />
			<ProgressBar ProgressCheck={check} />
		</Layout>
	);
}

export default DevTemplate;
