import React, { Dispatch } from 'react';
import './SignupGenderItem.scss';

interface SignupGenderProps {
	imgsrc: string;
	gender: string;
	setTest: Dispatch<React.SetStateAction<string>>;
	isSelected: boolean;
}

function SignupGenderItem({ imgsrc, gender, setTest, isSelected }: SignupGenderProps) {
	const handleCheck = (select: string) => {
		setTest(select);
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
