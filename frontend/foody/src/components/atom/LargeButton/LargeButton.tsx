import React, { ButtonHTMLAttributes, ReactNode, createElement } from 'react';
import './LargeButton.scss';
import classNames from 'classnames';
import LargeButtonColor from 'constants/color';
import { IconBaseProps, IconType } from 'react-icons/lib';

interface BottomBtnProps extends ButtonHTMLAttributes<HTMLButtonElement> {
	value?: string;
	buttonColor?: LargeButtonColor;
	buttonClick: () => void;
}

interface ImgProps {
	imgsrc?: string;
	icon?: IconType;
	iconSize?: number;
}

function renderIcon(icon: IconType, size: number): ReactNode {
	return createElement(icon, { size } as IconBaseProps); // 아이콘을 ReactNode으로 래핑합니다.
}

function LargeButton({ value, buttonColor, buttonClick, imgsrc, icon, iconSize }: BottomBtnProps & ImgProps) {
	return (
		<button type="button" onClick={buttonClick} className={classNames('button-value', buttonColor)}>
			{imgsrc && imgsrc.length > 0 && <img className="img-size" src={imgsrc} alt="" />}
			{icon && iconSize && renderIcon(icon, iconSize)}
			{value}
		</button>
	);
}

export default LargeButton;

LargeButton.defaultProps = {
	value: '',
	buttonColor: LargeButtonColor.Green,
	imgsrc: '',
	icon: undefined,
	iconSize: 15,
};
