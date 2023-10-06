/* eslint-disable jsx-a11y/media-has-caption */
import React, { Dispatch, useEffect, useRef, useState } from 'react';
import './OCRCamera.scss';
import { BiCamera, BiCheck } from 'react-icons/bi';
import classNames from 'classnames';
import decodingImage from 'utils/common/base64Decoding';
import { getOCRReceiptIngredients } from 'utils/api/ingredient';
import uuid from 'react-uuid';
import tabbarState from 'recoil/atoms/tabbarState';
import { useRecoilState } from 'recoil';
import { ReqReceiptItem } from 'types/receipt';
import toast from 'react-hot-toast';
import LoadingSpinner from '../LoadingSpinner/LoadingSpinner';

function OCRCamera({ setReceiptList }: { setReceiptList: Dispatch<React.SetStateAction<ReqReceiptItem[] | null>> }) {
	const [, setTabbarOn] = useRecoilState(tabbarState);
	const videoRef = useRef<HTMLVideoElement | null>(null);
	const photoRef = useRef<HTMLCanvasElement | null>(null);
	const [stream, setStream] = useState<MediaStream | null>(null);
	const [base64Image, setBase64Image] = useState('');
	const [hasPhoto, setHasPhoto] = useState(false);
	const [isLoading, setIsLoading] = useState(false);
	const [receiptData, setReceiptData] = useState<ReqReceiptItem[] | null>(null);

	const takePhoto = () => {
		const video = videoRef.current;
		const photo = photoRef.current;

		if (!video || !photo) return;
		const width = video.videoWidth;
		const height = video.videoHeight;
		photo.width = width;
		photo.height = height;

		const ctx = photo.getContext('2d');
		if (!ctx) return;
		ctx.drawImage(video, 0, 0, width, height);

		const imageDataUrl = photo.toDataURL('image/jpeg');
		setBase64Image(imageDataUrl);
		setHasPhoto(true);
		setIsLoading(true);
	};

	const confirmPhoto = () => {
		setReceiptList(receiptData);
		setBase64Image('');
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
			const constraints = {
				video: {
					facingMode: 'environment',
				},
			};
			try {
				const newStream = await navigator.mediaDevices.getUserMedia(constraints);
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
				setTabbarOn(false);
			}

			closePhoto();
		};
	}, [setTabbarOn, stream, videoRef]);

	useEffect(() => {
		// TODOS: API 요청해서 값 가져오기
		if (base64Image !== undefined && base64Image !== '') {
			getOCRReceiptIngredients(uuid(), decodingImage(base64Image))
				.then((res) => {
					if (res.status === 200) {
						setReceiptData(res.data);
						setIsLoading(false);
					}
				})
				.catch((err) => {
					if (err.response.status === 500 || err.response.status === 400) {
						toast.error('인식할 수 없는 영수증입니다. 다시 찍어주세요.');
						setHasPhoto(false);
						setIsLoading(false);
						setBase64Image('');
					}
					console.error(err);
				});
		}
	}, [base64Image, hasPhoto, setReceiptList]);

	return (
		<>
			{isLoading && <LoadingSpinner />}
			<div className="video-container">
				<video ref={videoRef} className="video" />
			</div>
			<div className={classNames('canvas-container', hasPhoto && 'has-photo')}>
				<div className="receipt-data-container">
					<ul className="receipt-data-list">
						{receiptData &&
							receiptData.map((data) => (
								<li key={data.ingredientId} className="receipt-data-item">
									{data.ingredientName}
								</li>
							))}
					</ul>
				</div>
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
