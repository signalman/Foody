import React, { HTMLAttributes, ReactNode } from 'react';
import './Layout.scss';
import classNames from 'classnames';
import LayoutPadding from 'constants/Padding';

interface LayoutProps extends HTMLAttributes<HTMLDivElement> {
	children: ReactNode;
	padding?: LayoutPadding;
}

function Layout({ children, padding, ...rest }: LayoutProps) {
	return (
		<div className={classNames('layout', padding)} {...rest}>
			{children}
		</div>
	);
}

export default Layout;

Layout.defaultProps = {
	padding: LayoutPadding.full,
};
