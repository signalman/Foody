import React, { useEffect, useState } from 'react';
import { CgClose } from 'react-icons/cg';
import './SelectedMealList.scss';

interface SelectedMealListProps {
	selectedMealList: string[] | null;
	onDelete: (data: string) => void;
}

function SelectedMealList({ selectedMealList, onDelete }: SelectedMealListProps) {
	const [check, setCheck] = useState<boolean>(false);

	useEffect(() => {
		setCheck(false);
	}, [check]);
	return (
		<div className="select-meal-list-container">
			<h3>선택된 식단</h3>
			{selectedMealList && selectedMealList.length !== 0 ? (
				<ul className="select-meal-list">
					{selectedMealList.map((item) => (
						<li className="select-meal-list-item">
							<span>{item}</span>
							<button
								type="button"
								onClick={() => {
									onDelete(item);
									setCheck(true);
								}}
							>
								<CgClose size={12} />
							</button>
						</li>
					))}
				</ul>
			) : (
				<div className="no-seleted-meal">선택된 재료가 없습니다</div>
			)}
		</div>
	);
}

export default SelectedMealList;
