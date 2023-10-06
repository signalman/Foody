import React, { useEffect, useState } from 'react';
import './BookmarkItem.scss';
import { BookmarkItemType } from 'types/receipt';
import { getRecipeDetail } from 'utils/api/recipe';
import toast from 'react-hot-toast';
import { Link } from 'react-router-dom';
import Bookmark from '../../atom/Bookmark/Bookmark';

function BookmarkItem({ item, handleChange }: { item: BookmarkItemType; handleChange: () => void }) {
	const [data, setData] = useState<RecipeDetailDataType | null>(null);

	useEffect(() => {
		if (!data) {
			getRecipeDetail(item.id).then((res) => {
				setData(res.data);
			});
		}
	});

	return (
		<li className="bookmark-item-container">
			{data && (
				<Link className="contents-wrap" to={`/recipe/${data.id}`}>
					<div className="img-wrap">
						<img src={data.url} alt="" />
					</div>
					<div className="desc-wrap">
						<div className="desc">
							<div className="header">
								<span>{data.name}</span>
								<Bookmark
									id={data.id}
									isBookmarked={data.isBookmarked}
									size={18}
									handleChange={() => {
										handleChange();
										toast.success('북마크가 해제되었습니다.');
									}}
								/>
							</div>
							<div>
								<div className="ingredient-list">
									{data.ingredient.slice(0, 10).map((i) => (
										<span>{i.name}</span>
									))}
									{data.ingredient.length > 10 && ' ...'}
								</div>
							</div>
						</div>
						<div className="footer">
							<span className="ingre-info">필요한 재료 {data.ingredient.length}개</span>
							<span>{data.difficulty}</span>
						</div>
					</div>
				</Link>
			)}
		</li>
	);
}

export default BookmarkItem;
