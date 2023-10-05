import React, { InputHTMLAttributes, useEffect, useState } from 'react';
import './InputSearch.scss';
import { AiOutlineSearch } from 'react-icons/ai';

interface InputSearchProps extends InputHTMLAttributes<HTMLInputElement> {
	value: string;
	onChangeValue: (value: string) => void;
}

function InputSearch({ value, onChangeValue, ...rest }: InputSearchProps) {
	const [inputText, setInputText] = useState<string>('');

	const handleInputChange = (event: React.ChangeEvent<HTMLInputElement>) => {
		setInputText(event.target.value);
	};

	useEffect(() => {
		const debounceTimeout = setTimeout(() => {
			onChangeValue(inputText);
		}, 300);

		return () => {
			clearTimeout(debounceTimeout);
		};
	}, [inputText, onChangeValue]);

	const handleKeyboardInputChange = (event: React.KeyboardEvent<HTMLInputElement>) => {
		const inputValue: string = event.currentTarget.value;
		onChangeValue(inputValue);
	};

	return (
		<div className="search-input-container">
			<input
				type="text"
				{...rest}
				value={inputText}
				onChange={handleInputChange}
				onKeyDown={handleKeyboardInputChange}
			/>
			<button type="button" onClick={() => onChangeValue(inputText)}>
				<AiOutlineSearch size={20} className="search-icon" />
			</button>
		</div>
	);
}

export default InputSearch;
