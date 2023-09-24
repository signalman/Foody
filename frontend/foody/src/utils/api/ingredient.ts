import { instance } from './instance';

export const getRefriIndegredients = async () => {
	const response = await instance.get('/refrigerators/ingredient?keyword=토마토');
	return response;
};

export const getOCRReceiptIndegredients = async (uuid: string, data: string) => {
	const body = {
		images: [
			{
				format: 'jpeg',
				name: 'indegredients',
				data,
			},
		],
		requestId: uuid,
		version: 'V2',
		timestamp: Date.now(),
	};
	console.log('body', body);
	const response = await instance.post('/receipt/ingredients', body);
	return response;
};

export const getReceiptIndegredients = async () => {
	const response = await instance.post('/refrigerator');
	return response;
};

export default getRefriIndegredients;
