import React from 'react';
import './SelectIngredientList.scss';
import { CgClose } from 'react-icons/cg';
import { IngridientSearchItem } from 'types/refrigerator';

function SelectIngredientList({
	selectedIngredientList,
	onDelete,
}: {
	selectedIngredientList: IngridientSearchItem[] | null;
	onDelete: (key: number) => void;
}) {
	return (
		<div className="select-ingredient-list-container">
			<h3>선택된 재료</h3>
			{selectedIngredientList && selectedIngredientList.length !== 0 ? (
				<ul className="select-ingredient-list">
					{selectedIngredientList.map((item) => (
						<li key={item.key} className="select-ingredient-list-item">
							<span>{item.text}</span>
							<button
								type="button"
								onClick={() => {
									onDelete(item.key);
								}}
							>
								<CgClose size={12} />
							</button>
						</li>
					))}
				</ul>
			) : (
				<div className="no-seleted-ingredients">선택된 재료가 없습니다</div>
			)}
		</div>
	);
}

export default SelectIngredientList;
