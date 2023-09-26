import React, { InputHTMLAttributes } from 'react';
import './UnderlineInput.scss';

interface UnderlineInputProps extends InputHTMLAttributes<HTMLInputElement> {
	value: string;
	maxlength: number;
	onChangeValue: (value: string) => void;
	isValid: (value: string) => boolean;
}

interface PlaceholderProps {
	placeholder: string;
}

interface UnitProps {
	unit: string;
}

function UnderlineInput({
	value,
	placeholder,
	onChangeValue,
	unit,
	isValid,
	maxlength,
}: UnderlineInputProps & PlaceholderProps & UnitProps) {
	const handleInputChange = (event: React.ChangeEvent<HTMLInputElement>) => {
		if (isValid(event.target.value)) {
			onChangeValue(event.target.value);
		}
	};

	return (
		<div className="input-container">
			<input type="text" maxLength={maxlength} value={value} onChange={handleInputChange} required />
			<div className="labelline">{placeholder}</div>
			<div className={unit}>{unit}</div>
		</div>
	);
}

export default UnderlineInput;
