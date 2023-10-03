import React from 'react';
import './BarChart.scss';
import classNames from 'classnames';
import { BarColor, PreviewBarColor } from 'constants/color';

interface BarChartProps {
	total: number;
	value: number;
	previewValue?: number;
	barcolor: BarColor;
}

function BarChart({ total, value, previewValue = 0, barcolor }: BarChartProps) {
	const totalWidth = 100;

	let barWidth: number = (value / total) * totalWidth; // n%
	const checkBar: number = barWidth / totalWidth; // 0.0n
	let previewBarWidth: number = (previewValue / total) * totalWidth;

	let checkColor = barcolor; // 기본 색상은 barcolor 그대로 사용
	let previewColor: PreviewBarColor = PreviewBarColor.Null;

	if (barcolor === 'noColor') {
		// barcolor가 'null'인 경우
		if (checkBar <= 0.33 || checkBar >= 1.01) {
			checkColor = BarColor.Red; // 빨간색
			previewColor = PreviewBarColor.Red;
		} else if (checkBar >= 0.33 && checkBar <= 1.01) {
			checkColor = BarColor.Green; // 초록색
			previewColor = PreviewBarColor.Green;
		}
	}

	if (barWidth >= totalWidth) {
		barWidth = totalWidth;
	}
	if (previewBarWidth >= totalWidth) {
		previewBarWidth = totalWidth;
	}

	return (
		<div className="bar-box">
			<div className={classNames('bar-content', checkColor)} style={{ width: `${barWidth}%` }} />
			<div className={classNames('preview-bar', previewColor)} style={{ width: `${previewBarWidth}%` }} />
		</div>
	);
}

export default BarChart;

BarChart.defaultProps = {
	previewValue: 0,
};
