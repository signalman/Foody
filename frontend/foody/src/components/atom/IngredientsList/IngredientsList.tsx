import React, { useState } from 'react';
import './IngredientsList.scss';
import { IngridientItem } from 'constants/category';
import classNames from 'classnames';
import CustomTextAlert from 'components/organism/CustomTextAlert/CustomTextAlert';
import { DUMMY_INGREDIENTS_ITEM_INFO_LIST } from 'constants/dummy';
import toast from 'react-hot-toast';
import IngredientsListItem from '../IngredientsListItem/IngredientsListItem';
import IngredientsListItemInfo from '../IngredientsListItemInfo/IngredientsListItemInfo';

function IngredientsList({ ingredientsList, type }: { ingredientsList: IngridientItem[] | null; type: boolean }) {
	const containerDivClasses = classNames('ingredients-list-container', {
		'refrigerator-door': type,
		'drawer-leg': !type,
	});
	const wrapDivClasses = classNames('ingredients-list-wrap', 'no-scrollbar', {
		refrigerator: type,
		drawer: !type,
	});
	const [openDeleteAlert, setOpenDeleteAlert] = useState(false);
	const [selectedItem, setSelectedItem] = useState<IngridientItem | null>(null);

	const handleClickIngredientItem = (idx: number) => {
		if (!ingredientsList) {
			return;
		}
		setSelectedItem(ingredientsList[idx]);
	};

	if (selectedItem) {
		CustomTextAlert({
			title: `재료 정보`,
			contents: <IngredientsListItemInfo infoList={DUMMY_INGREDIENTS_ITEM_INFO_LIST} />,
			isDelete: true,
			closeBtnTitle: '닫기',
			params: {},
			onAction: () => {
				// TODOS: 재료 삭제
				setOpenDeleteAlert(true);
			},
		});
	}

	return (
		<>
			<div className={containerDivClasses}>
				<div className={wrapDivClasses}>
					<ul className="ingredients-list">
						{ingredientsList &&
							ingredientsList.map((item, i) => (
								<IngredientsListItem key={item.text} idx={i} handleClick={handleClickIngredientItem} item={item} />
							))}
					</ul>
				</div>
			</div>

			{/* {selectedItem &&
                CustomTextAlert({
                    title: `재료 정보`,
                    contents: <IngredientsListItemInfo infoList={DUMMY_INGREDIENTS_ITEM_INFO_LIST} />,
                    isDelete: true,
                    closeBtnTitle: '닫기',
                    params: {},
                    onAction: () => {
                        // TODOS: 재료 삭제
                        setOpenDeleteAlert(true);
                    },
                })} */}

			{openDeleteAlert &&
				selectedItem &&
				CustomTextAlert({
					title: `재료 삭제`,
					desc: `'${selectedItem.text}'를 삭제하시겠습니까?`,
					confirmBtnTitle: '삭제하기',
					closeBtnTitle: '닫기',
					params: { key: selectedItem.key },
					onAction: (params) => {
						// TODOS: 재료 삭제
						if (selectedItem) {
							console.log(params);
							toast.success('삭제');
							setSelectedItem(null); // 모달을 닫습니다.
						}
					},
				})}
		</>
	);
}

export default IngredientsList;
