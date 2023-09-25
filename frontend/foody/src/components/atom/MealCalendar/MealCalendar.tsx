import React, { useEffect, useState } from 'react';
import { subWeeks, addWeeks, startOfWeek, endOfWeek, isWithinInterval } from 'date-fns';
import { DayPicker, RowProps, Row } from 'react-day-picker';
import { ko } from 'date-fns/locale';
import 'react-day-picker/dist/style.css';
import './MealCalendar.scss';
import { HiOutlineChevronLeft, HiOutlineChevronRight } from 'react-icons/hi';

const isDays = [new Date(2023, 8, 23)];
const isDaysStyle = { backgroundColor: '#1aa274', color: '#ffffff' };

function PresentWeek({ dates, displayMonth }: RowProps, selectedDate: Date) {
	const isCurrentMonth = displayMonth.getMonth() + 1;
	return (
		<Row
			dates={
				isCurrentMonth
					? dates.filter((date) =>
							isWithinInterval(date, { start: startOfWeek(selectedDate), end: endOfWeek(selectedDate) }),
					  )
					: []
			}
			displayMonth={displayMonth}
			weekNumber={1}
		/>
	);
}

function MealCalendar() {
	const [selectedDate, setSelectedDate] = useState(new Date());
	const [displayMonth, setDisplayMonth] = useState(new Date());
	// const [booked, setBooked] = React.useState(false);

	const handleDayClick = (date: any) => {
		setSelectedDate(date);
	};

	const goToPreviousWeek = () => {
		setSelectedDate(subWeeks(selectedDate, 1));
	};

	const goToNextWeek = () => {
		const nextWeekFirstDay = addWeeks(selectedDate, 1);
		setSelectedDate(nextWeekFirstDay);
	};

	useEffect(() => {
		setDisplayMonth(selectedDate);
	}, [selectedDate]);

	const handleMonthChange = (newMonth: Date) => {
		setDisplayMonth(newMonth);
		setSelectedDate(newMonth);
	};

	return (
		<div className="calendar-box">
			<button type="button" onClick={goToPreviousWeek}>
				<HiOutlineChevronLeft size={30} />
			</button>
			<DayPicker
				showOutsideDays
				modifiers={{ booked: isDays }}
				modifiersStyles={{ booked: isDaysStyle }}
				locale={ko}
				weekStartsOn={0}
				captionLayout="dropdown"
				fromYear={2020}
				toYear={2023}
				selected={selectedDate}
				onDayClick={handleDayClick}
				onMonthChange={handleMonthChange}
				month={displayMonth}
				components={{ Row: (props) => PresentWeek(props, selectedDate) }}
			/>
			<button type="button" onClick={goToNextWeek}>
				<HiOutlineChevronRight size={30} />
			</button>
			{/* <p>선택한 주: {format(selectedDate, 'yyyy-MM-dd')}</p>
			<p>선택한 달: {format(displayMonth, 'yyyy-MM-dd')}</p> */}
		</div>
	);
}

export default MealCalendar;
