import React from 'react';
import './PreviewDonutChart.scss';
import { Chart as ChartJS, ArcElement, Tooltip, Legend } from 'chart.js';
import { Doughnut } from 'react-chartjs-2';

ChartJS.register(ArcElement, Tooltip, Legend);

interface PreviewDonutChartProps {
	totalCarolie: number;
	percentage: number;
	Data: {
		labels: string[];
		datasets: {
			data: number[];
			backgroundColor: string[];
			borderColor: string[];
			borderWidth: number;
			circumference: number;
			rotation: number;
			cutout: string;
		}[];
	};
	options: {
		plugins: {
			legend: {
				display: boolean;
			};
		};
	};
}

function PreviewDonutChart({ Data, options, totalCarolie, percentage }: PreviewDonutChartProps) {
	return (
		<div style={{ width: '180px', height: '180px', position: 'relative' }}>
			<Doughnut data={Data} options={options} />
			<div className="text-box carolie-text">
				<p className="carolie-title">채워지는 열량</p>
				<h3 className="carolie-value">{totalCarolie.toFixed(0)}Kcal</h3>
				<h1 className="carlorie-percentage">{percentage}%</h1>
			</div>
		</div>
	);
}

export default PreviewDonutChart;
