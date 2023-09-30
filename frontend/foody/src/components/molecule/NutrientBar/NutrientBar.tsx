import React, { useState } from 'react';
import { HiOutlineChevronRight } from 'react-icons/hi';
import NutrientInformation from '../NutrientInformation/NutrientInformation';

interface NutrientBarProps {
	ismore: boolean;
	title: string;
	nutrient: { [key: string]: number };
}

function NutrientBar({ ismore, title, nutrient }: NutrientBarProps) {
	const [open, setOpen] = useState<boolean>(false);

	const openMore = () => {
		setOpen(true);
		console.log(open);
	};

	return (
		<div>
			<div className="nutirent-of-day-title-box">
				<p className="nutrient-of-day-title">{title}</p>
				{ismore && (
					<button className="nutrient-of-day-button" onClick={openMore} type="button">
						<p>더보기</p>
						<HiOutlineChevronRight size={14} />
					</button>
				)}
			</div>
			<div className="nutrient-main-box">
				{Object.keys(nutrient).map((nutrientName) => (
					<NutrientInformation
						key={nutrientName}
						nutrient={nutrientName}
						totalNutrient={300} // 여기에 영양소에 대한 적절한 기본값 또는 측정된 값 사용
						valueNutrient={nutrient[nutrientName]} // 객체의 키를 사용하여 해당 영양소의 값을 가져옵니다.
					/>
				))}
			</div>
		</div>
	);
}

export default NutrientBar;
