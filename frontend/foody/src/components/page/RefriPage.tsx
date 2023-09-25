import React, { useEffect, useState } from 'react';
import RefriTemplate from 'components/template/RefriTemplate/RefriTemplate';
import PageTitle from 'components/molecule/PageTitle/PageTitle';
import REFI_CATEGORY_LIST, { DRAWER_CATEGORY_LIST, IngridientItem } from 'constants/category';
import useToggle from 'hooks/useToggle';
import IngredientsCategory from 'components/molecule/IngredientsCategory/IngredientsCategory';
import IngredientsList from 'components/atom/IngredientsList/IngredientsList';
import DUMMY_INGREDIENTS_LIST, { DUMMY_INGREDIENTS_LIST2 } from 'constants/dummy';
import FloatingMenu from 'components/molecule/FloatingMenu/FloatingMenu';
import IngredientRegistOCR from 'components/organism/IngredientRegistOCR/IngredientRegistOCR';

function RefriPage() {
	const [type, setType] = useToggle(true); // true: 냉장고, false: 서랍
	const [title, setTitle] = useState<string>('나의 냉장고');
	const [selectedCategory, setSelectedCategory] = useState<string>('모든 재료');
	const [categoryList, setCategoryList] = useState(REFI_CATEGORY_LIST);
	const [ingredientsList, setIngredientsList] = useState<IngridientItem[] | null>(null);
	const [menuOpen, setMenuOpen] = useState(false);

	const handleMenuSelect = (menu: string) => {
		console.log('선택된 메뉴', menu);
		setMenuOpen(!menuOpen);
	};

	useEffect(() => {
		setSelectedCategory('모든 재료');
		if (type) {
			setTitle('나의 냉장고');
			setCategoryList(REFI_CATEGORY_LIST);
			return;
		}
		setTitle('나의 선반');
		setCategoryList(DRAWER_CATEGORY_LIST);
	}, [type]);

	useEffect(() => {
		// TODOS: selectedCategory 이용해서 카테고리별 재료 목록 GET
		if (type) {
			// type은 임시로 사용
			setIngredientsList(DUMMY_INGREDIENTS_LIST2);
			return;
		}

		setIngredientsList(DUMMY_INGREDIENTS_LIST);
	}, [selectedCategory, type]);

	if (menuOpen) {
		return <IngredientRegistOCR setOpen={setMenuOpen} />;
	}

	return (
		<RefriTemplate>
			{/* 나의 냉장고 or 나의 선반 및 토글 버튼 */}
			<PageTitle title={title} type={type} setType={setType} />

			{/* 재료 카테고리 */}
			<IngredientsCategory categoryList={categoryList} selected={selectedCategory} setSelected={setSelectedCategory} />

			{/* 재료 목록 */}
			{ingredientsList && <IngredientsList ingredientsList={ingredientsList} type={type} />}

			{/* 카메라/앨범/검색 메뉴 */}
			<FloatingMenu menuList={['camera', 'album', 'search']} onMenuSelect={handleMenuSelect} />
		</RefriTemplate>
	);
}

export default RefriPage;
