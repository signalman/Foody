import React from 'react';
import './BarChart.scss';
import classNames from 'classnames';
import { BarColor } from 'constants/color';

interface BarChartProps {
	total: number;
	value: number;
	barcolor: BarColor;
}

function BarChart({ total, value, barcolor }: BarChartProps) {
	const totalWidth = 268.6;

	let barWidth: number = (value / total) * totalWidth;
	const checkBar: number = barWidth / totalWidth;

	let checkColor = barcolor; // 기본 색상은 barcolor 그대로 사용

	if (barcolor === 'noColor') {
		// barcolor가 'null'인 경우
		if (checkBar <= 0.33 || checkBar >= 1.01) {
			checkColor = BarColor.Red; // 빨간색
		} else if (checkBar >= 0.33 && checkBar <= 1.01) {
			checkColor = BarColor.Green; // 초록색
		}
	}

	if (barWidth > totalWidth) {
		barWidth = totalWidth;
	}
	return (
		<div className="bar-box">
			<div className={classNames('bar-content', checkColor)} style={{ width: `${barWidth}px` }} />
		</div>
	);
}

export default BarChart;
