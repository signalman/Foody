import React from 'react';
import './ImgCount.scss';

interface ImgCountProps {
	value: number;
	total: number;
}

function ImgCount({ value, total }: ImgCountProps) {
	return (
		<div className="imgcount-box">
			<p>
				{value}/{total}
			</p>
		</div>
	);
}

export default ImgCount;
