import React, { useEffect, useState } from 'react';
import RecipeDetailTemplate from 'components/template/RecipeDetailTemplate/RecipeDetailTemplate';
import SubHeader from 'components/organism/SubHeader/SubHeader';
import RecipeInfo from 'components/molecule/RecipeInfo/RecipeInfo';
import { useParams } from 'react-router-dom';
import { getRecipeDetail } from 'utils/api/recipe';
import RecipeNutrient from 'components/molecule/RecipeNutrient/RecipeNutrient';
import RecipeIngredientList from '../atom/RecipeIngredientList/RecipeIngredientList';

function RecipeDetailPage() {
	const { id } = useParams();
	const [data, setData] = useState<RecipeDetailDataType | null>(null);

	useEffect(() => {
		if (id && !data) {
			getRecipeDetail(parseInt(id, 10)).then((res) => {
				console.log(res);
				setData(res.data);
			});
		}
	}, [data, id]);

	if (!data) {
		return <div>로딩</div>;
	}

	return (
		<RecipeDetailTemplate>
			{/* 서브 타이틀 : 레시피명 */}
			<SubHeader isBack title={data.name} handleMove={null} />

			{/* 레시피 정보 : 이미지, 레시피명, 칼로리, 북마크, 레시피 보러 가기 */}
			<RecipeInfo id={data.id} url={data.url} name={data.name} isBookmarked={data.isBookmarked} energy={data.energy} />

			{/* 필요한 재료 */}
			<RecipeIngredientList
				steps={data.steps}
				ingredient={data.ingredient}
				servers={data.servers}
				difficulty={data.difficulty}
			/>

			{/* 채워지는 영양소 */}
			<RecipeNutrient
				energy={data.energy}
				carbohydrates={data.carbohydrates}
				protein={data.protein}
				dietaryFiber={data.dietaryFiber}
				calcium={data.calcium}
				sodium={data.sodium}
				iron={data.iron}
				fats={data.fats}
				vitaminA={data.vitaminA}
				vitaminC={data.vitaminC}
			/>
		</RecipeDetailTemplate>
	);
}

export default RecipeDetailPage;
