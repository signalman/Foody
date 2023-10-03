import DetailMeal from 'components/molecule/DetailMeal/DetailMeal';
import MealCalendar from 'components/organism/meal/MealCalendar/MealCalendar';
import MealInformation from 'components/organism/meal/MealInformation/MealInformation';
import MealSearch from 'components/organism/meal/MealSearch/MealSearch';
import MealTable from 'components/organism/meal/MealTable/MealTable';
import NutrientOfDay from 'components/organism/meal/NutrientOfDay/NutrientOfDay';
import MealTemplate from 'components/template/MealTemplate/MealTemplate';
import React, { useEffect, useState } from 'react';
import getDayofMeal from 'utils/api/meal';

const data = {
	total: {
		energy: 0,
		carbohydrates: 0,
		protein: 0,
		dietaryFiber: 0,
		calcium: 0,
		sodium: 0,
		iron: 0,
		fats: 0,
		vitaminA: 0,
		vitaminC: 0,
	},
	foods: [],
	imgUrl: '',
	time: '',
};

function MealPage() {
	const [selectedDate, setSelectedDate] = useState(new Date());
	const [displayMonth, setDisplayMonth] = useState(new Date());
	const [breakfast, setBreakfast] = useState(data);
	const [lunch, setLunch] = useState(data);
	const [dinner, setDinner] = useState(data);
	const [snack, setSnack] = useState(data);
	const [total, setTotal] = useState(data.total);

	const [getDate, setGetDate] = useState<string>('');

	const [detailOpen, setDetailOpen] = useState<boolean>(false);
	const [searchOpen, setSearchOpen] = useState<boolean>(false);
	const [meal, setMeal] = useState<string>('');

	useEffect(() => {
		const dateObject = new Date(selectedDate);

		const year = dateObject.getFullYear();
		const month = String(dateObject.getMonth() + 1).padStart(2, '0');
		const day = String(dateObject.getDate()).padStart(2, '0');

		const sendDate = `${year}-${month}-${day}`;
		setGetDate(sendDate);

		getDayofMeal(sendDate).then((response) => {
			setBreakfast(response.data.breakfast);
			setLunch(response.data.lunch);
			setDinner(response.data.dinner);
			setSnack(response.data.snack);
			setTotal(response.data.total);
		});
		console.log(selectedDate);

		setDisplayMonth(selectedDate);
	}, [selectedDate]);
	console.log(breakfast);

	if (searchOpen === true && meal) {
		return <MealSearch setOpen={setSearchOpen} meal={meal} selectedDate={getDate} />;
	}

	if (detailOpen === true && meal) {
		return <DetailMeal />;
	}

	return (
		<MealTemplate>
			<MealCalendar
				selectedDate={selectedDate}
				setSelectedDate={setSelectedDate}
				displayMonth={displayMonth}
				setDisplayMonth={setDisplayMonth}
			/>
			<MealTable total={total} breakfast={breakfast} lunch={lunch} dinner={dinner} snack={snack} />
			<NutrientOfDay total={total} />
			<MealInformation
				setSearchOpen={setSearchOpen}
				setDetailOpen={setDetailOpen}
				setMeal={setMeal}
				breakfast={breakfast}
				lunch={lunch}
				dinner={dinner}
				snack={snack}
			/>
		</MealTemplate>
	);
}

export default MealPage;
