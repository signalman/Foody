import React, { ReactNode } from 'react';
import './FloatingLayout.scss';
import classNames from 'classnames';

interface FloatingLayoutProps {
	children: ReactNode;
}

function FloatingLayout({ children, ...rest }: FloatingLayoutProps) {
	const positionClasses = classNames('floating-layout', 'floating-bg');
	return (
		<div className={positionClasses} {...rest}>
			{children}
		</div>
	);
}

export default FloatingLayout;
