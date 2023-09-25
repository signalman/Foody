import React, { Dispatch } from 'react';
import { useRecoilState } from 'recoil';
import tabbarState from 'recoil/atoms/tabbarState';
import Layout from 'components/template/Layout/Layout';
import SubHeader from '../SubHeader/SubHeader';

function IngredientRegistAlbum({ setOpen }: { setOpen: Dispatch<React.SetStateAction<boolean>> }) {
	const [tabbarOn, setTabbarOn] = useRecoilState(tabbarState);

	const handleMove = () => {
		setOpen((prev) => !prev);
		setTabbarOn(!tabbarOn);
	};

	return (
		<div className="ingredient-regist-album-container">
			<SubHeader isBack title="재료 등록" handleMove={handleMove} />
			<Layout>앨범 열어서 재료 등록</Layout>
		</div>
	);
}

export default IngredientRegistAlbum;
