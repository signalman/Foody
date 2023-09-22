import MealCalendar from 'components/organism/meal/MealCalendar/MealCalendar';
import MealInformation from 'components/organism/meal/MealInformation/MealInformation';
import MealTable from 'components/organism/meal/MealTable/MealTable';
import NutrientOfDay from 'components/organism/meal/NutrientOfDay/NutrientOfDay';
import MealTemplate from 'components/template/MealTemplate/MealTemplate';
import React from 'react';

function MealPage() {
	return (
		<MealTemplate>
			<MealCalendar />
			<MealTable />
			<NutrientOfDay />
			<MealInformation />
		</MealTemplate>
	);
}

export default MealPage;
