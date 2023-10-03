import React, { ButtonHTMLAttributes } from 'react';
import './MealButton.scss';
import { AiOutlinePlus } from 'react-icons/ai';

interface MealButtonProps extends ButtonHTMLAttributes<HTMLButtonElement> {
	buttonClick: () => void;
	imgsrc: string;
}

function MealButton({ buttonClick, imgsrc }: MealButtonProps) {
	return (
		<button className="meal-button" type="button" onClick={buttonClick}>
			{imgsrc ? <img src={imgsrc} alt="" /> : <AiOutlinePlus size={24} className="meal-button-icon" />}
		</button>
	);
}

export default MealButton;
