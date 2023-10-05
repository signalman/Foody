import React from 'react';
import './RecipeIngredientItem.scss';

function RecipeIngredientItem({ name, unit }: RecipeIngredient) {
	return (
		<li className="recipe-ingredient-item-container">
			<span>{name}</span>
			<span>{unit}</span>
		</li>
	);
}

export default RecipeIngredientItem;
