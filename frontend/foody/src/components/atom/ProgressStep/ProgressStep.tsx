import React from 'react';
import './ProgressStep.scss';
// import { ProgressStepColor } from 'constants/color';
import classNames from 'classnames';

interface ProgressStepProps {
	progressState: boolean;
}

function ProgressStep({ progressState }: ProgressStepProps) {
	return <div className={classNames('progress', progressState ? 'green' : 'lightgreen')} />;
}

export default ProgressStep;

// ProgressStep.defaultProps = {
// 	progressColor: ProgressStepColor.Green,
// };
