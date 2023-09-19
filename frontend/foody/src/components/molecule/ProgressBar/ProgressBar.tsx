import React from 'react';
import './ProgressBar.scss';
// import classNames from 'classnames';
import ProgressStep from 'components/atom/ProgressStep/ProgressStep';

interface ProgressBarProps {
	ProgressCheck: boolean[];
}

function ProgressBar({ ProgressCheck }: ProgressBarProps) {
	return (
		<div className="progress-bar">
			{ProgressCheck.map((value) => (
				<ProgressStep progressState={value} />
			))}
		</div>
	);
}

export default ProgressBar;
