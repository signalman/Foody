import React, { useState } from 'react';
import './NutrientOfDay.scss';
import NutrientBar from 'components/molecule/NutrientBar/NutrientBar';
import { HiOutlineChevronRight } from 'react-icons/hi';
import DayofTotalNutrient from 'components/molecule/DayofTotalNutrient/DayofTotalNutrient';
import { NutrientTotal } from 'types/meal';
import { useRecoilState } from 'recoil';
import tabbarState from 'recoil/atoms/tabbarState';

interface MealTotal {
	total: NutrientTotal;
}

function NutrientOfDay({ total }: MealTotal) {
	const [, setTabbarOn] = useRecoilState(tabbarState);

	const mealNutrient = {
		탄수화물: total.carbohydrates,
		단백질: total.protein,
		지방: total.fats,
	};

	const maelMinerals = {
		식이섬유: total.dietaryFiber,
		칼슘: total.calcium,
		나트륨: total.sodium,
		철분: total.iron,
	};

	const mealVitamin = {
		비타민A: total.vitaminA,
		비타민C: total.vitaminC,
	};

	const [open, setOpen] = useState<boolean>(false);

	const openMore = () => {
		setOpen(true);
		setTabbarOn(false);
		console.log(open);
	};

	return (
		<div>
			<div className="nutirent-of-day-title-box">
				<p className="nutrient-of-day-title">일일 영양소</p>
				<button className="nutrient-of-day-button" onClick={openMore} type="button">
					<p>더보기</p>
					<HiOutlineChevronRight size={14} />
					{open && (
						<DayofTotalNutrient
							mealMinerals={maelMinerals}
							mealNutrient={mealNutrient}
							mealVitamin={mealVitamin}
							open={open}
							setOpen={setOpen}
						/>
					)}
				</button>
			</div>
			<NutrientBar isempty={false} mealNutrient={mealNutrient} title="일일 영양소" />
		</div>
	);
}

export default NutrientOfDay;
