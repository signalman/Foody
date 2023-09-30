import React, { useCallback, useEffect, useState } from 'react';
import RefriTemplate from 'components/template/RefriTemplate/RefriTemplate';
import PageTitle from 'components/molecule/PageTitle/PageTitle';
import REFI_CATEGORY_LIST, { CATEGORY_KEY_VALUE, DRAWER_CATEGORY_LIST, IngredientItem } from 'constants/category';
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
	const [allIngredientsList, setAllIngredientsList] = useState<IngredientItem[] | null>(null);
	const [ingredientsList, setIngredientsList] = useState<IngredientItem[] | null>(null);
	const [menuOpen, setMenuOpen] = useState(false);

	const handleMenuSelect = (menu: string) => {
		setMenuOpen(!menuOpen);
		setSelectedMenu(menu);
		setTabbarOn(!tabbarOn);
	};

	const changeAllIngredientList = useCallback(() => {
		getAllIngredientList().then((res) => {
			if (res.data) {
				setAllIngredientsList(formatIngredientsList(res.data));
			}
		});
	}, []);

	const changeIngredientList = useCallback(() => {
		if (!allIngredientsList) {
			setIngredientsList(null);
			return;
		}

		const categoryId = CATEGORY_KEY_VALUE[selectedCategory];
		if (categoryId === 0) {
			let newIngredientsList = null;
			if (type) {
				newIngredientsList = allIngredientsList.filter((item) => item.categoryType === 0);
			} else {
				newIngredientsList = allIngredientsList.filter((item) => item.categoryType === 1);
			}
			setIngredientsList(newIngredientsList);
			return;
		}

		const newIngredientsList = allIngredientsList.filter((item) => item.ingredientCategoryId === categoryId);
		setIngredientsList(newIngredientsList);
	}, [allIngredientsList, selectedCategory, type]);

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
		changeIngredientList();
	}, [changeIngredientList, allIngredientsList, selectedCategory, type]);

	useEffect(() => {
		changeAllIngredientList();
	}, [changeAllIngredientList, menuOpen]);

	useEffect(() => {
		if (!menuOpen) {
			setTabbarOn(true);
		}
	}, [menuOpen, setTabbarOn]);

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
			<IngredientsList
				changeIngredientList={changeAllIngredientList}
				handleMenuSelect={handleMenuSelect}
				ingredientsList={ingredientsList}
				type={type}
			/>

			{/* 카메라/앨범/검색 메뉴 */}
			<FloatingMenu menuList={['camera', 'album']} onMenuSelect={handleMenuSelect} />
		</RefriTemplate>
	);
}

export default RefriPage;
