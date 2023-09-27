import React, { useEffect, useState } from 'react';
import RefriTemplate from 'components/template/RefriTemplate/RefriTemplate';
import PageTitle from 'components/molecule/PageTitle/PageTitle';
import REFI_CATEGORY_LIST, { CATEGORY_KEY_VALUE, DRAWER_CATEGORY_LIST, IngridientItem } from 'constants/category';
import useToggle from 'hooks/useToggle';
import IngredientsCategory from 'components/molecule/IngredientsCategory/IngredientsCategory';
import IngredientsList from 'components/atom/IngredientsList/IngredientsList';
import FloatingMenu from 'components/molecule/FloatingMenu/FloatingMenu';
import IngredientRegistOCR from 'components/organism/IngredientRegistOCR/IngredientRegistOCR';
import IngredientRegistAlbum from 'components/organism/IngredientRegistAlbum/IngredientRegistAlbum';
import IngredientSearch from 'components/organism/IngredientSearch/IngredientSearch';
import { getAllIngredientList } from 'utils/api/ingredient';
import { formatIngredientsList } from 'utils/common/ingredient';
import { useRecoilState } from 'recoil';
import tabbarState from 'recoil/atoms/tabbarState';

function RefriPage() {
	const [tabbarOn, setTabbarOn] = useRecoilState(tabbarState);
	const [type, setType] = useToggle(true); // true: 냉장고, false: 서랍
	const [title, setTitle] = useState<string>('나의 냉장고');
	const [selectedCategory, setSelectedCategory] = useState<string>('모든 재료');
	const [selectedMenu, setSelectedMenu] = useState<string | null>(null);
	const [categoryList, setCategoryList] = useState(REFI_CATEGORY_LIST);
	const [ingredientsListAll, setIngredientsListAll] = useState<IngridientItem[] | null>(null);
	const [ingredientsList, setIngredientsList] = useState<IngridientItem[] | null>(null);
	const [menuOpen, setMenuOpen] = useState(false);

	const handleMenuSelect = (menu: string) => {
		setMenuOpen(!menuOpen);
		setSelectedMenu(menu);
		setTabbarOn(!tabbarOn);
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
		if (!ingredientsListAll) {
			setIngredientsList(null);
			return;
		}

		console.log('카테고리별 목록 수정!!');
		const categoryId = CATEGORY_KEY_VALUE[selectedCategory];
		if (categoryId === 0) {
			let newIngredientsList = null;
			if (type) {
				newIngredientsList = ingredientsListAll.filter((item) => item.categoryType === 0);
			} else {
				newIngredientsList = ingredientsListAll.filter((item) => item.categoryType === 1);
			}
			setIngredientsList(newIngredientsList);
			return;
		}

		const newIngredientsList = ingredientsListAll.filter((item) => item.ingredientCategoryId === categoryId);
		setIngredientsList(newIngredientsList);
	}, [ingredientsListAll, selectedCategory, type]);

	useEffect(() => {
		getAllIngredientList().then((res) => {
			console.log('all', res.data);
			if (res.data && res.data.length > 0) {
				setIngredientsListAll(formatIngredientsList(res.data));
			}
		});
	}, [menuOpen]);

	if (menuOpen) {
		if (selectedMenu === 'camera') return <IngredientRegistOCR setOpen={setMenuOpen} />;
		if (selectedMenu === 'album') return <IngredientRegistAlbum setOpen={setMenuOpen} />;
		return <IngredientSearch setOpen={setMenuOpen} />;
	}

	return (
		<RefriTemplate>
			{/* 나의 냉장고 or 나의 선반 및 토글 버튼 */}
			<PageTitle title={title} type={type} setType={setType} />

			{/* 재료 카테고리 */}
			<IngredientsCategory categoryList={categoryList} selected={selectedCategory} setSelected={setSelectedCategory} />

			{/* 재료 목록 */}
			<IngredientsList handleMenuSelect={handleMenuSelect} ingredientsList={ingredientsList} type={type} />

			{/* 카메라/앨범/검색 메뉴 */}
			<FloatingMenu menuList={['camera', 'album']} onMenuSelect={handleMenuSelect} />
		</RefriTemplate>
	);
}

export default RefriPage;
