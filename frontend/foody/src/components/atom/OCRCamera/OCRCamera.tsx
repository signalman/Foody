/* eslint-disable jsx-a11y/media-has-caption */
import React, { useEffect, useRef, useState } from 'react';
import './OCRCamera.scss';
import { BiCamera, BiCheck } from 'react-icons/bi';
// import decodingImage from 'utils/common/base64Decoding';
import classNames from 'classnames';
// import ReceiptListType from 'types/receipt';
// import { getOCRReceiptIngredients } from 'utils/api/ingredient';
// import uuid from 'react-uuid';
import LoadingSpinner from '../LoadingSpinner/LoadingSpinner';

function OCRCamera() {
	const videoRef = useRef<HTMLVideoElement | null>(null);
	const photoRef = useRef<HTMLCanvasElement | null>(null);
	const [stream, setStream] = useState<MediaStream | null>(null);
	const [base64Image, setBase64Image] = useState('');
	const [hasPhoto, setHasPhoto] = useState(false);
	const [isLoading, setIsLoading] = useState(false);
	// const [receptList, setReceptList] = useState<ReceiptListType | null>(null);
	// const [containerSize, setContainerSize] = useState({ width: 0, height: 0 });

	const takePhoto = () => {
		const video = videoRef.current;
		const photo = photoRef.current;

		if (!video || !photo) return;
		const width = video.videoWidth;
		const height = video.videoHeight;
		photo.width = width;
		photo.height = height;
		console.log(width, height);

		const ctx = photo.getContext('2d');
		if (!ctx) return;
		ctx.drawImage(video, 0, 0, width, height);

		const imageDataUrl = photo.toDataURL('image/jpeg');
		setBase64Image(imageDataUrl);
		setHasPhoto(true);
		setIsLoading(true);
		// setContainerSize({ width, height });
	};

	const confirmPhoto = () => {
		console.log('confirmPhoto');
		// TODOS : 값을 가지고 검색 menu로 넘어가기
	};

	const closePhoto = () => {
		const photo = photoRef.current;
		if (!photo) {
			return;
		}
		const ctx = photo.getContext('2d');
		if (!ctx) {
			return;
		}
		ctx.clearRect(0, 0, photo.width, photo.height);
	};

	useEffect(() => {
		const getVideo = async () => {
			try {
				const newStream = await navigator.mediaDevices.getUserMedia({ video: true });
				setStream(newStream);
				const video = videoRef.current;
				if (!video) {
					return;
				}
				video.srcObject = newStream;

				video.onloadedmetadata = () => {
					video.play();
				};
			} catch (err) {
				console.error(err);
			}
		};

		if (!stream) {
			getVideo();
		}

		return () => {
			if (stream) {
				const tracks = stream.getTracks();
				tracks.forEach((track) => track.stop());
			}

			closePhoto();
		};
	}, [stream, videoRef]);

	useEffect(() => {
		// console.log(decodingImage(base64Image));
		// console.log(base64Image);
		// TODOS: API 요청해서 값 가져오기
		if (base64Image !== undefined && base64Image !== '') {
			// console.log(base64Image);
			console.log(base64Image);
			// getOCRReceiptIngredients(uuid(), decodingImage(base64Image)).then((res) => {
			// 	console.log(res);
			// 	setIsLoading(false);
			// 	setBase64Image('');
			// 	// setReceptList(DUMMY_RECEIPT_LIST);
			// });
		}
	}, [base64Image, hasPhoto]);

	return (
		<>
			{isLoading && <LoadingSpinner />}
			base64Image : {base64Image}
			<div className="video-container">
				<video ref={videoRef} className="video" />
			</div>
			<div className={classNames('canvas-container', hasPhoto && 'has-photo')}>
				{/* {receptList && <ReceiptList containerSize={containerSize} receiptList={DUMMY_RECEIPT_LIST} />} */}
				<canvas ref={photoRef} className="canvas" />
			</div>
			<div className="btn-container">
				{hasPhoto ? (
					<button className="btn-check" type="button" onClick={confirmPhoto}>
						<BiCheck size={25} />
					</button>
				) : (
					<button type="button" onClick={takePhoto}>
						<BiCamera size={25} />
					</button>
				)}
			</div>
		</>
	);
}

export default OCRCamera;
