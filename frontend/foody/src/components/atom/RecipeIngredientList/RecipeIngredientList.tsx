import React from 'react';
import './RecipeIngredientList.scss';
import { HiOutlineChevronRight } from 'react-icons/hi';
import RecipeIngredientItem from '../RecipeIngredientItem/RecipeIngredientItem';

function RecipeIngredientList({ ingredient, servers, difficulty }: RecipeType) {
	const handleIngredientsDelete = () => {
		console.log('레시피 관련 재료 삭제');
	};

	return (
		<div className="recipe-ingredient-list-container">
			<h3>필요한 재료</h3>
			<div className="desc">
				총 {ingredient.length}개, {servers}인분 기준, {difficulty}
			</div>
			<div className="list-wrap">
				<ul>
					{ingredient.map((item) => (
						<RecipeIngredientItem key={item.name} name={item.name} unit={item.unit} />
					))}
				</ul>
				<button type="button" onClick={handleIngredientsDelete}>
					해당 레시피로 요리하셨나요? <HiOutlineChevronRight size={14} />
				</button>
			</div>
		</div>
	);
}

export default RecipeIngredientList;
