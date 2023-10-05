import React, { useEffect, useState } from 'react';
import './BookmarkList.scss';
import { BookmarkItemType } from 'types/receipt';
import { getBookmarkList } from 'utils/api/recipe';
import { Link } from 'react-router-dom';
import { HiOutlineChevronRight } from 'react-icons/hi';
import BookmarkItem from '../BookmarkItem/BookmarkItem';

function BookmarkList() {
	const [bookmarkList, setBookmarkList] = useState<BookmarkItemType[] | null>(null);

	const getData = () => {
		getBookmarkList().then((res) => {
			setBookmarkList(res.data);
		});
	};

	useEffect(() => {
		if (!bookmarkList) {
			getData();
		}
	}, [bookmarkList]);

	return (
		<div className="bookmark-list-container">
			<h2>북마크 레시피</h2>
			<div className="list-wrap">
				<ul>
					{bookmarkList && bookmarkList.length !== 0 ? (
						bookmarkList.map((item) => <BookmarkItem key={item.id} item={item} handleChange={getData} />)
					) : (
						<div className="no-list">
							<p>북마크된 레시피가 없습니다.</p>
							<Link to="/recommend">
								<div>레시피 보러 가기</div>
								<HiOutlineChevronRight size={14} />
							</Link>
						</div>
					)}
				</ul>
			</div>
		</div>
	);
}

export default BookmarkList;
