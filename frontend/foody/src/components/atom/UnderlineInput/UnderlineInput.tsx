import React, { InputHTMLAttributes } from 'react';
import './UnderlineInput.scss';

interface UnderlineInputProps extends InputHTMLAttributes<HTMLInputElement> {
	value: string;
	onChangeValue: (value: string) => void;
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
}: UnderlineInputProps & PlaceholderProps & UnitProps) {
	const handleInputChange = (event: React.ChangeEvent<HTMLInputElement>) => {
		onChangeValue(event.target.value);
	};

	return (
		<div className="input-container">
			<input type="text" value={value} onChange={handleInputChange} required />
			<div className="labelline">{placeholder}</div>
			<div className={unit}>{unit}</div>
		</div>
	);
}

export default UnderlineInput;
