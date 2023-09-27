import React, { Dispatch, useEffect, useState } from 'react';
import './IngredientSearch.scss';
import { useRecoilState } from 'recoil';
import tabbarState from 'recoil/atoms/tabbarState';
import InputSearch from 'components/atom/InputSearch/InputSearch';
import SearchTemplate from 'components/template/SearchTemplate/SearchTemplate';
import { HiPencil } from 'react-icons/hi';
import LargeButton from 'components/atom/LargeButton/LargeButton';
import LargeButtonColor from 'constants/color';
import getSearchIngredients, { createIngredientList } from 'utils/api/ingredient';
import formatSearchResultList from 'utils/common/ingredient';
import { CustomIngredientItemType, IngredientSearchItem } from 'types/refrigerator';
import toast from 'react-hot-toast';
import SubHeader from '../SubHeader/SubHeader';
import SelectIngredientList from '../../molecule/SelectIngredientList/SelectIngredientList';
import IngredientSearchResultList from '../../molecule/IngredientSearchResultList/IngredientSearchResultList';

function IngredientSearch({ setOpen }: { setOpen: Dispatch<React.SetStateAction<boolean>> }) {
	const [tabbarOn, setTabbarOn] = useRecoilState(tabbarState);
	const [searchKeyword, setSearchKeyword] = useState<string>('');
	const [searchResultList, setSearchResultList] = useState<IngredientSearchItem[] | null>(null);
	const [selectedIngredientList, setSelectedIngredientList] = useState<IngredientSearchItem[] | null>(null);
	const [customIngredientList, setCustomIngredientList] = useState<CustomIngredientItemType[] | []>([]);

	const handleMove = () => {
		setOpen((prev) => !prev);
		setTabbarOn(!tabbarOn);
	};

	const handleWrite = () => {
		console.log('handleWrite');
		toast.success('재료 직접 등록');
		setCustomIngredientList([
			...customIngredientList,
			{
				ingredientCategoryId: 2,
				ingredientName: '닭고기',
			},
		]);
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

	const handleSubmitIngredientRegist = () => {
		if (selectedIngredientList && selectedIngredientList.length !== 0) {
			console.log('등록');
			console.log(customIngredientList);
			createIngredientList(
				selectedIngredientList.map((item) => item.key),
				customIngredientList,
			).then((res) => {
				if (res.status === 204) {
					toast.success('재료 등록에 성공하였습니다.');
					setOpen(false);
					setTabbarOn(!tabbarOn);
				} else {
					toast.error('재료 등록에 실패하였습니다.');
				}
			});
		}
	};

	useEffect(() => {
		if (searchKeyword !== '') {
			getSearchIngredients(searchKeyword)
				.then((res) => {
					if (res.data && res.data.length > 0) {
						setSearchResultList(formatSearchResultList(res.data));
						return;
					}
					setSearchResultList(null);
				})
				.catch((err) => console.error(err));
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
				<div />

				{/* 검색/등록 버튼 */}
				<LargeButton
					buttonColor={
						selectedIngredientList && selectedIngredientList.length !== 0
							? LargeButtonColor.Green
							: LargeButtonColor.Gray
					}
					value="재료 등록하기"
					buttonClick={handleSubmitIngredientRegist}
				/>
			</SearchTemplate>
		</div>
	);
}

export default IngredientSearch;
