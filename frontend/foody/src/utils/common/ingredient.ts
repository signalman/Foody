import { IngridientSearchItem, IngridientSearchResultItem } from 'types/refrigerator';
import testImg1 from 'assets/icons/Apple.svg';
import { IngredientsList, IngridientItem } from 'constants/category';

const formatSearchResultList = (res: IngridientSearchResultItem[]): IngridientSearchItem[] => {
	return res.map((r) => {
		return {
			key: r.ingredientId,
			text: r.ingredientName,
		};
	});
};

export const formatIngredientsList = (res: IngredientsList[]): IngridientItem[] => {
	return res.map((r, idx) => {
		return {
			key: idx,
			text: r.ingredientName,
			img: testImg1,
			regiDate: r.createDate,
			categoryType: r.categoryType,
			ingredientCategoryId: r.ingredientCategoryId,
		};
	});
};

export default formatSearchResultList;
