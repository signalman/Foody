/* eslint-disable jsx-a11y/media-has-caption */
import React, { useEffect, useRef, useState } from 'react';
import './MealCamera.scss';
import { BiCamera } from 'react-icons/bi';
import tabbarState from 'recoil/atoms/tabbarState';
import { useRecoilState } from 'recoil';
import { getMealNutrient, mealCamera } from 'utils/api/meal';
import LoadingSpinner from '../LoadingSpinner/LoadingSpinner';

function MealCamera() {
	const [, setTabbarOn] = useRecoilState(tabbarState);
	setTabbarOn(false);
	const videoRef = useRef<HTMLVideoElement | null>(null);
	const photoRef = useRef<HTMLCanvasElement | null>(null);
	const [stream, setStream] = useState<MediaStream | null>(null);
	const [isLoading, setIsLoading] = useState(false);

	// 사진 찍었을 때 사용할 File, photo 정의
	const [captureImg, setCaptureImg] = useState<File | null>(null);
	const [photoImg, setPhotoImg] = useState<HTMLCanvasElement | null>(null);
	const [croppedImages, setCroppedImages] = useState<string[]>([]);

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

		photo.toBlob((blob) => {
			console.log(blob);
			if (blob) {
				setCaptureImg(new File([blob], 'image', { type: 'image/jpeg' }));
			}
			setIsLoading(true);
		});

		setPhotoImg(photo);
	};

	useEffect(() => {
		console.log(captureImg);
		if (captureImg) {
			setIsLoading(false);
			mealCamera(captureImg).then((response) => {
				console.log(response);
				response.data.forEach((item: { name: string; x1: number; y1: number; x2: number; y2: number }) => {
					const { name, x1, y1, x2, y2 } = item; // API 응답에서 좌표 추출

					getMealNutrient(name).then((response2) => {
						console.log(response2);
					});

					const croppedCanvas = document.createElement('canvas');
					croppedCanvas.width = x2 - x1;
					croppedCanvas.height = y2 - y1;
					const croppedCtx = croppedCanvas.getContext('2d');

					if (!croppedCtx || !photoImg) return;
					croppedCtx.drawImage(photoImg, x1, y1, x2 - x1, y2 - y1, 0, 0, x2 - x1, y2 - y1);

					croppedCanvas.toBlob((blob) => {
						if (blob) {
							const croppedImgFile = new File([blob], 'cropped_image', { type: 'image/jpeg' });

							// croppedImgFile을 API로 전송하거나 사용할 작업 수행
							console.log(croppedImgFile);
							URL.createObjectURL(blob);
							setCroppedImages((prevImages) => [...prevImages, URL.createObjectURL(blob)]);
						}
					}, 'image/jpeg');
				});
			});
		}
	}, [captureImg, photoImg]);

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

	return (
		<>
			{isLoading && <LoadingSpinner />}
			<div className="video-container">
				<video ref={videoRef} className="video" />
			</div>
			<div className="canvas-container">
				<canvas ref={photoRef} className="canvas" />
			</div>
			<div className="btn-container">
				<button type="button" onClick={takePhoto}>
					<BiCamera size={25} />
				</button>
			</div>
			<div className="cropped-images-container">
				{/* 크롭된 이미지의 URL 목록을 매핑하고 이미지를 표시합니다. */}
				{croppedImages.map((imageUrl) => (
					<img key={imageUrl} src={imageUrl} alt="" className="cropped-image" />
				))}
			</div>
		</>
	);
}

export default MealCamera;
