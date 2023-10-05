import { IngredientSearchItem, IngredientSearchResultItem } from 'types/refrigerator';
import { IngredientsList, IngredientItem } from 'constants/category';
import defaultIcon from 'assets/icons/ingredientDefaultIcon.png';

const formatSearchResultList = (res: IngredientSearchResultItem[]): IngredientSearchItem[] => {
	return res.map((r) => {
		return {
			key: r.ingredientId,
			text: r.ingredientName,
		};
	});
};

export const formatIngredientsList = (res: IngredientsList[]): IngredientItem[] => {
	return res.map((r) => {
		return {
			key: r.ingredientId,
			text: r.ingredientName,
			img: r.iconImg ? r.iconImg : defaultIcon,
			regiDate: r.createDate,
			categoryType: r.categoryType,
			ingredientCategoryId: r.ingredientCategoryId,
			ingredientId: r.ingredientId,
		};
	});
};

export default formatSearchResultList;
