import React, { useState } from 'react';
import './DevTemplate.scss';
import LayoutBottomMargin from 'constants/Margin';
import Header from 'components/organism/Header/Header';
import Layout from '../Layout/Layout';

function DevTemplate() {
	const [selectedFile, setSelectedFile] = useState<File | null>(null);

	const handleFileChange = (event: React.ChangeEvent<HTMLInputElement>) => {
		const file = event.target.files?.[0]; // Optional chaining을 사용하여 null 체크
		if (file) {
			setSelectedFile(file);
		}
	};

	const handleUpload = () => {
		// 여기에서 `selectedFile`을 서버로 업로드하거나 필요한 처리를 수행할 수 있습니다.
		if (selectedFile) {
			// 파일을 업로드하는 로직을 여기에 추가할 수 있습니다.
			console.log('업로드할 파일:', selectedFile);
		} else {
			console.log('파일을 선택해주세요.');
		}
	};

	return (
		<Layout marginBottom={LayoutBottomMargin.mbTabbar}>
			<Header />
			<div>
				<input type="file" onChange={handleFileChange} />
				<button type="button" onClick={handleUpload}>
					업로드
				</button>
			</div>
		</Layout>
	);
}

export default DevTemplate;
