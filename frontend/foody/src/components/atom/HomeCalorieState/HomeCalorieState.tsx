import React, { useState } from 'react';
import './HomeCalorieState.scss';
import { Doughnut } from 'react-chartjs-2';
import { useRecoilValue } from 'recoil';
import nutrientState from 'recoil/atoms/nutrientState';
import getDayofMeal from 'utils/api/meal';

const options = {
	plugins: {
		legend: {
			display: false,
		},
	},
};

function HomeCalorieState() {
	const nutrient = useRecoilValue(nutrientState);
	const [todayCurrentEnergy, setTodayCurrentEnergy] = useState<number | null>(null);
	const data = {
		labels: ['채워진 열량', '남은 권장 열량'],
		datasets: [
			{
				data: [
					todayCurrentEnergy,
					nutrient.energy - (todayCurrentEnergy || 0) < 0 ? 0 : nutrient.energy - (todayCurrentEnergy || 0),
				],
				backgroundColor: ['#1aa274', '#eee'],
				borderColor: ['#fff'],
				borderWidth: 1,
				circumference: 360,
				rotation: 0,
				cutout: '85%',
			},
		],
	};

	useState(() => {
		if (!todayCurrentEnergy) {
			const dateObject = new Date();

			const year = dateObject.getFullYear();
			const month = String(dateObject.getMonth() + 1).padStart(2, '0');
			const day = String(dateObject.getDate()).padStart(2, '0');

			const sendDate = `${year}-${month}-${day}`;
			getDayofMeal(sendDate).then((response) => {
				let currentData = response.data.total.energy;
				currentData = 2000;
				if (currentData >= nutrient) {
					currentData = nutrient;
				}
				setTodayCurrentEnergy(currentData);
			});
		}
	});

	return (
		<div className="home-calorie-state-container">
			<div className="doughnut-wrap">
				<Doughnut data={data} options={options} />
				<div className="doughnut-percent">
					{parseFloat((((todayCurrentEnergy || 1) / nutrient.energy) * 100).toFixed(0))}%
				</div>
			</div>
			<div className="doughnut-desc">
				<div>
					<span className="now point">{todayCurrentEnergy && todayCurrentEnergy.toLocaleString()}</span> kcal
				</div>
				<div className="goal">목표 {nutrient.energy.toLocaleString()} kcal</div>
			</div>
		</div>
	);
}

export default HomeCalorieState;
