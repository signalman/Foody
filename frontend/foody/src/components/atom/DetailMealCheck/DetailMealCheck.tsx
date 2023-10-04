import React from 'react';
import { RiDeleteBinFill } from 'react-icons/ri';
import './DetailMealCheck.scss';

interface DetailMealCheckProps {
	onDelete: () => void;
}
function DetailMealCheck({ onDelete }: DetailMealCheckProps) {
	return (
		<div className="detail-meal-check-container">
			<p>해당 음식이 아닌가요?</p>
			<button type="button" onClick={onDelete} className="detail-meal-check-btn">
				<RiDeleteBinFill size={10} />
				<p>삭제하기</p>
			</button>
		</div>
	);
}

export default DetailMealCheck;
