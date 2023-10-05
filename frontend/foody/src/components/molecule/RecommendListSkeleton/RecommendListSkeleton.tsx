import React from 'react';
import './RecommendListSkeleton.scss';
// import { FiRefreshCw } from 'react-icons/fi';

function RecommendListSkeleton({ title }: { title: string }) {
	return (
		<div className="recommend-list-skeleton-container">
			<div className="recommend-list-header">
				<h2>{title}</h2>
				{/* <button type="button">
					<FiRefreshCw size={12} />
				</button> */}
			</div>
			<div className="recommend-list-body">
				<ul className="recipe-list slider-container no-scrollbar">
					{[1, 2, 3, 4, 5].map((idx) => (
						<li key={idx}>
							<div className="skeleton-image" />
							<div className="skeleton-text" />
						</li>
					))}
				</ul>
			</div>
		</div>
	);
}

export default RecommendListSkeleton;
