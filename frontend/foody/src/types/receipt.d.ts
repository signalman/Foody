// interface ReceiptListType {
// 	value: string[];
// 	boundingPolys: {
// 		vertices: {
// 			x: number;
// 			y: number;
// 		}[];
// 	}[];
// }

export interface ReqReceiptItem {
	ingredientCategoryId: number;
	ingredientId: number;
	ingredientName: string;
}

export interface BookmarkItemType {
	id: number; // 레시피 ID
	name: string; // 레시피 이름
	ingredients: string[]; // 레시피 재료 목록
}
