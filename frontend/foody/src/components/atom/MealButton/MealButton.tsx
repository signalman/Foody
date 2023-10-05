import React, { ButtonHTMLAttributes } from 'react';
import './MealButton.scss';
import { AiOutlinePlus } from 'react-icons/ai';

interface MealButtonProps extends ButtonHTMLAttributes<HTMLButtonElement> {
	buttonClick: () => void;
	imgsrc: string;
	value: number;
}

function MealButton({ buttonClick, imgsrc, value }: MealButtonProps) {
	const imageUrl = imgsrc
		? `https://please-success-foody.s3.ap-northeast-2.amazonaws.com/${imgsrc}`
		: 'https://please-success-foody.s3.ap-northeast-2.amazonaws.com/meal/mealNullImage.png';
	const content = value > 0 ? <img src={imageUrl} alt="" /> : <AiOutlinePlus size={24} className="meal-button-icon" />;

	return (
		<button className="meal-button" type="button" onClick={buttonClick}>
			{content}
		</button>
	);
}

export default MealButton;
