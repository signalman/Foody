import React from 'react';
import './NutrientCheck.scss';

interface NutrientInfoProps {
	value: number;
	total: number;
}

function NutrientInfo({ value, total }: NutrientInfoProps) {
	return (
		<div className="small-box">
			<p className="nutrient-value">{value}</p>
			<p className="nutrient-total">/ {total}</p>
		</div>
	);
}

export default NutrientInfo;
