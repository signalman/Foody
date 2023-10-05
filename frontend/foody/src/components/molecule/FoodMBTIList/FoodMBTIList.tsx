import FoodMBTIButton from 'components/atom/FoodMBTIButton/FoodMBTIButton';
import wearylCat from 'assets/icons/weary-cat.png';
import normalCat from 'assets/icons/normal-cat.png';
import loveCat from 'assets/icons/love-cat.png';
import React, { Dispatch, SetStateAction, useEffect, useState } from 'react';
import './FoodMBTIList.scss';

interface FoodMBTIListProps {
	idx: number;
	setIdx: Dispatch<SetStateAction<number>>;
	imageValue: (data: number[]) => void;
}

function FoodMBTIList({ imageValue, idx, setIdx }: FoodMBTIListProps) {
	const [images, setImages] = useState<number[]>([]);

	const handleIndex = () => {
		let index = idx + 1;
		if (index >= 28) index = 28;
		setIdx(index);
	};

	const weary = () => {
		handleIndex();
		setImages((prevImages) => [...prevImages, 0]);
	};

	const normal = () => {
		handleIndex();
		setImages((prevImages) => [...prevImages, 1]);
	};

	const love = () => {
		handleIndex();
		setImages((prevImages) => [...prevImages, 2]);
	};

	useEffect(() => {
		if (images.length === 28) {
			imageValue(images);
		}
	});
	return (
		<div className="fbti-box">
			<FoodMBTIButton buttonClick={weary} imgsrc={wearylCat} value="별로에요" />
			<FoodMBTIButton buttonClick={normal} imgsrc={normalCat} value="보통이에요" />
			<FoodMBTIButton buttonClick={love} imgsrc={loveCat} value="좋아요" />
		</div>
	);
}

export default FoodMBTIList;
