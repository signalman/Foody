import React from 'react';
import './IngredientSearchResultList.scss';
import { IngredientSearchItem } from 'types/refrigerator';

function IngredientSearchResultList({
	searchKeyword,
	searchResultList,
	handleIngredientSelect,
}: {
	searchKeyword: string;
	searchResultList: IngredientSearchItem[] | null;
	handleIngredientSelect: (idx: number) => void;
}) {
	return (
		<div className="ingredient-search-result-list-conatainer">
			<h3>검색된 재료</h3>
			<ul className="ingredient-search-result-list">
				{searchResultList && searchResultList.length !== 0 ? (
					<>
						{searchResultList.map((item, idx) => (
							<li key={item.key} className="ingredient-search-result-item">
								<button
									type="button"
									key={item.key}
									onClick={() => {
										handleIngredientSelect(idx);
									}}
								>
									{item.text}
								</button>
							</li>
						))}
					</>
				) : (
					<div className="no-search-ingredients">
						{searchKeyword !== '' ? `'${searchKeyword}'에 대한 검색 결과가 없습니다` : '등록할 재료를 검색해 보세요!'}
					</div>
				)}
			</ul>
		</div>
	);
}

export default IngredientSearchResultList;
