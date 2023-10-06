import React, { Dispatch, useEffect, useState } from 'react';
import './CustomIngredientInsert.scss';
import LargeButton from 'components/atom/LargeButton/LargeButton';
import SubHeader from 'components/organism/SubHeader/SubHeader';
import LargeButtonColor from 'constants/color';
import { CustomIngredientItemType } from 'types/refrigerator';
import UnderlineInput from 'components/atom/UnderlineInput/UnderlineInput';
import ContentsLayout from 'components/template/ContentsLayout/ContentsLayout';
import LayoutBottomMargin, { LayoutLeftRightMargin } from 'constants/Margin';
import BottomButtonLayout from '../../template/BottomButtonLayout/BottomButtonLayout';
import CategoryButtonList from '../CategoryButtonList/CategoryButtonList';

function CustomIngredientInsert({
	setIsCustomIngredientInsertOpen,
	setCustomIngredientList,
}: {
	setIsCustomIngredientInsertOpen: Dispatch<React.SetStateAction<boolean>>;
	setCustomIngredientList: Dispatch<React.SetStateAction<[] | CustomIngredientItemType[]>>;
}) {
	const [ingredientName, setIngredientName] = useState<string>('');
	const [selectCategory, setSelectCategory] = useState<number>(-1);
	const [check, setCheck] = useState<boolean>(false);

	const ingredientNameCheck = (value: string) => {
		const nameRegex = /^[a-zA-Z가-힣\s]*[ㄱ-ㅎㅏ-ㅣa-zA-Z가-힣\s]+[a-zA-Z가-힣\s]*$/;

		if (nameRegex.test(value) || value === '') {
			return true;
		}

		return false;
	};

	const handleBack = () => {
		setIsCustomIngredientInsertOpen(false);
	};

	const handleSubmit = () => {
		setCustomIngredientList([
			{
				ingredientCategoryId: selectCategory,
				ingredientName,
			},
		]);
	};

	const handleSelectCategory = (id: number) => {
		setSelectCategory(id);
	};

	useEffect(() => {
		if (ingredientName.length === 0) {
			setCheck(true);
		} else {
			setCheck(false);
		}
	}, [ingredientName, check]);

	return (
		<div className="custom-ingredient-insert-container">
			<SubHeader isBack title="직접 입력하기" handleMove={handleBack} />
			<ContentsLayout marginBottom={LayoutBottomMargin.mbTabbar} marginLR={LayoutLeftRightMargin.m10}>
				<UnderlineInput
					maxlength={100}
					isValid={ingredientNameCheck}
					onChangeValue={setIngredientName}
					placeholder="재료 이름"
					unit=""
					value={ingredientName}
				/>
				<CategoryButtonList selectCategory={selectCategory} handleSelectCategory={handleSelectCategory} />
			</ContentsLayout>
			<BottomButtonLayout>
				<LargeButton
					buttonColor={
						!check && ingredientName.length !== 0 && selectCategory !== -1
							? LargeButtonColor.Green
							: LargeButtonColor.Gray
					}
					value="등록하기"
					buttonClick={handleSubmit}
				/>
			</BottomButtonLayout>
		</div>
	);
}

export default CustomIngredientInsert;
