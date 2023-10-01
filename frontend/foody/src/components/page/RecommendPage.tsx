import React, { useEffect, useState } from 'react';
import RecommendTemplate from 'components/template/RecommendTemplate/RecommendTemplate';
import RecommendList from 'components/molecule/RecommendList/RecommendList';
import getAllRecommendList from 'utils/api/recommend';
import RecommendListSkeleton from 'components/molecule/RecommendListSkeleton/RecommendListSkeleton';
import RECOMMEND_LIST from 'constants/recommend';

function RecommendPage() {
	// const [searchKeyword, setSearchKeyword] = useState<string>('');
	const [isLoading, setIsLoading] = useState<boolean[]>([true, true, true]);
	const [recommendDatas, setRecommendDatas] = useState<[][] | RecommendItem[][]>([[], [], []]);

	useEffect(() => {
		getAllRecommendList()
			.then((res) => {
				if (res) {
					setIsLoading([false, false, false]);
					setRecommendDatas([res[0].data, res[1].data, res[2].data]);
				}
			})
			.catch((err) => {
				console.error(err);
			});
	}, []);

	return (
		<RecommendTemplate>
			{/* 검색 */}
			{/* <InputSearch value={searchKeyword} placeholder="음식 이름을 입력하세요" onChangeValue={setSearchKeyword} /> */}

			{/* OO 님의 입맛에 딱 맞는 음식 */}
			{isLoading[0] ? (
				<RecommendListSkeleton title={RECOMMEND_LIST[0].title} />
			) : (
				<RecommendList title={RECOMMEND_LIST[0].title} index={RECOMMEND_LIST[0].index} list={recommendDatas[0]} />
			)}

			{/* 건강을 채우는 음식 */}
			{isLoading[1] ? (
				<RecommendListSkeleton title={RECOMMEND_LIST[1].title} />
			) : (
				<RecommendList title={RECOMMEND_LIST[1].title} index={RECOMMEND_LIST[1].index} list={recommendDatas[1]} />
			)}

			{/* 직접 만들어 보세요! */}
			{isLoading[2] ? (
				<RecommendListSkeleton title={RECOMMEND_LIST[2].title} />
			) : (
				<RecommendList title={RECOMMEND_LIST[2].title} index={RECOMMEND_LIST[2].index} list={recommendDatas[2]} />
			)}

			{/* 믿고 먹는 FOODY의 음식 컬렉션 */}
			{/* <RecommendList title={moreTitle[3]} list={recommendDatas[3]} /> */}
		</RecommendTemplate>
	);
}

export default RecommendPage;
