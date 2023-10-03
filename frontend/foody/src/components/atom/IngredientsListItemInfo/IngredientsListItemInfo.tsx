import React from 'react';
import './IngredientsListItemInfo.scss';

interface InfoList {
	title: string;
	value: string;
}

function IngredientsListItemInfo({ infoList }: { infoList: InfoList[] }) {
	return (
		<div className="ingredients-list-item-info-conatiner">
			<ul className="info-list">
				{infoList.map((info) => (
					<li className="info-list-item" key={info.title}>
						<span className="title">{info.title}</span>
						<span className="value">{info.value}</span>
					</li>
				))}
			</ul>
		</div>
	);
}

export default IngredientsListItemInfo;
