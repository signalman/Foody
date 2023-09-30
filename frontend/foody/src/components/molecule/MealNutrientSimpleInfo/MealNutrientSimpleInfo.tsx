import NutrientUnderline from 'components/atom/NutrientUnderline/NutrientUnderline';
import React from 'react';
import './MealNutrientSimpleInfo.scss';
import { HiOutlineChevronRight } from 'react-icons/hi';

interface MealNutrientSimpleInfoProps {
	meal: string;
	tan: number;
	dan: number;
	ji: number;
	na: number;
}

function MealNutrientSimpleInfo({ meal, tan, dan, ji, na }: MealNutrientSimpleInfoProps) {
	return (
		<div className="meal-nutrient-simple-info-box">
			<div className="meal-nutrient-simple-info-title">
				<p>{meal} 영양소 정보</p>
				<HiOutlineChevronRight size={16} />
			</div>
			<div className="meal-nutrient-simple-info-content">
				<NutrientUnderline iswhite title="탄수화물" value={tan} unit="g" />
				<NutrientUnderline iswhite title="단백질" value={dan} unit="g" />
				<NutrientUnderline iswhite title="지방" value={ji} unit="g" />
				<NutrientUnderline iswhite title="나트륨" value={na} unit="mg" />
			</div>
		</div>
	);
}

export default MealNutrientSimpleInfo;
