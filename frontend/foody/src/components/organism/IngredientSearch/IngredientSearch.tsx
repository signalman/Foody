import React, { Dispatch, useEffect, useState } from 'react';
import './IngredientSearch.scss';
import { useRecoilState } from 'recoil';
import tabbarState from 'recoil/atoms/tabbarState';
import InputSearch from 'components/atom/InputSearch/InputSearch';
import SearchTemplate from 'components/template/SearchTemplate/SearchTemplate';
import { HiPencil } from 'react-icons/hi';
import LargeButton from 'components/atom/LargeButton/LargeButton';
import LargeButtonColor from 'constants/color';
import getSearchIndegredients from 'utils/api/ingredient';
import formatSearchResultList from 'utils/common/ingredient';
import { IngridientSearchItem } from 'types/refrigerator';
import SubHeader from '../SubHeader/SubHeader';
import SelectIngredientList from '../../molecule/SelectIngredientList/SelectIngredientList';
import IngredientSearchResultList from '../../molecule/IngredientSearchResultList/IngredientSearchResultList';

function IngredientSearch({ setOpen }: { setOpen: Dispatch<React.SetStateAction<boolean>> }) {
	const [tabbarOn, setTabbarOn] = useRecoilState(tabbarState);
	const [searchKeyword, setSearchKeyword] = useState<string>('');
	const [searchResultList, setSearchResultList] = useState<IngridientSearchItem[] | null>(null);
	const [selectedIngredientList, setSelectedIngredientList] = useState<IngridientSearchItem[] | null>(null);

	const handleMove = () => {
		setOpen((prev) => !prev);
		setTabbarOn(!tabbarOn);
	};

	const handleWrite = () => {
		console.log('handleWrite');
	};

	const handleIngredientSelect = (idx: number) => {
		if (searchResultList) {
			const selectedIngredient = searchResultList[idx];
			const isAlreadySelected = selectedIngredientList?.some((item) => item.key === selectedIngredient.key);
			if (!isAlreadySelected) {
				setSelectedIngredientList((prevList) => [...(prevList || []), selectedIngredient]);
			}
		}
	};

	const handleDelete = (key: number) => {
		if (!selectedIngredientList) {
			return;
		}
		const newSelectIngredientList = selectedIngredientList.filter((item) => item.key !== key);
		setSelectedIngredientList(newSelectIngredientList);
	};

	useEffect(() => {
		if (searchKeyword !== '') {
			getSearchIndegredients(searchKeyword).then((res) => {
				if (res.data && res.data.length > 0) {
					console.log('formatSearchResultList(res.data)', formatSearchResultList(res.data));
					setSearchResultList(formatSearchResultList(res.data));
				}
			});
		}
	}, [searchKeyword]);

	return (
		<div className="ingredient-regist-album-container">
			<SubHeader isBack title="재료 찾기" handleMove={handleMove} />
			<SearchTemplate>
				{/* 인풋 */}
				<InputSearch value={searchKeyword} placeholder="재료 이름을 입력하세요" onChangeValue={setSearchKeyword} />

				{/* 직접 입력하기 버튼 */}
				<LargeButton
					buttonColor={LargeButtonColor.Gray}
					value="직접 입력하기"
					buttonClick={handleWrite}
					icon={HiPencil}
				/>

				{/* 선택된 재료 */}
				<SelectIngredientList selectedIngredientList={selectedIngredientList} onDelete={handleDelete} />

				{/* 검색 결과 */}
				<IngredientSearchResultList
					searchKeyword={searchKeyword}
					searchResultList={searchResultList}
					handleIngredientSelect={handleIngredientSelect}
				/>

				{/* Floating 메뉴 */}

				{/* 검색/등록 버튼 */}
			</SearchTemplate>
		</div>
	);
}

export default IngredientSearch;
