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
import { ReqReceiptItem } from 'types/receipt';
import CustomIngredientInsertOpen from 'components/molecule/CustomIngredientInsert/CustomIngredientInsert';
import SubHeader from '../SubHeader/SubHeader';
import SelectIngredientList from '../../molecule/SelectIngredientList/SelectIngredientList';
import IngredientSearchResultList from '../../molecule/IngredientSearchResultList/IngredientSearchResultList';

interface IngredientSearchProps {
	setOpen: Dispatch<React.SetStateAction<boolean>>;
	receiptList?: ReqReceiptItem[] | null;
}

function IngredientSearch(props: IngredientSearchProps) {
	const { setOpen, receiptList } = props;
	const [tabbarOn, setTabbarOn] = useRecoilState(tabbarState);
	const [isCustomIngredientInsertOpen, setIsCustomIngredientInsertOpen] = useState(false);
	const [searchKeyword, setSearchKeyword] = useState<string>('');
	const [searchResultList, setSearchResultList] = useState<IngredientSearchItem[] | null>(null);
	const [selectedIngredientList, setSelectedIngredientList] = useState<IngredientSearchItem[] | null>(null);
	const [customIngredientList, setCustomIngredientList] = useState<CustomIngredientItemType[] | []>([]);

	const handleMove = () => {
		setOpen((prev) => !prev);
		setTabbarOn(!tabbarOn);
	};

	const handleWrite = () => {
		setIsCustomIngredientInsertOpen(true);
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

	useEffect(() => {
		if (receiptList && receiptList.length !== 0) {
			setSelectedIngredientList([
				...receiptList.map((item) => {
					const selectItem = {
						key: item.ingredientId,
						text: item.ingredientName,
					};
					return selectItem;
				}),
			]);
		}
	}, [receiptList]);

	useEffect(() => {
		if (customIngredientList.length !== 0) {
			const ingredientsList: number[] = !selectedIngredientList ? [] : selectedIngredientList.map((item) => item.key);
			createIngredientList(ingredientsList, customIngredientList).then((res) => {
				if (res.status === 204) {
					toast.success('재료 등록에 성공하였습니다.');
					setOpen(false);
					setTabbarOn(true);
				} else {
					toast.error('재료 등록에 실패하였습니다.');
				}
			});
		}
	}, [customIngredientList, selectedIngredientList, setOpen, setTabbarOn, tabbarOn]);

	if (isCustomIngredientInsertOpen) {
		return (
			<CustomIngredientInsertOpen
				setIsCustomIngredientInsertOpen={setIsCustomIngredientInsertOpen}
				setCustomIngredientList={setCustomIngredientList}
				// handleSubmitIngredientRegist={handleSubmitIngredientRegist}
			/>
		);
	}

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

IngredientSearch.defaultProps = {
	receiptList: null,
};
