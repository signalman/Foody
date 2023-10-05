import React, { useEffect, useState } from 'react';
import './MealChart.scss';
import { Chart as ChartJS, ArcElement, Tooltip, Legend } from 'chart.js';
import { Doughnut } from 'react-chartjs-2';

ChartJS.register(ArcElement, Tooltip, Legend);

interface MealChartProps {
	value: number;
	total: number;
	meal: string;
}
function MealChart({ meal, value, total }: MealChartProps) {
	const [remaincheck, setRemainCheck] = useState<boolean>(false);
	const [remain, setRemain] = useState<number>(Math.floor(total - value));
	const [reverseRemain, setReverseRemain] = useState<number>(0);

	const Data = {
		labels: [{ meal }],
		datasets: [
			{
				data: [value, remain],
				backgroundColor: [remaincheck ? '#ffa112' : '#1aa274', '#ffde1a'],
				borderColor: [remaincheck ? '#ffa112' : '#1aa274', '#ffde1a'],
				borderWidth: 2,
				circumference: 180,
				rotation: 270,
				cutout: '85%',
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

	useEffect(() => {
		if (remain < 0) {
			const number = Math.floor(remain);
			setRemainCheck(true);
			setReverseRemain(-number);
			setRemain(0);
		}
	}, [remain]);

	return (
		<div className="meal-chart-container">
			<p className="meal-chart-title">{meal} 권장 섭취량</p>
			<div style={{ width: '130px', height: '130px', position: 'relative' }} className="meal-chart-box">
				<Doughnut data={Data} options={options} />
				<div className="meal-text-box">
					<p className="meal-chart-cal">섭취 칼로리</p>
					<div className="meal-chart-value-box">
						<h3 className="meal-chart-value">{value}</h3>
						<h1 className="meal-chart-percentage">Kcal</h1>
					</div>
				</div>
				<div className="meal-value-box">
					<p className="meal-chart-zero">0</p>
					<p className="meal-chart-total">{total}</p>
				</div>
			</div>
			<div className="meal-chart-text-container">
				<p>권장칼로리보다</p>
				<div className="meal-chart-text-second">
					{remaincheck ? (
						<p className="meal-chart-text-remain">{reverseRemain}kcal</p>
					) : (
						<p className="meal-chart-text-remain">{remain}kcal</p>
					)}
					{remaincheck ? <p className="meal-chart-text-under">초과</p> : <p className="meal-chart-text-under">부족</p>}
					<p>합니다</p>
				</div>
			</div>
		</div>
	);
}

export default MealChart;
