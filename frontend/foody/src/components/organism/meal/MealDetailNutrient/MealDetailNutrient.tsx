import React from 'react';
import './MealDetailNutrient.scss';
import NutrientUnderline from 'components/atom/NutrientUnderline/NutrientUnderline';

// interface MealDetailNutrient {
//     tan: number;
//     dan: number;
//     ji: number;
//     na: number;
//     sume: number;
//     sik: number;
//     iron: number;

// }
function MealDetailNutrient() {
	return (
		<div className="meal-detail-nutrient-box">
			<p>title</p>
			<p className="meal-detail-nutrient-value">value</p>
			<div className="meal-detail-nutrient-content">
				<NutrientUnderline iswhite={false} title="탄수화물" value={30} unit="g" />
				<NutrientUnderline iswhite={false} title="단백질" value={30} unit="g" />
				<NutrientUnderline iswhite={false} title="지방" value={30} unit="g" />
				<NutrientUnderline iswhite={false} title="식이섬유" value={30} unit="g" />
				<NutrientUnderline iswhite={false} title="칼슘" value={30} unit="mg" />
				<NutrientUnderline iswhite={false} title="나트륨" value={30} unit="mg" />
				<NutrientUnderline iswhite={false} title="철분" value={30} unit="mg" />
				<NutrientUnderline iswhite={false} title="비타민A" value={30} unit="ug" />
				<NutrientUnderline iswhite={false} title="비타민C" value={30} unit="mg" />
			</div>
		</div>
	);
}

export default MealDetailNutrient;
