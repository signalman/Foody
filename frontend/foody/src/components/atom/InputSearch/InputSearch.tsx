import React, { InputHTMLAttributes } from 'react';
import './InputSearch.scss';
import { AiOutlineSearch } from 'react-icons/ai';

interface InputSearchProps extends InputHTMLAttributes<HTMLInputElement> {
	value: string;
	onChangeValue: (value: string) => void;
}

function InputSearch({ value, onChangeValue, ...rest }: InputSearchProps) {
	const handleInputChange = (event: React.ChangeEvent<HTMLInputElement>) => {
		onChangeValue(event.target.value);
	};

	return (
		<div className="search-input-container">
			<input
				type="text"
				{...rest}
				value={value}
				onChange={handleInputChange}
				onKeyDown={() => {
					onChangeValue(value);
				}}
			/>
			<button type="button" onClick={() => onChangeValue(value)}>
				<AiOutlineSearch size={20} className="search-icon" />
			</button>
		</div>
	);
}

export default InputSearch;
