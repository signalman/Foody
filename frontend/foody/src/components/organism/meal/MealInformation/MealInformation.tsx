import React, { Dispatch } from 'react';
import './MealInformation.scss';
import DayofMealPart from 'components/molecule/DayofMealPart/DayofMealPart';
import { Meal } from 'types/meal';
import { breakfastState, dinnerState, lunchState, snackState } from 'recoil/atoms/nutrientState';
import { useRecoilValue } from 'recoil';

interface DailyMeals {
	breakfast: Meal;
	lunch: Meal;
	dinner: Meal;
	snack: Meal;
	setDetailOpen: Dispatch<React.SetStateAction<boolean>>;
	setSearchOpen: Dispatch<React.SetStateAction<boolean>>;
	setMeal: Dispatch<React.SetStateAction<string>>;
}

function MealInformation({ setDetailOpen, setSearchOpen, setMeal, breakfast, lunch, dinner, snack }: DailyMeals) {
	const breakState = useRecoilValue(breakfastState);
	const lunState = useRecoilValue(lunchState);
	const dinState = useRecoilValue(dinnerState);
	const snaState = useRecoilValue(snackState);

	return (
		<div className="meal-information-box">
			<p className="meal-information-title">일일 식단 정보</p>
			<div className="meal-information">
				<DayofMealPart
					setMeal={setMeal}
					setDetailOpen={setDetailOpen}
					setSearchOpen={setSearchOpen}
					imgsrc={breakfast.imgUrl}
					goal={breakState.energy}
					meal="아침"
					value={breakfast.total.energy}
				/>
				<DayofMealPart
					setMeal={setMeal}
					setDetailOpen={setDetailOpen}
					setSearchOpen={setSearchOpen}
					imgsrc={lunch.imgUrl}
					goal={lunState.energy}
					meal="점심"
					value={lunch.total.energy}
				/>
				<DayofMealPart
					setMeal={setMeal}
					setDetailOpen={setDetailOpen}
					setSearchOpen={setSearchOpen}
					imgsrc={dinner.imgUrl}
					goal={dinState.energy}
					meal="저녁"
					value={dinner.total.energy}
				/>
				<DayofMealPart
					setMeal={setMeal}
					setDetailOpen={setDetailOpen}
					setSearchOpen={setSearchOpen}
					imgsrc={snack.imgUrl}
					goal={snaState.energy}
					meal="간식"
					value={snack.total.energy}
				/>
			</div>
		</div>
	);
}

export default MealInformation;
