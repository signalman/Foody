import React, { ButtonHTMLAttributes } from 'react';
import './LargeButton.scss';
import classNames from 'classnames';
import LargeButtonColor from 'constants/color';

interface BottomBtnProps extends ButtonHTMLAttributes<HTMLButtonElement> {
	value?: string;
	buttonColor?: LargeButtonColor;
}

function LargeButton({ value, buttonColor }: BottomBtnProps) {
	return (
		<button type="button" className={classNames('button-value', buttonColor)}>
			<img src="" alt="" /> {value}
		</button>
	);
}

export default LargeButton;

LargeButton.defaultProps = {
	value: '',
	buttonColor: LargeButtonColor.Green,
};
