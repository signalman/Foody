import React from 'react';
import './DetailImg.scss';

interface DetailImgProps {
	imgsrc: string;
}

function DetailImg({ imgsrc }: DetailImgProps) {
	return (
		<div className="detail-img-container">
			{imgsrc ? (
				<img src={`https://please-success-foody.s3.ap-northeast-2.amazonaws.com/${imgsrc}`} alt="" />
			) : (
				<img src="https://please-success-foody.s3.ap-northeast-2.amazonaws.com/meal/mealNullImage.png" alt="" />
			)}
		</div>
	);
}

export default DetailImg;
