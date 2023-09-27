import BarChart from 'components/atom/BarChart/BarChart';
import NutrientInfo from 'components/atom/NutrientInfo/NutrientInfo';
import { BarColor } from 'constants/color';
import './NutrientInformation.scss';
import React from 'react';

interface NutrientInformationProps {
	nutrient: string;
	totalNutrient: number;
	valueNutrient: number;
}

function NutrientInformation({ nutrient, totalNutrient, valueNutrient }: NutrientInformationProps) {
	return (
		<div className="nutrient-little-box">
			<p className="nutrient-name">{nutrient}</p>
			<div className="nutrient">
				<BarChart barcolor={BarColor.Null} total={totalNutrient} value={valueNutrient} />
				<NutrientInfo total={totalNutrient} value={valueNutrient} />
			</div>
		</div>
	);
}

export default NutrientInformation;
