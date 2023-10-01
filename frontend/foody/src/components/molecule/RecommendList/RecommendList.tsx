import React, { WheelEvent, useRef } from 'react';
import './RecommendList.scss';
import { BsChevronRight } from 'react-icons/bs';
import { Link } from 'react-router-dom';

interface RecommendListProps {
	title: string;
	to: string;
	list: RecommendItem[];
}

function RecommendList({ title, to, list }: RecommendListProps) {
	const containerRef = useRef<HTMLUListElement | null>(null);

	const handleScroll = (e: WheelEvent<HTMLUListElement>) => {
		const container = containerRef.current;
		if (container) {
			container.scrollLeft += e.deltaY;
		}
	};

	return (
		<div className="recommend-list-container">
			<div className="recommend-list-header">
				<h2>{title}</h2>
				<Link to={to}>
					<span>더보기</span>
					<BsChevronRight size={14} />
				</Link>
			</div>
			<div className="recommend-list-body">
				<ul className="recipe-list slider-container no-scrollbar" onWheel={handleScroll} ref={containerRef}>
					{list.map((item) => (
						<li key={item.id}>
							<Link to="/recipe/1">
								<div className="info-wrap">
									<img src={item.url} alt="" />
									<div className="title">{item.name}</div>
								</div>
							</Link>
						</li>
					))}
				</ul>
			</div>
		</div>
	);
}

export default RecommendList;
