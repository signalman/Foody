import { IngridientSearchItem, IngridientSearchResultItem } from 'types/refrigerator';

const formatSearchResultList = (res: IngridientSearchResultItem[]): IngridientSearchItem[] => {
	return res.map((r) => {
		return {
			key: r.ingredientId,
			text: r.ingredientName,
		};
	});
};

export default formatSearchResultList;
