import React, { Dispatch, WheelEvent, useRef } from 'react';
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
	const containerRef = useRef<HTMLUListElement | null>(null);

	const handleScroll = (e: WheelEvent<HTMLUListElement>) => {
		const container = containerRef.current;
		if (container) {
			container.scrollLeft += e.deltaY;
		}
	};

	const handleCategorySelect = (text: string) => {
		setSelected(text);
	};

	const handleClick = (idx: number, e: React.MouseEvent<HTMLButtonElement, MouseEvent>) => {
		e.preventDefault();
		handleCategorySelect(categoryList[idx].text);
	};

	return (
		<ul className="ingredients-category-container slider-container" onWheel={handleScroll} ref={containerRef}>
			{categoryList.map((v, i) => (
				<li key={v.text} className={classNames(selected === v.text && 'active')}>
					<button type="button" onClick={(e) => handleClick(i, e)}>
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
