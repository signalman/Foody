/* eslint-disable jsx-a11y/media-has-caption */
import React, { useEffect, useRef, useState } from 'react';
import './MealCamera.scss';
import { BiCamera } from 'react-icons/bi';
import tabbarState from 'recoil/atoms/tabbarState';
import { useRecoilState } from 'recoil';
import { mealCamera } from 'utils/api/meal';
import LoadingSpinner from '../LoadingSpinner/LoadingSpinner';

function MealCamera() {
	const [, setTabbarOn] = useRecoilState(tabbarState);
	setTabbarOn(false);
	const videoRef = useRef<HTMLVideoElement | null>(null);
	const photoRef = useRef<HTMLCanvasElement | null>(null);
	const [stream, setStream] = useState<MediaStream | null>(null);
	const [isLoading, setIsLoading] = useState(false);
	const [captureImg, setCaptureImg] = useState<File | null>(null);

	const takePhoto = () => {
		const video = videoRef.current;
		const photo = photoRef.current;
		console.log(video);
		console.log(photo);

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
	};

	useEffect(() => {
		console.log(captureImg);
		if (captureImg) {
			setIsLoading(false);
			mealCamera(captureImg).then((response) => {
				console.log(response);
			});
		}
	}, [captureImg]);

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
		</>
	);
}

export default MealCamera;
