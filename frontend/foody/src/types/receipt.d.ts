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
