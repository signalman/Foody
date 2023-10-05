import React from 'react';
import './MealDetailNutrient.scss';
import NutrientUnderline from 'components/atom/NutrientUnderline/NutrientUnderline';
import { NutrientTotal } from 'types/meal';
import CustomBottomSheet from 'components/molecule/CustomBottomSheet/CustomBottomSheet';
import Layout from 'components/template/Layout/Layout';
import LayoutBottomMargin, { LayoutTopMargin } from 'constants/Margin';
import LayoutPadding from 'constants/Padding';

interface MealDetailNutrientProps {
	open: boolean;
	setOpen: React.Dispatch<React.SetStateAction<boolean>>;
	name: string;
	nutrient: NutrientTotal;
}
function MealDetailNutrient({ open, setOpen, name, nutrient }: MealDetailNutrientProps) {
	return (
		<CustomBottomSheet
			open={open}
			setOpen={() => {
				setOpen(false);
			}}
		>
			<Layout marginBottom={LayoutBottomMargin.mb10} marginTop={LayoutTopMargin.mt30} padding={LayoutPadding.p30}>
				<div className="meal-detail-nutrient-box">
					<p>{name}</p>
					<p className="meal-detail-nutrient-value">{nutrient.energy}kcal</p>
					<div className="meal-detail-nutrient-content">
						<NutrientUnderline iswhite={false} title="탄수화물" value={nutrient.carbohydrates} unit="g" />
						<NutrientUnderline iswhite={false} title="단백질" value={nutrient.protein} unit="g" />
						<NutrientUnderline iswhite={false} title="지방" value={nutrient.fats} unit="g" />
						<NutrientUnderline iswhite={false} title="식이섬유" value={nutrient.dietaryFiber} unit="g" />
						<NutrientUnderline iswhite={false} title="칼슘" value={nutrient.calcium} unit="mg" />
						<NutrientUnderline iswhite={false} title="나트륨" value={nutrient.sodium} unit="mg" />
						<NutrientUnderline iswhite={false} title="철분" value={nutrient.iron} unit="mg" />
						<NutrientUnderline iswhite={false} title="비타민A" value={nutrient.vitaminA} unit="ug" />
						<NutrientUnderline iswhite={false} title="비타민C" value={nutrient.vitaminC} unit="mg" />
					</div>
				</div>
			</Layout>
		</CustomBottomSheet>
	);
}

export default MealDetailNutrient;
