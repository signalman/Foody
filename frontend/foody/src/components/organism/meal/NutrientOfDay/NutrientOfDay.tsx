import React from 'react';
import './NutrientOfDay.scss';
import BarChart from 'components/atom/BarChart/BarChart';
import { BarColor } from 'constants/color';
import NutrientInfo from 'components/atom/NutrientInfo/NutrientInfo';

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
		<div className="nutrient-main-box">
			<div className="nutrient-sub-box">
				<div className="nutrient-little-box">
					<p>탄수화물</p>
					<div className="nutrient">
						<BarChart barcolor={BarColor.Null} total={data.totalTan} value={data.tan} />
						<NutrientInfo total={data.tan} value={data.totalTan} />
					</div>
				</div>
				<div className="nutrient-little-box">
					<p>단백질</p>
					<div className="nutrient">
						<BarChart barcolor={BarColor.Null} total={data.totalDan} value={data.dan} />
						<NutrientInfo total={data.dan} value={data.totalDan} />
					</div>
				</div>
				<div className="nutrient-little-box">
					<p>지방</p>
					<div className="nutrient">
						<BarChart barcolor={BarColor.Null} total={data.totalJi} value={data.ji} />
						<NutrientInfo total={data.ji} value={data.totalJi} />
					</div>
				</div>
			</div>
		</div>
	);
}

export default NutrientOfDay;
