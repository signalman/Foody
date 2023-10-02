import React from 'react';
import './RecipeInfo.scss';
import { Link } from 'react-router-dom';
import { HiOutlineChevronRight } from 'react-icons/hi';
import Bookmark from '../../atom/Bookmark/Bookmark';

interface RecipeInfoProps extends RecipeInfoType {
	energy: number;
}

function RecipeInfo({ id, name, url, energy, isBookmarked }: RecipeInfoProps) {
	return (
		<div className="recipe-info-container">
			<div className="img-wrap">
				<img src={url} alt="" />
			</div>
			<div className="recipe-info-list">
				<div className="title">
					<h2>{name}</h2>
					<span className="kcal">{energy}kcal</span>
				</div>
				<Bookmark isBookmarked={isBookmarked} id={id} />
			</div>
			<Link to={`https://www.10000recipe.com/recipe/${id}`}>
				<span>레시피 보러 가기</span>
				<HiOutlineChevronRight size={14} />
			</Link>
		</div>
	);
}

export default RecipeInfo;
