import React from 'react';
import ContentsLayout from 'components/template/ContentsLayout/ContentsLayout';
import LayoutBottomMargin, { LayoutTopMargin } from 'constants/Margin';
import CustomBottomSheet from '../CustomBottomSheet/CustomBottomSheet';
import NutrientBar from '../NutrientBar/NutrientBar';
import './DayofTotalNutrient.scss';

interface DayofTotalNutrientProps {
	open: boolean;
	setOpen: React.Dispatch<React.SetStateAction<boolean>>;
	mealNutrient: { [key: string]: number };
	mealMinerals: { [key: string]: number };
	mealVitamin: { [key: string]: number };
}

function DayofTotalNutrient({ open, setOpen, mealNutrient, mealMinerals, mealVitamin }: DayofTotalNutrientProps) {
	return (
		<CustomBottomSheet
			open={open}
			setOpen={() => {
				setOpen(false);
			}}
		>
			<div className="day-of-total-nutrient-content">
				<ContentsLayout marginTop={LayoutTopMargin.mt20} marginBottom={LayoutBottomMargin.mb30}>
					<NutrientBar isempty mealNutrient={mealNutrient} title="영양소" />
				</ContentsLayout>
				<ContentsLayout marginTop={LayoutTopMargin.mt20} marginBottom={LayoutBottomMargin.mb30}>
					<NutrientBar isempty mealNutrient={mealMinerals} title="다량 무기질" />
				</ContentsLayout>
				<ContentsLayout marginTop={LayoutTopMargin.mt20} marginBottom={LayoutBottomMargin.mb0}>
					<NutrientBar isempty mealNutrient={mealVitamin} title="비타민" />
				</ContentsLayout>
			</div>
		</CustomBottomSheet>
	);
}

export default DayofTotalNutrient;
