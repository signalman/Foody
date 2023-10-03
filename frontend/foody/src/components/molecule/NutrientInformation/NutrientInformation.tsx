import BarChart from 'components/atom/BarChart/BarChart';
import NutrientInfo from 'components/atom/NutrientCheck/NutrientCheck';
import { BarColor } from 'constants/color';
import './NutrientInformation.scss';
import React from 'react';

interface NutrientInformationProps {
	nutrient: string;
	totalNutrient: number;
	valueNutrient: number;
	previewValue?: number;
}

function NutrientInformation({ nutrient, totalNutrient, valueNutrient, previewValue = 0 }: NutrientInformationProps) {
	return (
		<div className="nutrient-little-box">
			<p className="nutrient-name">{nutrient}</p>
			<div className="nutrient">
				<BarChart barcolor={BarColor.Null} total={totalNutrient} value={valueNutrient} previewValue={previewValue} />
				<NutrientInfo total={totalNutrient} value={parseFloat((valueNutrient + previewValue).toFixed(0))} />
			</div>
		</div>
	);
}

export default NutrientInformation;

NutrientInformation.defaultProps = {
	previewValue: 0,
};
