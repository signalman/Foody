import React from 'react';
import './DonutChart.scss';
import { Chart as ChartJS, ArcElement, Tooltip, Legend } from 'chart.js';
import { Doughnut } from 'react-chartjs-2';

ChartJS.register(ArcElement, Tooltip, Legend);

interface DonutChartProbs {
	breakfast: number;
	lunch: number;
	dinner: number;
	snack: number;

	testBreakfast: number;
	testLunch: number;
	testDinner: number;
	testSnack: number;

	carolie: number;
}

function checkRange(value: number) {
	if (value > 25) {
		return 25;
	}

	return value;
}
function DonutChart({
	breakfast,
	lunch,
	dinner,
	snack,
	testBreakfast,
	testLunch,
	testDinner,
	testSnack,
	carolie,
}: DonutChartProbs) {
	const rangeBreakfast = checkRange((breakfast / testBreakfast) * 25);
	const rangeLunch = checkRange((lunch / testLunch) * 25);
	const rangeDinner = checkRange((dinner / testDinner) * 25);
	const rangeSnack = checkRange((snack / testSnack) * 25);

	const total = 100 - (rangeBreakfast + rangeLunch + rangeDinner + rangeSnack);
	const percentage = (((breakfast + lunch + dinner + snack) / carolie) * 100).toFixed(0);

	const Data = {
		labels: ['아침', '점심', '저녁', '간식'],
		datasets: [
			{
				data: [rangeBreakfast, rangeLunch, rangeDinner, rangeSnack, total],
				backgroundColor: ['#ffde1a', '#ffa112', '#1aa274', '#0094FF', '#eeeeee'],
				borderColor: ['#ffffff'],
				borderWidth: 2,
				circumference: 320,
				rotation: 200,
				cutout: '65%',
			},
		],
	};

	const options = {
		plugins: {
			legend: {
				display: false,
			},
		},
	};

	return (
		<div style={{ width: '180px', height: '180px', position: 'relative' }}>
			<Doughnut data={Data} options={options} />
			<div className="text-box carolie-text">
				<p className="carolie-title">총열량</p>
				<h3 className="carolie-value">{carolie}Kcal</h3>
				<h1 className="carlorie-percentage">{percentage}%</h1>
			</div>
		</div>
	);
}

export default DonutChart;
