interface RecipeIngredient {
	name: string; // 레시피 재료 이름
	unit: string; // 재료의 양
}

interface RecipeInfoType {
	id: number; // recipe ID
	url: string; // 레시피 사진 주소
	name: string; // 레시피 제목
	isBookmarked: boolean; // 회원의 이 레시피 북마크 여부
}

interface RecipeType {
	steps: []; // 레시피 순서(list에 순서대로 저장되있음)
	servers: number; // 몇 인분 기준
	difficulty: string; // 레시피 난이도
	ingredient: RecipeIngredient[]; // 레시피 재료 목록
}

interface RecipeNutrientType {
	energy: number; // 칼로리
	carbohydrates: number; // 탄수화물
	protein: number; // 단백질
	dietaryFiber: number; // 식이섬유
	calcium: number; // 칼슘
	sodium: number; // 나트륨
	iron: number; // 철분
	fats: number; // 지방
	vitaminA: number; // 비타민A
	vitaminC: number; // 비타민C
}

interface RecipeDetailDataType extends RecipeInfoType, RecipeType, RecipeNutrientType {}
