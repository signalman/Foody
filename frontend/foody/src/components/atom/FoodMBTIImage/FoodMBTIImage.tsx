import React, { useEffect, useState } from 'react';
import './FoodMBTIImage.scss';

interface FoodMBTIImageProps {
	images: string[];
	idx: number;
}

function FoodMBTIImage({ images, idx }: FoodMBTIImageProps) {
	const imgSrc = images[idx];
	const [imgUrl, setImgUrl] = useState<string>('');
	const [dishname, setDishName] = useState<string>('');

	useEffect(() => {
		if (imgSrc !== undefined) {
			setDishName(imgSrc[0]);
			setImgUrl(imgSrc[1]);
		}
	}, [imgSrc]);

	return (
		<div className="food-mbti-image-container">
			<p>{dishname}</p>
			<img src={imgUrl} alt="" />
		</div>
	);
}

export default FoodMBTIImage;
