/* eslint-disable jsx-a11y/media-has-caption */
import React, { useEffect, useRef, useState } from 'react';
import './OCRCamera.scss';
import { BiCamera } from 'react-icons/bi';

function OCRCamera() {
	const videoRef = useRef<HTMLVideoElement | null>(null);
	const photoRef = useRef<HTMLCanvasElement | null>(null);
	const [hasPhoto, setHasPhoto] = useState(false);
	const [stream, setStream] = useState<MediaStream | null>(null);
	const [base64Image, setBase64Image] = useState('');

	const takePhoto = () => {
		const width = 414;
		const height = width / (16 / 9);

		const video = videoRef.current;
		const photo = photoRef.current;

		if (!video || !photo) {
			return;
		}

		photo.width = width;
		photo.height = height;

		const ctx = photo?.getContext('2d');
		ctx?.scale(-1, 1);
		ctx?.translate(-1 * width, 0);
		ctx?.drawImage(video, 0, 0, width, height);
		ctx?.scale(1, 1);
		setHasPhoto(true);
	};

	const savePhoto = () => {
		takePhoto();

		const photo = photoRef.current;
		if (!photo) {
			return;
		}

		const imageDataUrl = photo.toDataURL('image/jpeg');
		setBase64Image(imageDataUrl);
		console.log('imageDataUrl', imageDataUrl);
		console.log('imageDataUrl split', imageDataUrl.split(',')[1]);

		const downloadLink = document.createElement('a');
		downloadLink.href = imageDataUrl;
		downloadLink.download = 'photo.jpg';
		downloadLink.click();
	};

	const closePhoto = () => {
		console.log(videoRef);
		console.log(photoRef);
		const photo = photoRef.current;
		if (!photo) {
			return;
		}

		const ctx = photo.getContext('2d');
		if (!ctx) {
			return;
		}

		ctx.clearRect(0, 0, photo.width, photo.height);
		setHasPhoto(false);
	};

	useEffect(() => {
		console.log('stream', stream);

		const getVideo = async () => {
			const constraints = {
				video: {
					width: 1920,
					height: 1280,
					facingMode: 'environment',
				},
			};

			try {
				await navigator.mediaDevices.getUserMedia(constraints).then((newStream) => {
					console.log('newStream', newStream);
					setStream(newStream);
					const video = videoRef.current;
					if (!video) {
						return;
					}
					video.srcObject = newStream;
					video.play();
				});
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

	return (
		<>
			<div className="camera">
				<video ref={videoRef} className="video-flip" />
				{/* <video ref={videoRef} /> */}
				<button
					type="button"
					onClick={savePhoto}
					style={{
						fontSize: '1.5rem',
					}}
				>
					저장
				</button>
				<br />
				<button
					type="button"
					onClick={closePhoto}
					style={{
						fontSize: '1.5rem',
					}}
				>
					다시찍기
				</button>
			</div>
			<div className={`result${hasPhoto ? 'hasPhoto' : ''}`}>
				<canvas ref={photoRef} />
			</div>
			<div className="btn-container">
				<button type="button" onClick={takePhoto}>
					<BiCamera size={25} />
				</button>
			</div>
			{base64Image}
		</>
	);
}

export default OCRCamera;
