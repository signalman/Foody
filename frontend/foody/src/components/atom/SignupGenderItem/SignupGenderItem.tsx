import React, { Dispatch } from 'react';
import './SignupGenderItem.scss';

interface SignupGenderProps {
	imgsrc: string;
	gender: string;
	setTest: Dispatch<React.SetStateAction<number>>;
	isSelected: boolean;
}

function SignupGenderItem({ imgsrc, gender, setTest, isSelected }: SignupGenderProps) {
	const handleCheck = (select: string) => {
		if (select === '여성') {
			setTest(2);
		} else {
			setTest(1);
		}
	};

	return (
		<div className="signup-gender-container">
			<button type="button" onClick={() => handleCheck(gender)} className={isSelected ? 'selected' : 'unselected'}>
				<img src={imgsrc} alt="" />
				<p>{gender}</p>
			</button>
		</div>
	);
}

export default SignupGenderItem;
