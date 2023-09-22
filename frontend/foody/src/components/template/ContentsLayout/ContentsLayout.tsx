import React, { HTMLAttributes, ReactNode } from 'react';
import './ContentsLayout.scss';
import LayoutBottomMargin, { LayoutLeftRightMargin, LayoutTopMargin } from 'constants/Margin';
import classNames from 'classnames';

interface ContentsLayoutProps extends HTMLAttributes<HTMLDivElement> {
	children: ReactNode;
	marginTop?: LayoutTopMargin;
	marginBottom?: LayoutBottomMargin;
	marginLR?: LayoutLeftRightMargin;
}

function ContentsLayout({ children, marginTop, marginBottom, marginLR, ...rest }: ContentsLayoutProps) {
	return (
		<div className={classNames('contents-layout-container', marginTop, marginBottom, marginLR)} {...rest}>
			{children}
		</div>
	);
}

export default ContentsLayout;

ContentsLayout.defaultProps = {
	marginTop: LayoutTopMargin.mt0,
	marginBottom: LayoutBottomMargin.mb30,
	marginLR: LayoutLeftRightMargin.m0,
};
