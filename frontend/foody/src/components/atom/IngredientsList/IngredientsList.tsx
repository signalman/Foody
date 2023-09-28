import React from 'react';
import './IngredientsList.scss';
import { IngredientItem } from 'constants/category';
import classNames from 'classnames';
import CustomTextAlert from 'components/organism/CustomTextAlert/CustomTextAlert';
import { BiPlus } from 'react-icons/bi';
import { deleteIngredient } from 'utils/api/ingredient';
import toast from 'react-hot-toast';
import IngredientsListItem from '../IngredientsListItem/IngredientsListItem';
import IngredientsListItemInfo from '../IngredientsListItemInfo/IngredientsListItemInfo';

function IngredientsList({
	changeIngredientList,
	handleMenuSelect,
	ingredientsList,
	type,
}: {
	changeIngredientList: () => void;
	handleMenuSelect: (menu: string) => void;
	ingredientsList: IngredientItem[] | null;
	type: boolean;
}) {
	const containerDivClasses = classNames('ingredients-list-container', {
		'refrigerator-door': type,
		'drawer-leg': !type,
	});
	const wrapDivClasses = classNames('ingredients-list-wrap', 'no-scrollbar', {
		refrigerator: type,
		drawer: !type,
	});
	const handleClickIngredientItem = (item: IngredientItem) => {
		if (!ingredientsList) {
			return;
		}

		const infoList = [
			{
				title: '재료명',
				value: item.text,
			},
			{
				title: '등록일',
				value: item.regiDate,
			},
		];

		CustomTextAlert({
			title: `재료 정보`,
			contents: <IngredientsListItemInfo infoList={infoList} />,
			btnTitle: '닫기',
			isDelete: true,
			params: {},
			onAction: () => {},
			onDelete: () => {
				deleteIngredient(item.ingredientId)
					.then(() => {
						toast.success(`'${item.text}'를 삭제하였습니다.`);
						changeIngredientList();
					})
					.catch((err) => {
						if (err.response.status === 404) {
							toast.error(`'${item.text}'가 존재하지 않습니다.`);
							changeIngredientList();
						}
					});
			},
		});
	};

	return (
		<div className={containerDivClasses}>
			<div className={wrapDivClasses}>
				<ul className="ingredients-list">
					<li className="ingredients-list-item-container">
						<button type="button" onClick={() => handleMenuSelect('search')}>
							<BiPlus className="plus-icon" size={30} />
						</button>
					</li>
					{ingredientsList &&
						ingredientsList.map((item) => (
							<IngredientsListItem
								key={item.ingredientId}
								idx={item.ingredientId}
								handleClick={() => handleClickIngredientItem(item)}
								item={item}
							/>
						))}
				</ul>
			</div>
		</div>
	);
}

export default IngredientsList;
