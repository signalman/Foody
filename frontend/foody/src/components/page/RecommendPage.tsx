import React, { useEffect, useState } from 'react';
import RecommendTemplate from 'components/template/RecommendTemplate/RecommendTemplate';
import InputSearch from 'components/atom/InputSearch/InputSearch';
import getRecommendList from 'utils/api/recommend';
import { DUMMY_RECOMMEND_LIST1 } from 'constants/dummy';
import RecommendList from 'components/molecule/RecommendList/RecommendList';

function RecommendPage() {
	const [searchKeyword, setSearchKeyword] = useState<string>('');

	useEffect(() => {
		if (searchKeyword !== '') {
			getRecommendList(searchKeyword)
				.then((res) => {
					if (res.data && res.data.length > 0) {
						console.log(res.data);
					}
				})
				.catch((err) => console.error(err));
		}
	}, [searchKeyword]);

	return (
		<RecommendTemplate>
			{/* 검색 */}
			<InputSearch value={searchKeyword} placeholder="음식 이름을 입력하세요" onChangeValue={setSearchKeyword} />

			{/* OO 님의 입맛에 딱 맞는 음식 */}
			<RecommendList title="OO님의 입맛에 딱 맞는 음식" to="/recommend/more/1" list={DUMMY_RECOMMEND_LIST1} />

			{/* OO 님의 식단과 비슷한 음식 */}
			<RecommendList title="OO 님의 식단과 비슷한 음식" to="/recommend/more/2" list={DUMMY_RECOMMEND_LIST1} />

			{/* 직접 만들어 보세요! */}
			<RecommendList title="직접 만들어 보세요!" to="/recommend/more/3" list={DUMMY_RECOMMEND_LIST1} />

			{/* 믿고 먹는 FOODY의 음식 컬렉션 */}
			<RecommendList title="믿고 먹는 FOODY의 음식 컬렉션" to="/recommend/more/4" list={DUMMY_RECOMMEND_LIST1} />
		</RecommendTemplate>
	);
}

export default RecommendPage;
