import React from 'react';
import './IngredientsList.scss';
import { IngridientItem } from 'constants/category';
import formatDate from 'utils/common/formatDate';
import classNames from 'classnames';
import CustomTextAlert from 'components/organism/CustomTextAlert/CustomTextAlert';
import toast from 'react-hot-toast';
import { DUMMY_INGREDIENTS_ITEM_INFO_LIST } from 'constants/dummy';
import IngredientsListItem from '../IngredientsListItem/IngredientsListItem';
import IngredientsListItemInfo from '../IngredientsListItemInfo/IngredientsListItemInfo';

function IngredientsList({ ingredientsList, type }: { ingredientsList: IngridientItem[]; type: boolean }) {
	const containerDivClasses = classNames('ingredients-list-container', {
		'refrigerator-door': type,
		'drawer-leg': !type,
	});
	const wrapDivClasses = classNames('ingredients-list-wrap', 'no-scrollbar', {
		refrigerator: type,
		drawer: !type,
	});

	const handleClickIngredientItem = (idx: number) => {
		console.log(idx, ingredientsList[idx], formatDate(ingredientsList[idx].regiDate));
		CustomTextAlert({
			title: `재료 정보`,
			// desc: `재료명\n아래 재료가 냉장고에서 삭제됩니다.`,
			contents: <IngredientsListItemInfo infoList={DUMMY_INGREDIENTS_ITEM_INFO_LIST} />,
			closeBtnTitle: '닫기',
			params: {},
			onAction: () => {
				toast.success('삭제됐습니다~');
			},
		});
	};

	return (
		<div className={containerDivClasses}>
			<div className={wrapDivClasses}>
				<ul className="ingredients-list">
					{ingredientsList.map((item, i) => (
						<IngredientsListItem key={item.key} idx={i} handleClick={handleClickIngredientItem} item={item} />
					))}
				</ul>
			</div>
		</div>
	);
}

export default IngredientsList;
