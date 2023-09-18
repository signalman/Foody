import React from 'react';
import './DevTemplate.scss';
import LargeButton from 'components/atom/LargeButton/LargeButton';
import ToggleSwitch from 'components/atom/ToggleSwitch/ToggleSwitch';
import LargeButtonColor from 'constants/color';

function DevTemplate() {
	return (
		<div>
			<LargeButton value="hi" buttonColor={LargeButtonColor.Green} />
			<LargeButton value="hi" buttonColor={LargeButtonColor.Gray} />
			<ToggleSwitch isRefri onText="냉장고" offText="선반" />
		</div>
	);
}

export default DevTemplate;
