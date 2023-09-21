import React, { Dispatch } from 'react';
import './IngredientsCategory.scss';
import { RefricategoryItem } from 'constants/category';
import { MdExpandMore } from 'react-icons/md';
import classNames from 'classnames';

interface IngredientsCategoryProps {
	categoryList: RefricategoryItem[];
	selected: string;
	setSelected: Dispatch<React.SetStateAction<string>>;
}

function IngredientsCategory({ categoryList, selected, setSelected }: IngredientsCategoryProps) {
	const handleCategorySelect = (text: string) => {
		setSelected(text);
	};

	return (
		<ul className="ingredients-category-container slider-container">
			{categoryList.map((v) => (
				<li key={v.text} className={classNames(selected === v.text && 'actvie')}>
					<button
						type="button"
						onClick={() => {
							handleCategorySelect(v.text);
						}}
					>
						<div className="desc-wrap">
							<img src={v.img} alt="" />
							<h4>{v.text}</h4>
						</div>
						<MdExpandMore size={20} />
					</button>
				</li>
			))}
		</ul>
	);
}

export default IngredientsCategory;
