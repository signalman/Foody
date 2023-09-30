import React from 'react';
import './NutrientUnderline.scss';

interface NutrientUnderlineProps {
	title: string;
	value: number;
	unit: string;
	iswhite: boolean;
}

function NutrientUnderline({ title, value, unit, iswhite }: NutrientUnderlineProps) {
	const changebox = `nutrient-underline-box ${iswhite ? 'white' : 'black'} `;
	const changetitle = `${iswhite ? 'white-title' : 'black-title'}`;
	const changevalue = `${iswhite ? 'white-value' : 'black-value'}`;
	return (
		<div className={changebox}>
			<p className={changetitle}>{title}</p>
			<div className={changevalue}>
				<p>{value}</p>
				<p>{unit}</p>
			</div>
		</div>
	);
}

export default NutrientUnderline;
