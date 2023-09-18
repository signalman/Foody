import React from 'react';
import './DevTemplate.scss';
import LargeButton from 'components/atom/LargeButton/LargeButton';
import LargeButtonColor from 'constants/color';

function DevTemplate() {
	return (
		<div>
			<LargeButton value="hi" buttonColor={LargeButtonColor.Green} />
			<LargeButton value="hi" buttonColor={LargeButtonColor.Gray} />
		</div>
	);
}

export default DevTemplate;
