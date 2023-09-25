interface ReceiptListType {
	value: string[];
	boundingPolys: {
		vertices: {
			x: number;
			y: number;
		}[];
	}[];
}

export default ReceiptListType;
