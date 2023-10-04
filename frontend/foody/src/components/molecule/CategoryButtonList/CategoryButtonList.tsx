import React from 'react';
import './CategoryButtonList.scss';
import { ALL_CATEGORY_LIST, AllCategoryItem } from 'constants/category';
import classNames from 'classnames';

function CategoryButtonList({
	selectCategory,
	handleSelectCategory,
}: {
	selectCategory: number;
	handleSelectCategory: (id: number) => void;
}) {
	return (
		<div className="category-button-list-container">
			<div className="title-wrap">
				<h2>카테고리 선택</h2>
				<div className="desc-wrap">
					<span>냉장고</span>
					<span>선반</span>
				</div>
			</div>
			<div className="list-container">
				<ul className="button-list">
					{ALL_CATEGORY_LIST.map((item: AllCategoryItem) => (
						<li
							key={item.ingredientCategoryId}
							className={classNames(
								'item',
								item.type === 0 ? 'refri' : 'drawer',
								item.ingredientCategoryId === selectCategory && 'active',
							)}
						>
							<button type="button" onClick={() => handleSelectCategory(item.ingredientCategoryId)}>
								<div className="img-wrap">
									<img src={item.img} alt="" />
								</div>
								<h4>{item.text}</h4>
							</button>
						</li>
					))}
				</ul>
			</div>
		</div>
	);
}

export default CategoryButtonList;
