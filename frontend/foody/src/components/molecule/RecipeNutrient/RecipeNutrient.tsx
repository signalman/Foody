import React, { useEffect, useState } from 'react';
import './RecipeNutrient.scss';
import getDayofMeal from 'utils/api/meal';
import PreviewDonutChart from 'components/atom/PreviewDonutChart/PreviewDonutChart';
import { useRecoilValue } from 'recoil';
import nutrientState from 'recoil/atoms/nutrientState';
import PreviewNutrientBar from 'components/atom/PreviewNutrientBar/PreviewNutrientBar';
import { DonutChartDataType } from 'types/receipt';

const defaultData = {
	labels: ['채워진 열량', '채워지는 열량', '남은 권장 열량'],
	datasets: [
		{
			data: [0, 0, 0],
			backgroundColor: ['#1aa274', '#8dd7be', '#e8e8e8'],
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

function RecipeNutrient({
	energy,
	carbohydrates,
	protein,
	dietaryFiber,
	calcium,
	sodium,
	iron,
	fats,
	vitaminA,
	vitaminC,
}: RecipeNutrientType) {
	const todayRecommendNutrient = useRecoilValue(nutrientState); // 일일 총 권장 영양소
	const [todayCurrentNutrient, setTodayCurrentNutrient] = useState<RecipeNutrientType | null>(null); // 오늘 채운 권장 영양소
	const [Data, setData] = useState<DonutChartDataType | null>(null);

	const recipeNutrient = {
		energy,
		carbohydrates,
		protein,
		dietaryFiber,
		calcium,
		sodium,
		iron,
		fats,
		vitaminA,
		vitaminC,
	}; // 레시피 영양소
	const percentage = todayCurrentNutrient
		? ((todayCurrentNutrient.energy + recipeNutrient.energy) / todayRecommendNutrient.energy) * 100
		: 0;

	const mealNutrient1 = {
		탄수화물: todayCurrentNutrient ? todayCurrentNutrient.carbohydrates : 0,
		단백질: todayCurrentNutrient ? todayCurrentNutrient.protein : 0,
		지방: todayCurrentNutrient ? todayCurrentNutrient.fats : 0,
	};
	const mealNutrient2 = {
		식이섬유: todayCurrentNutrient ? todayCurrentNutrient.dietaryFiber : 0,
		칼슘: todayCurrentNutrient ? todayCurrentNutrient.calcium : 0,
		나트륨: todayCurrentNutrient ? todayCurrentNutrient.sodium : 0,
		철분: todayCurrentNutrient ? todayCurrentNutrient.iron : 0,
	};
	const mealNutrient3 = {
		비타민A: todayCurrentNutrient ? todayCurrentNutrient.vitaminA : 0,
		비타민C: todayCurrentNutrient ? todayCurrentNutrient.vitaminC : 0,
	};

	const previewMealNutrient1 = {
		탄수화물: todayCurrentNutrient
			? todayCurrentNutrient.carbohydrates + recipeNutrient.carbohydrates
			: recipeNutrient.carbohydrates,
		단백질: todayCurrentNutrient ? todayCurrentNutrient.protein + recipeNutrient.protein : recipeNutrient.protein,
		지방: todayCurrentNutrient ? todayCurrentNutrient.fats + recipeNutrient.fats : recipeNutrient.fats,
	};
	const previewMealNutrient2 = {
		식이섬유: todayCurrentNutrient
			? todayCurrentNutrient.dietaryFiber + recipeNutrient.dietaryFiber
			: recipeNutrient.dietaryFiber,
		칼슘: todayCurrentNutrient ? todayCurrentNutrient.calcium + recipeNutrient.calcium : recipeNutrient.calcium,
		나트륨: todayCurrentNutrient ? todayCurrentNutrient.sodium + recipeNutrient.sodium : recipeNutrient.sodium,
		철분: todayCurrentNutrient ? todayCurrentNutrient.iron + recipeNutrient.iron : recipeNutrient.iron,
	};
	const previewMealNutrient3 = {
		비타민A: todayCurrentNutrient ? todayCurrentNutrient.vitaminA + recipeNutrient.vitaminA : recipeNutrient.vitaminA,
		비타민C: todayCurrentNutrient ? todayCurrentNutrient.vitaminC + recipeNutrient.vitaminC : recipeNutrient.vitaminC,
	};

	useEffect(() => {
		if (!todayCurrentNutrient) {
			const dateObject = new Date();

			const year = dateObject.getFullYear();
			const month = String(dateObject.getMonth() + 1).padStart(2, '0');
			const day = String(dateObject.getDate()).padStart(2, '0');

			const sendDate = `${year}-${month}-${day}`;
			getDayofMeal(sendDate).then((response) => {
				const responseData = response.data.total;
				const remindEnergy = todayRecommendNutrient.energy - (responseData.energy + recipeNutrient.energy);
				setTodayCurrentNutrient(response.data.total);
				setData({
					labels: ['채워진 열량', '채워지는 열량', '남은 권장 열량'],
					datasets: [
						{
							data: [responseData.energy, recipeNutrient.energy, remindEnergy < 0 ? 0 : remindEnergy],
							backgroundColor: ['#1aa274', '#8dd7be', '#e8e8e8'],
							borderColor: ['#ffffff'],
							borderWidth: 2,
							circumference: 320,
							rotation: 200,
							cutout: '65%',
						},
					],
				});
			});
		}
	});

	return (
		<section className="recipe-nutrient-container">
			<h3>채워지는 영양소</h3>
			<div className="nutrient-wrap">
				<PreviewDonutChart
					Data={Data || defaultData}
					options={options}
					totalCarolie={todayCurrentNutrient ? todayCurrentNutrient.energy + recipeNutrient.energy : 0}
					percentage={parseFloat(percentage.toFixed(2))}
				/>
				<p className="nutrient-bar-desc-wrap">
					<span>현재 영양소</span>
					<span>섭취 후 영양소</span>
					<span>부족 혹은 과섭취</span>
				</p>
				<PreviewNutrientBar title="영양소" mealNutrient={mealNutrient1} previewMealNutrient={previewMealNutrient1} />
				<PreviewNutrientBar
					title="다량 무기질"
					mealNutrient={mealNutrient2}
					previewMealNutrient={previewMealNutrient2}
				/>
				<PreviewNutrientBar title="비타민" mealNutrient={mealNutrient3} previewMealNutrient={previewMealNutrient3} />
			</div>
		</section>
	);
}

export default RecipeNutrient;
