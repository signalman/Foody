import React, { HTMLAttributes, ReactNode } from 'react';
import './Layout.scss';
import classNames from 'classnames';
import LayoutPadding from 'constants/Padding';
import LayoutBottomMargin, { LayoutTopMargin } from 'constants/Margin';

interface LayoutProps extends HTMLAttributes<HTMLDivElement> {
	children: ReactNode;
	marginTop?: LayoutTopMargin;
	marginBottom?: LayoutBottomMargin;
	padding?: LayoutPadding;
}

function Layout({ children, marginTop, marginBottom, padding, ...rest }: LayoutProps) {
	return (
		<div className={classNames('layout', marginTop, marginBottom, padding)} {...rest}>
			{children}
		</div>
	);
}

export default Layout;

Layout.defaultProps = {
	marginTop: LayoutTopMargin.mt0,
	marginBottom: LayoutBottomMargin.mb0,
	padding: LayoutPadding.p0,
};
