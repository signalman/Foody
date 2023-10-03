import React, { useEffect, useState } from 'react';
import './RecipeNutrient.scss';
// import PreviewDonutChart from 'components/atom/PreviewDonutChart/PreviewDonutChart';
import getDayofMeal from 'utils/api/meal';
import PreviewDonutChart from 'components/atom/PreviewDonutChart/PreviewDonutChart';
import { useRecoilValue } from 'recoil';
import nutrientState from 'recoil/atoms/nutrientState';
import PreviewNutrientBar from 'components/atom/PreviewNutrientBar/PreviewNutrientBar';

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
	const [todayCurrentNutrient, setTodayCurrentNutrient] = useState<RecipeNutrientType>({
		energy: 0,
		carbohydrates: 0,
		protein: 0,
		dietaryFiber: 0,
		calcium: 0,
		sodium: 0,
		iron: 0,
		fats: 0,
		vitaminA: 0,
		vitaminC: 0,
	}); // 오늘 채운 권장 영양소
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
	const percentage = ((todayCurrentNutrient.energy + recipeNutrient.energy) / todayRecommendNutrient.energy) * 100;
	const Data = {
		labels: ['채워진 열량', '채워지는 열량', '남은 권장 열량'],
		datasets: [
			{
				data: [
					todayCurrentNutrient.energy,
					recipeNutrient.energy,
					todayRecommendNutrient.energy - (todayCurrentNutrient.energy + recipeNutrient.energy),
				],
				backgroundColor: ['#1aa274', '#8dd7be', '#e8e8e8'],
				borderColor: ['#ffffff'],
				borderWidth: 2,
				circumference: 320,
				rotation: 200,
				cutout: '65%',
			},
		],
	};
	const mealNutrient1 = {
		탄수화물: todayCurrentNutrient.carbohydrates,
		단백질: todayCurrentNutrient.protein,
		지방: todayCurrentNutrient.fats,
	};
	const mealNutrient2 = {
		식이섬유: todayCurrentNutrient.dietaryFiber,
		칼슘: todayCurrentNutrient.calcium,
		나트륨: todayCurrentNutrient.sodium,
		철분: todayCurrentNutrient.iron,
	};
	const mealNutrient3 = {
		비타민A: todayCurrentNutrient.vitaminA,
		비타민C: todayCurrentNutrient.vitaminC,
	};

	const previewMealNutrient1 = {
		탄수화물: todayCurrentNutrient.carbohydrates + recipeNutrient.carbohydrates,
		단백질: todayCurrentNutrient.protein + recipeNutrient.protein,
		지방: todayCurrentNutrient.fats + recipeNutrient.fats,
	};
	const previewMealNutrient2 = {
		식이섬유: todayCurrentNutrient.dietaryFiber + recipeNutrient.dietaryFiber,
		칼슘: todayCurrentNutrient.calcium + recipeNutrient.calcium,
		나트륨: todayCurrentNutrient.sodium + recipeNutrient.sodium,
		철분: todayCurrentNutrient.iron + recipeNutrient.iron,
	};
	const previewMealNutrient3 = {
		비타민A: todayCurrentNutrient.vitaminA + recipeNutrient.vitaminA,
		비타민C: todayCurrentNutrient.vitaminC + recipeNutrient.vitaminC,
	};

	useEffect(() => {
		if (!todayCurrentNutrient) {
			const dateObject = new Date();

			const year = dateObject.getFullYear();
			const month = String(dateObject.getMonth() + 1).padStart(2, '0');
			const day = String(dateObject.getDate()).padStart(2, '0');

			const sendDate = `${year}-${month}-${day}`;
			getDayofMeal(sendDate).then((response) => {
				setTodayCurrentNutrient(response.data.total);
			});
		}
	});

	return (
		<section className="recipe-nutrient-container">
			<h3>채워지는 영양소</h3>
			<div className="nutrient-wrap">
				<PreviewDonutChart
					Data={Data}
					options={options}
					totalCarolie={todayCurrentNutrient.energy + recipeNutrient.energy}
					percentage={parseFloat(percentage.toFixed(2))}
				/>
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
