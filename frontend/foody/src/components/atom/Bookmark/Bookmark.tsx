import React, { useState } from 'react';
import { AiFillStar, AiOutlineStar } from 'react-icons/ai';
import { setBookmark } from 'utils/api/recipe';

interface BookmarkProps {
	isBookmarked: boolean;
	id: number;
	handleChange?: () => void;
	size?: number;
	color?: string;
}

function Bookmark({ isBookmarked, id, handleChange, size, color }: BookmarkProps) {
	const [isMarked, setIsMarked] = useState<boolean>(isBookmarked);

	const handleBookmark = () => {
		setBookmark(id).then(() => {
			setIsMarked(!isMarked);
			if (handleChange) handleChange();
		});
	};

	return (
		<button type="button" onClick={handleBookmark}>
			{isMarked ? <AiFillStar size={size} color={color} /> : <AiOutlineStar size={size} color={color} />}
		</button>
	);
}

export default Bookmark;

Bookmark.defaultProps = {
	size: 20,
	color: '#ffde1a',
	handleChange: () => {},
};
