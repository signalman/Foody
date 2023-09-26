import React, { ReactNode } from 'react';
import './BottomButtonLayout.scss';

function BottomButtonLayout({ children }: { children: ReactNode }) {
	return <div className="bottom-button-layout-container">{children}</div>;
}

export default BottomButtonLayout;
