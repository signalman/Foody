import React from 'react';
import './PreviewNutrientBar.scss';
import { useRecoilState } from 'recoil';
import nutrientState from 'recoil/atoms/nutrientState';
import { NutrientTotal } from 'types/meal';
import NutrientInformation from 'components/molecule/NutrientInformation/NutrientInformation';

interface PreviewNutrientBarProps {
	title: string;
	mealNutrient: { [key: string]: number };
	previewMealNutrient: { [key: string]: number };
}

const convert: { [key: string]: keyof NutrientTotal } = {
	탄수화물: 'carbohydrates',
	단백질: 'protein',
	지방: 'fats',
	식이섬유: 'dietaryFiber',
	칼슘: 'calcium',
	나트륨: 'sodium',
	철분: 'iron',
	비타민A: 'vitaminA',
	비타민C: 'vitaminC',
};

function PreviewNutrientBar({ title, mealNutrient, previewMealNutrient }: PreviewNutrientBarProps) {
	const [totalNutrient] = useRecoilState(nutrientState);

	return (
		<div className="nutrient-bar-container">
			<div className="nutirent-of-day-title-box">
				<p className="nutrient-of-day-title">{title}</p>
			</div>
			<div className="nutrient-main-box">
				{Object.keys(mealNutrient).map((nutrientName) => (
					<NutrientInformation
						key={nutrientName}
						nutrient={nutrientName}
						totalNutrient={totalNutrient[convert[nutrientName]]} // 여기에 영양소에 대한 적절한 기본값 또는 측정된 값 사용
						valueNutrient={mealNutrient[nutrientName]} // 객체의 키를 사용하여 해당 영양소의 값을 가져옵니다.
						previewValue={previewMealNutrient[nutrientName]}
					/>
				))}
			</div>
		</div>
	);
}

export default PreviewNutrientBar;
