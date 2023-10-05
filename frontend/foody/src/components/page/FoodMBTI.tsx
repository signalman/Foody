import ImgCount from 'components/atom/ImgCount/ImgCount';
import FoodMBTIList from 'components/molecule/FoodMBTIList/FoodMBTIList';
import React, { useEffect, useState } from 'react';
import { getMBTIImage, resultMBTI } from 'utils/api/auth';
import FoodMBTIImage from 'components/atom/FoodMBTIImage/FoodMBTIImage';
import FoodMBTITemplate from 'components/template/FoodMBTITemplate/FoodMBTITemplate';
import useMovePage from 'hooks/useMovePage';

function FoodMBTI() {
	const { movePage } = useMovePage();
	const [arr, setArr] = useState([]);
	const [idx, setIdx] = useState(0);

	const resultImages = (data: number[]) => {
		resultMBTI(data).then(() => {
			movePage('/home', null);
		});
	};

	useEffect(() => {
		getMBTIImage().then((response) => {
			setArr(response.data.images);
		});
	}, []);

	return (
		<FoodMBTITemplate>
			<div className="desc-wrap">
				<h2>선호하는 메뉴를 골라주세요!</h2>
				<p>선호도에 따라 레시피를 추천해 드려요</p>
			</div>
			<ImgCount total={arr.length} value={idx + 1} />
			<FoodMBTIImage images={arr} idx={idx} />
			<FoodMBTIList imageValue={resultImages} setIdx={setIdx} idx={idx} />
		</FoodMBTITemplate>
	);
}

export default FoodMBTI;
