import React from 'react';
import './IngredientsListItem.scss';
import { RefricategoryItem } from 'constants/category';

interface IngredientsListItemProps {
	idx: number;
	handleClick: (idx: number) => void;
	item: RefricategoryItem;
}

function IngredientsListItem({ idx, handleClick, item }: IngredientsListItemProps) {
	return (
		<li className="ingredients-list-item-container">
			<button type="button" onClick={() => handleClick(idx)}>
				<div className="desc-wrap">
					<img src={item.img} alt="" />
					<p>{item.text}</p>
				</div>
			</button>
		</li>
	);
}

export default IngredientsListItem;
