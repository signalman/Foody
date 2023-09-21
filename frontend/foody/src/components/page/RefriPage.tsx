import React, { useEffect, useState } from 'react';
import RefriTemplate from 'components/template/RefriTemplate/RefriTemplate';
import PageTitle from 'components/molecule/PageTitle/PageTitle';
import REFI_CATEGORY_LIST, { DRAWER_CATEGORY_LIST } from 'constants/category';
import useToggle from 'hooks/useToggle';
import IngredientsCategory from 'components/molecule/IngredientsCategory/IngredientsCategory';

function RefriPage() {
	const [type, setType] = useToggle(true); // true: 냉장고, false: 서랍
	const [title, setTitle] = useState<string>('나의 냉장고');
	const [selectedCategory, setSelectedCategory] = useState<string>('모든 재료');
	const [categoryList, setCategoryList] = useState(REFI_CATEGORY_LIST);

	useEffect(() => {
		if (type) {
			setTitle('나의 냉장고');
			setCategoryList(REFI_CATEGORY_LIST);
			return;
		}
		setTitle('나의 선반');
		setCategoryList(DRAWER_CATEGORY_LIST);
	}, [type]);

	return (
		<RefriTemplate>
			{/* 나의 냉장고 or 나의 선반 및 토글 버튼 */}
			<PageTitle title={title} type={type} setType={setType} />

			{/* 재료 카테고리 */}
			<IngredientsCategory categoryList={categoryList} selected={selectedCategory} setSelected={setSelectedCategory} />
			{selectedCategory}
			{/* 재료 목록 */}
			<div>
				<ul>
					<li>사과</li>
					<li>닭가슴살</li>
					<li>오이</li>
					<li>토마토</li>
					<li>양배추</li>
				</ul>
			</div>
		</RefriTemplate>
	);
}

export default RefriPage;
