import React, { ReactNode } from 'react';
import './LoginTemplate.scss';
import LayoutPadding from 'constants/Padding';
import Layout from '../Layout/Layout';

function LoginTemplate({ children }: { children: ReactNode[] }) {
	return (
		<Layout padding={LayoutPadding.p20}>
			<div className="login-template-container">
				<div className="main-div">
					<div className="main-logo">{children[0]}</div>
				</div>
				<div className="btn-group">{children[1]}</div>
			</div>
		</Layout>
	);
}

export default LoginTemplate;
