import React, { InputHTMLAttributes } from 'react';
import { FaStarOfLife } from 'react-icons/fa6';
import './MealInput.scss';

interface MealInputProps extends InputHTMLAttributes<HTMLInputElement> {
	value: string;
	onChangeValue: (value: string) => void;
	isValid: (value: string) => boolean;
}

interface MealInputProps {
	title: string;
	requireIcon: boolean;
	placeholder: string;
}

interface UnitProps {
	unit: string;
}
function MealInput({
	title,
	isValid,
	requireIcon,
	placeholder,
	value,
	onChangeValue,
	unit,
}: MealInputProps & MealInputProps & UnitProps) {
	const handleInputChange = (event: React.ChangeEvent<HTMLInputElement>) => {
		if (isValid(event.target.value)) {
			onChangeValue(event.target.value);
		}
	};
	return (
		<div className="meal-input-box">
			<div className="meal-input-title">
				<p>{title}</p>
				{requireIcon && <FaStarOfLife className="meal-input-icon" size={8} />}
			</div>
			<div className="meal-input-sub-box">
				<input type="text" placeholder={placeholder} onChange={handleInputChange} value={value} />
				<div className={unit}>{unit}</div>
			</div>
		</div>
	);
}

export default MealInput;
