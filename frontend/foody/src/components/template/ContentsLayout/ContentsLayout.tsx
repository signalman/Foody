import React, { HTMLAttributes, ReactNode } from 'react';
import './ContentsLayout.scss';
import LayoutBottomMargin, { LayoutTopMargin } from 'constants/Margin';
import classNames from 'classnames';

interface ContentsLayoutProps extends HTMLAttributes<HTMLDivElement> {
	children: ReactNode;
	marginTop?: LayoutTopMargin;
	marginBottom?: LayoutBottomMargin;
}

function ContentsLayout({ children, marginTop, marginBottom, ...rest }: ContentsLayoutProps) {
	return (
		<div className={classNames('contents-layout-container', marginTop, marginBottom)} {...rest}>
			{children}
		</div>
	);
}

export default ContentsLayout;

ContentsLayout.defaultProps = {
	marginTop: LayoutTopMargin.mt0,
	marginBottom: LayoutBottomMargin.mb30,
};
