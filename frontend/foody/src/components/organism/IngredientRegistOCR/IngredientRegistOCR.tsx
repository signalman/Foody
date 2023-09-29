import React, { Dispatch, useState } from 'react';
import './IngredientRegistOCR.scss';
import Layout from 'components/template/Layout/Layout';
import { useRecoilState } from 'recoil';
import tabbarState from 'recoil/atoms/tabbarState';
import OCRCamera from 'components/atom/OCRCamera/OCRCamera';
import { ReqReceiptItem } from 'types/receipt';
import SubHeader from '../SubHeader/SubHeader';
import IngredientSearch from '../IngredientSearch/IngredientSearch';

function IngredientRegistOCR({ setOpen }: { setOpen: Dispatch<React.SetStateAction<boolean>> }) {
	const [, setTabbarOn] = useRecoilState(tabbarState);
	const [receiptList, setReceiptList] = useState<ReqReceiptItem[] | null>(null);

	const handleMove = () => {
		setOpen((prev) => !prev);
		setTabbarOn(true);
	};

	if (receiptList) {
		return <IngredientSearch receiptList={receiptList} setOpen={setOpen} />;
	}

	return (
		<div className="ingredient-regist-OCR-container">
			<SubHeader isBack title="재료 등록" handleMove={handleMove} />
			<Layout>
				<OCRCamera setReceiptList={setReceiptList} />
			</Layout>
		</div>
	);
}

export default IngredientRegistOCR;
