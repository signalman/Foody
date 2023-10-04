/* eslint-disable jsx-a11y/media-has-caption */
import React, { useEffect, useRef, useState } from 'react';
import './MealCamera.scss';
import { BiCamera } from 'react-icons/bi';
import tabbarState from 'recoil/atoms/tabbarState';
import { useRecoilState } from 'recoil';
import { getMealNutrient, mealCamera, postRegistMeal } from 'utils/api/meal';
import { RegistMeal, RegistSendData } from 'types/meal';
import toast from 'react-hot-toast';
import SubHeader from 'components/organism/SubHeader/SubHeader';

interface MealCameraProps {
	sendMeal: string;
	selectedDate: string;
	setOpen: React.Dispatch<React.SetStateAction<boolean>>;
}
function MealCamera({ sendMeal, selectedDate, setOpen }: MealCameraProps) {
	const [, setTabbarOn] = useRecoilState(tabbarState);
	const videoRef = useRef<HTMLVideoElement | null>(null);
	const photoRef = useRef<HTMLCanvasElement | null>(null);
	const [stream, setStream] = useState<MediaStream | null>(null);

	// 사진 찍었을 때 사용할 File, photo 정의
	const [captureImg, setCaptureImg] = useState<File | null>(null);
	const [subImg, setSubImg] = useState<File | null>(null);
	const [photoImg, setPhotoImg] = useState<HTMLCanvasElement | null>(null);
	// const [croppedImages, setCroppedImages] = useState<string[]>([]);

	// 사진 찍었을 때 음식 데이터 영양소 값 가져오기
	// const [registMeal, setRegistMeal] = useState<RegistMeal | null>(null);
	const [sendData, setSendData] = useState<RegistMeal[]>([]);
	const [subImgArray, setSubImgArray] = useState<File[] | []>([]);
	const [test, setTest] = useState<{ name: string; x1: number; y1: number; x2: number; y2: number }[] | []>([]);

	const [complete, setComplete] = useState<boolean>(false);

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
			// console.log(blob);
			if (blob) {
				setCaptureImg(new File([blob], 'capture.jpg', { type: 'image/jpeg' }));
			}
		});

		setPhotoImg(photo);
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

	const handleMove = () => {
		setOpen(false);
	};

	useEffect(() => {
		setTabbarOn(false);

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
		if (captureImg) {
			mealCamera(captureImg).then((response) => {
				if (response.data.length === 0) {
					toast.error('음식 사진 인식이 되지 않습니다!');
				}
				setTest(response.data);
			});
		}
	}, [captureImg, photoImg]);

	useEffect(() => {
		if (test && test.length > 0) {
			const fetchData = async () => {
				test.forEach((item) => {
					const { name, x1, y1, x2, y2 } = item;

					const croppedCanvas = document.createElement('canvas');
					croppedCanvas.width = x2 - x1;
					croppedCanvas.height = y2 - y1;
					const croppedCtx = croppedCanvas.getContext('2d');

					if (!croppedCtx || !photoImg) return;
					croppedCtx.drawImage(photoImg, x1, y1, x2 - x1, y2 - y1, 0, 0, x2 - x1, y2 - y1);

					croppedCanvas.toBlob((blob) => {
						if (blob) {
							setSubImg(new File([blob], 'cropped_image.jpg', { type: 'image/jpeg' }));

							// croppedImgFile을 API로 전송하거나 사용할 작업 수행
							// console.log(croppedImgFile);
							URL.createObjectURL(blob);
							// setCroppedImages((prevImages) => [...prevImages, URL.createObjectURL(blob)]);
						}
					}, 'image/jpeg');

					getMealNutrient(name).then((nutrientResponse) => {
						console.log(nutrientResponse);
						setSendData((prev) => [
							...prev,
							{
								name: nutrientResponse.data.name,
								nutrientRequest: {
									energy: nutrientResponse.data.energy,
									carbohydrates: nutrientResponse.data.carbohydrates,
									protein: nutrientResponse.data.protein,
									dietaryFiber: nutrientResponse.data.dietaryFiber,
									calcium: nutrientResponse.data.calcium,
									sodium: nutrientResponse.data.sodium,
									iron: nutrientResponse.data.iron,
									fats: nutrientResponse.data.fats,
									vitaminA: nutrientResponse.data.vitaminA,
									vitaminC: nutrientResponse.data.vitaminC,
								},
							},
						]);
					});
				});
			};

			fetchData();
		}
	}, [photoImg, test]);

	useEffect(() => {
		if (subImg !== null) {
			setSubImgArray((prev) => {
				console.log('subImg', [...prev, subImg]);
				return [...prev, subImg];
			});
		}
	}, [subImg]);

	useEffect(() => {
		if (test.length !== 0 && test.length === sendData.length) {
			const totalData: RegistSendData = {
				type: sendMeal,
				date: selectedDate,
				foodRequestList: sendData,
			};

			console.log('요청!!!!!!!!!!!!!!!!!!!!');
			console.log('totalData', totalData);
			console.log('captureImg', captureImg);
			console.log('subImgArray', subImgArray);

			postRegistMeal(totalData, captureImg, subImgArray).then((response) => {
				console.log('postRegistMeal!!!!!', response);
				setComplete(false);
				setTest([]);
				setSendData([]);
				setOpen(false);
			});
		}
	}, [captureImg, complete, selectedDate, sendData, sendMeal, setOpen, subImgArray, test.length]);

	return (
		<>
			<SubHeader handleMove={handleMove} isBack title="카메라로 등록하기" />
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
			{/* <div className="cropped-images-container"> */}
			{/* 크롭된 이미지의 URL 목록을 매핑하고 이미지를 표시합니다. */}
			{/* {croppedImages.map((imageUrl) => (
					<div>
						<img key={imageUrl} src={imageUrl} alt="" className="cropped-image" />
						<p>{imageUrl}</p>
					</div>
				))} */}
			{/* </div> */}
		</>
	);
}

export default MealCamera;
