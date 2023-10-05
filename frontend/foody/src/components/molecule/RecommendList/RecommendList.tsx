import React, { WheelEvent, useCallback, useEffect, useRef, useState } from 'react';
import './RecommendList.scss';
import { Link } from 'react-router-dom';
import { FiRefreshCw } from 'react-icons/fi';
import { getRecommendList } from 'utils/api/recommend';
import RecommendListSkeleton from '../RecommendListSkeleton/RecommendListSkeleton';

interface RecommendListProps {
	index: string;
	title: string;
}

function RecommendList({ index, title }: RecommendListProps) {
	const containerRef = useRef<HTMLUListElement | null>(null);
	const [isLoading, setIsLoading] = useState<boolean>(false);
	const [data, setData] = useState<[] | RecommendItem[]>([]);

	const handleRecommendRefresh = useCallback(() => {
		setIsLoading(true);
		getRecommendList(index).then((res) => {
			if (res.data) {
				setData(res.data);
				setIsLoading(false);
			}
		});
	}, [index]);

	const handleScroll = (e: WheelEvent<HTMLUListElement>) => {
		const container = containerRef.current;
		if (container) {
			container.scrollLeft += e.deltaY;
		}
	};

	useEffect(() => {
		handleRecommendRefresh();
	}, [handleRecommendRefresh]);

	if (isLoading) {
		return <RecommendListSkeleton title={title} />;
	}

	return (
		<div className="recommend-list-container">
			<div className="recommend-list-header">
				<h2>{title}</h2>
				{index === 'hybrid' && (
					<button type="button" onClick={handleRecommendRefresh}>
						<FiRefreshCw size={12} />
					</button>
				)}
			</div>
			{index === 'ingredients' && (
				<div className="warning">※ 냉장고 재료가 3개 이하일 경우, 정확도가 떨어질 수 있습니다.</div>
			)}
			<div className="recommend-list-body">
				<ul className="recipe-list slider-container no-scrollbar" onWheel={handleScroll} ref={containerRef}>
					{index === 'ingredients' && data.length === 0 ? (
						<li className="blank-item-container">
							<span className="blank-desc">
								가지고 있는 재료를 등록하여
								<br />
								만들 수 있는 레시피를 추천 받아보세요!
							</span>
							<Link to="/refri">재료 등록하러 가기</Link>
						</li>
					) : (
						data &&
						data.map((item) => (
							<li key={item.id}>
								<Link to={`/recipe/${item.id}`}>
									<div className="info-wrap">
										<div className="img-wrap">
											<img src={item.url} alt="" />
										</div>
										<div className="title">{item.name}</div>
									</div>
								</Link>
							</li>
						))
					)}
				</ul>
			</div>
		</div>
	);
}

export default RecommendList;
