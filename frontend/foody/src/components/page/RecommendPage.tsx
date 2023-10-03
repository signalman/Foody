import React from 'react';
import RecommendTemplate from 'components/template/RecommendTemplate/RecommendTemplate';
import RecommendList from 'components/molecule/RecommendList/RecommendList';
import RECOMMEND_LIST from 'constants/recommend';
import { useRecoilValue } from 'recoil';
import { userInfoState } from 'recoil/atoms/nutrientState';

function RecommendPage() {
	// const [searchKeyword, setSearchKeyword] = useState<string>('');
	const userInfo = useRecoilValue(userInfoState);

	return (
		<RecommendTemplate>
			{/* 검색 */}
			{/* <InputSearch value={searchKeyword} placeholder="음식 이름을 입력하세요" onChangeValue={setSearchKeyword} /> */}

			{/* OO 님의 입맛에 딱 맞는 음식 */}
			<RecommendList title={`${userInfo.nickname} 님의 입맛에 딱 맞는 음식`} index={RECOMMEND_LIST[0].index} />

			{/* 건강을 채우는 음식 */}
			<RecommendList title={RECOMMEND_LIST[1].title} index={RECOMMEND_LIST[1].index} />

			{/* 직접 만들어 보세요! */}
			<RecommendList title={RECOMMEND_LIST[2].title} index={RECOMMEND_LIST[2].index} />

			{/* 믿고 먹는 FOODY의 음식 컬렉션 */}
			{/* <RecommendList title={moreTitle[3]} list={recommendDatas[3]} /> */}
		</RecommendTemplate>
	);
}

export default RecommendPage;
