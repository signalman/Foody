import React, { Dispatch } from 'react';
import './IngredientRegistOCR.scss';
import Layout from 'components/template/Layout/Layout';
import { useRecoilState } from 'recoil';
import tabbarState from 'recoil/atoms/tabbarState';
import { BiCamera } from 'react-icons/bi';
import SubHeader from '../SubHeader/SubHeader';

function IngredientRegistOCR({ setOpen }: { setOpen: Dispatch<React.SetStateAction<boolean>> }) {
	const [tabbarOn, setTabbarOn] = useRecoilState(tabbarState);

	const handleMove = () => {
		setOpen((prev) => !prev);
		setTabbarOn(!tabbarOn);
	};

	return (
		<div className="ingredient-regist-OCR-container">
			<SubHeader isBack title="재료 등록" handleMove={handleMove} />
			<Layout>비디오</Layout>
			<div className="btn-container">
				<button
					type="button"
					onClick={() => {
						console.log('카메라');
					}}
				>
					<BiCamera size={25} />
				</button>
			</div>
		</div>
	);
}

export default IngredientRegistOCR;
