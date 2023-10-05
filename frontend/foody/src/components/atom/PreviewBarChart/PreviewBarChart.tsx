import React from 'react';
import './PreviewBarChart.scss';
import classNames from 'classnames';
import { BarColor, PreviewBarColor } from 'constants/color';

interface PreviewBarChartProps {
	total: number;
	value: number;
	previewValue?: number;
	barcolor: BarColor;
}

function PreviewBarChart({ total, value, previewValue = 0, barcolor }: PreviewBarChartProps) {
	const totalWidth = 100;

	let barWidth: number = (value / total) * totalWidth; // n%
	let previewBarWidth: number = (previewValue / total) * totalWidth;
	// const checkBarWidth: number = ; // n%
	// const checkBar: number = (value + previewValue) / totalWidth; // 0.0n
	// let previewBarWidth: number = (previewValue / total) * totalWidth;

	let checkColor = barcolor; // 기본 색상은 barcolor 그대로 사용
	let previewColor: PreviewBarColor = PreviewBarColor.Null;

	if (previewBarWidth >= 34 && previewBarWidth <= 105) {
		checkColor = BarColor.Green; // 초록색
		previewColor = PreviewBarColor.Green;
	} else {
		checkColor = BarColor.Red; // 빨간색
		previewColor = PreviewBarColor.Red;
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

export default PreviewBarChart;

PreviewBarChart.defaultProps = {
	previewValue: 0,
};
