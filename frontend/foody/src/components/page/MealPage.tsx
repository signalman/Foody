import DetailMeal from 'components/molecule/DetailMeal/DetailMeal';
import MealCalendar from 'components/organism/meal/MealCalendar/MealCalendar';
import MealInformation from 'components/organism/meal/MealInformation/MealInformation';
import MealSearch from 'components/organism/meal/MealSearch/MealSearch';
import MealTable from 'components/organism/meal/MealTable/MealTable';
import NutrientOfDay from 'components/organism/meal/NutrientOfDay/NutrientOfDay';
import MealTemplate from 'components/template/MealTemplate/MealTemplate';
import React, { useEffect, useState } from 'react';
import { useRecoilValue } from 'recoil';
import { breakfastState, dinnerState, lunchState, snackState } from 'recoil/atoms/nutrientState';
import getDayofMeal, { recentMeal } from 'utils/api/meal';

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
	const [start, setStart] = useState<boolean>(true);

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

	const breakState = useRecoilValue(breakfastState);
	const lunState = useRecoilValue(lunchState);
	const dinState = useRecoilValue(dinnerState);
	const snaState = useRecoilValue(snackState);

	const [deleteOk, setDeleteOk] = useState<boolean>(false);

	const [nowmonth, setMonth] = useState<string>('');
	const [nowday, setDay] = useState<string>('');

	const [bookDate, setBookDate] = useState<string[]>([]);
	useEffect(() => {
		const dateObject = new Date(selectedDate);
		// year, month, day 자르기
		const year = dateObject.getFullYear();
		const month = String(dateObject.getMonth() + 1).padStart(2, '0');
		const day = String(dateObject.getDate()).padStart(2, '0');
		setMonth(month);
		setDay(day);
		// sendDate 처리
		const sendDate = `${year}-${month}-${day}`;
		setGetDate(sendDate);

		getDayofMeal(sendDate).then((response) => {
			setBreakfast(response.data.breakfast);
			setLunch(response.data.lunch);
			setDinner(response.data.dinner);
			setSnack(response.data.snack);
			setTotal(response.data.total);
		});

		setDisplayMonth(selectedDate);
		setDeleteOk(false);
	}, [selectedDate, deleteOk, meal, breakfast.foods.length, setSearchOpen, setDetailOpen]);

	useEffect(() => {
		if (start === true) {
			recentMeal().then((response) => {
				setBookDate(response.data);
			});
			setStart(false);
		}
	}, [start]);

	if (searchOpen === true && meal) {
		return <MealSearch setOpen={setSearchOpen} meal={meal} selectedDate={getDate} />;
	}

	if (detailOpen === true && meal) {
		if (meal === '아침') {
			return (
				<DetailMeal
					selectedDate={getDate}
					meal={meal}
					getData={breakfast}
					requireData={breakState}
					setDeleteOk={setDeleteOk}
					setDetailOpen={setDetailOpen}
					month={nowmonth}
					day={nowday}
				/>
			);
		}
		if (meal === '점심') {
			return (
				<DetailMeal
					selectedDate={getDate}
					meal={meal}
					getData={lunch}
					requireData={lunState}
					setDeleteOk={setDeleteOk}
					setDetailOpen={setDetailOpen}
					month={nowmonth}
					day={nowday}
				/>
			);
		}
		if (meal === '저녁') {
			return (
				<DetailMeal
					selectedDate={getDate}
					meal={meal}
					getData={dinner}
					requireData={dinState}
					setDeleteOk={setDeleteOk}
					setDetailOpen={setDetailOpen}
					month={nowmonth}
					day={nowday}
				/>
			);
		}
		if (meal === '간식') {
			return (
				<DetailMeal
					selectedDate={getDate}
					meal={meal}
					getData={snack}
					requireData={snaState}
					setDeleteOk={setDeleteOk}
					setDetailOpen={setDetailOpen}
					month={nowmonth}
					day={nowday}
				/>
			);
		}
	}

	return (
		<MealTemplate>
			<MealCalendar
				selectedDate={selectedDate}
				setSelectedDate={setSelectedDate}
				displayMonth={displayMonth}
				setDisplayMonth={setDisplayMonth}
				bookDate={bookDate}
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
