import React from 'react';
import './ProgressBar.scss';
// import classNames from 'classnames';
import ProgressStep from 'components/atom/ProgressStep/ProgressStep';
import uuid from 'react-uuid';

interface ProgressBarProps {
	ProgressCheck: boolean[];
}

function ProgressBar({ ProgressCheck }: ProgressBarProps) {
	return (
		<div className="progress-bar">
			{ProgressCheck.map((value) => (
				<ProgressStep key={uuid()} progressState={value} />
			))}
		</div>
	);
}

export default ProgressBar;
