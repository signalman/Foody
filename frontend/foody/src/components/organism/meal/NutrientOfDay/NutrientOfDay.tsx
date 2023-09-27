import React from 'react';
import './NutrientOfDay.scss';
import NutrientInformation from 'components/molecule/NutrientInformation/NutrientInformation';
import { HiOutlineChevronRight } from 'react-icons/hi';

function NutrientOfDay() {
	const data = {
		tan: 196,
		dan: 44,
		ji: 46,

		totalTan: 257,
		totalDan: 93.5,
		totalJi: 70,
	};
	return (
		<div>
			<div className="nutirent-of-day-title-box">
				<p className="nutrient-of-day-title">일일 영양소</p>
				<button className="nutrient-of-day-button" type="button">
					<p>더보기</p>
					<HiOutlineChevronRight size={14} />
				</button>
			</div>
			<div className="nutrient-main-box">
				<NutrientInformation nutrient="탄수화물" totalNutrient={data.totalTan} valueNutrient={data.tan} />
				<NutrientInformation nutrient="단백질" totalNutrient={data.totalDan} valueNutrient={data.dan} />
				<NutrientInformation nutrient="지방" totalNutrient={data.totalJi} valueNutrient={data.ji} />
			</div>
		</div>
	);
}

export default NutrientOfDay;
