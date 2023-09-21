import React, { Dispatch, memo } from 'react';
import './PageTitle.scss';
import ToggleSwitch from 'components/atom/ToggleSwitch/ToggleSwitch';

const PageTitle = memo(
	({ title, type, setType }: { title: string; type: boolean; setType: Dispatch<React.SetStateAction<boolean>> }) => {
		return (
			<div className="page-title-container">
				<h2>{title}</h2>
				<ToggleSwitch type={type} setType={setType} onText="냉장고" offText="선반" />
			</div>
		);
	},
);

export default PageTitle;
