import React from 'react';
import './MealSearchResultList.scss';

interface MealSearchResultListProps {
	searchKeyword: string;
	searchResultList: string[] | null;
	handleMealSelect: (data: string) => void;
}

function MealSearchResultList({ searchKeyword, searchResultList, handleMealSelect }: MealSearchResultListProps) {
	return (
		<div className="meal-search-result-list-conatainer">
			<h3>검색된 식단</h3>
			<ul className="meal-search-result-list">
				{searchResultList && searchResultList.length !== 0 ? (
					<>
						{searchResultList.map((item) => (
							<div className="meal-search-result-item">
								<p>{item}</p>
								<button type="button" onClick={() => handleMealSelect(item)}>
									추가
								</button>
							</div>
						))}
					</>
				) : (
					<div className="no-search-meal">
						{searchKeyword !== '' ? `'${searchKeyword}'에 대한 검색 결과가 없습니다` : '등록할 재료를 검색해 보세요!'}
					</div>
				)}
			</ul>
		</div>
	);
}

export default MealSearchResultList;
