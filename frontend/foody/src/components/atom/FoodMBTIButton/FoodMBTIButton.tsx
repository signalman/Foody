import React, { ButtonHTMLAttributes } from 'react';
import './FoodMBTIButton.scss';

interface FoodMBTIButtonProps extends ButtonHTMLAttributes<HTMLButtonElement> {
	buttonClick: () => void;
}

interface MBTIProps {
	imgsrc: string;
	value: string;
}

function FoodMBTIButton({ buttonClick, imgsrc, value }: FoodMBTIButtonProps & MBTIProps) {
	return (
		<button className="mbti-box" type="button" onClick={buttonClick}>
			<img src={imgsrc} alt="" />
			<p>{value}</p>
		</button>
	);
}

export default FoodMBTIButton;
