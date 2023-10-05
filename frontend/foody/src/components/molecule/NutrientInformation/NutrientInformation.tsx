import BarChart from 'components/atom/BarChart/BarChart';
import NutrientInfo from 'components/atom/NutrientCheck/NutrientCheck';
import { BarColor } from 'constants/color';
import './NutrientInformation.scss';
import React from 'react';
import PreviewBarChart from 'components/atom/PreviewBarChart/PreviewBarChart';

interface NutrientInformationProps {
	nutrient: string;
	totalNutrient: number;
	valueNutrient: number;
	previewValue?: number;
}

function NutrientInformation({ nutrient, totalNutrient, valueNutrient, previewValue = 0 }: NutrientInformationProps) {
	return (
		<div className="nutrient-little-box">
			<div className="nutrient-name-wrap">
				<span className="nutrient-name">{nutrient}</span>
				<NutrientInfo
					total={totalNutrient}
					value={
						previewValue !== 0
							? parseFloat(previewValue.toFixed(0))
							: parseFloat((valueNutrient + previewValue).toFixed(0))
					}
				/>
			</div>
			<div className="nutrient">
				{previewValue !== 0 ? (
					<PreviewBarChart
						barcolor={BarColor.Null}
						total={totalNutrient}
						value={valueNutrient}
						previewValue={previewValue}
					/>
				) : (
					<BarChart barcolor={BarColor.Null} total={totalNutrient} value={valueNutrient} previewValue={previewValue} />
				)}
			</div>
		</div>
	);
}

export default NutrientInformation;

NutrientInformation.defaultProps = {
	previewValue: 0,
};
