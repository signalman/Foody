import React from 'react';
import './IngredientsList.scss';
import { IngridientItem } from 'constants/category';
import formatDate from 'utils/common/formatDate';
import classNames from 'classnames';
import IngredientsListItem from '../IngredientsListItem/IngredientsListItem';

function IngredientsList({ ingredientsList, type }: { ingredientsList: IngridientItem[]; type: boolean }) {
	const containerDivClasses = classNames('ingredients-list-container', {
		'refrigerator-door': type,
		'drawer-leg': !type,
	});
	const wrapDivClasses = classNames('ingredients-list-wrap', 'no-scrollbar', {
		refrigerator: type,
		drawer: !type,
	});

	const handleClickIngredientItem = (idx: number) => {
		console.log(idx, ingredientsList[idx], formatDate(ingredientsList[idx].regiDate));
	};

	return (
		<div className={containerDivClasses}>
			<div className={wrapDivClasses}>
				<ul className="ingredients-list">
					{ingredientsList.map((item, i) => (
						<IngredientsListItem key={item.key} idx={i} handleClick={handleClickIngredientItem} item={item} />
					))}
				</ul>
			</div>
		</div>
	);
}

export default IngredientsList;
