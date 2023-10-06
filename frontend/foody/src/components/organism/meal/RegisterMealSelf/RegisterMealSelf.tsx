import MealInput from 'components/atom/MealInput/MealInput';
import './RegisterMealSelf.scss';
import React, { useEffect, useState } from 'react';
import LargeButton from 'components/atom/LargeButton/LargeButton';
import LargeButtonColor from 'constants/color';
import Layout from 'components/template/Layout/Layout';
import LayoutBottomMargin, { LayoutTopMargin } from 'constants/Margin';
import LayoutPadding from 'constants/Padding';
import SubHeader from 'components/organism/SubHeader/SubHeader';
import { RegistMeal } from 'types/meal';

interface RegisterMealSelfProps {
	setWrite: React.Dispatch<React.SetStateAction<boolean>>;
	setRegistMealk: React.Dispatch<React.SetStateAction<RegistMeal | null>>;
	setSelfName: React.Dispatch<React.SetStateAction<string>>;
}
function RegisterMealSelf({ setWrite, setRegistMealk, setSelfName }: RegisterMealSelfProps) {
	const [name, setName] = useState<string>('');
	const [cal, setCal] = useState<string>('');
	const [tan, setTan] = useState<string>('');
	const [dan, setDan] = useState<string>('');
	const [ji, setJi] = useState<string>('');
	const [sik, setSik] = useState<string>('');
	const [sume, setSume] = useState<string>('');
	const [na, setNa] = useState<string>('');
	const [ir, setIron] = useState<string>('');
	const [vitaA, setVitaA] = useState<string>('');
	const [vitaC, setVitaC] = useState<string>('');

	const inputNameCheck = (value: string) => {
		const hangeulicRegex = /^[가-힣ㄱ-ㅎㅏ-ㅣ]+$/;
		const isTrue = hangeulicRegex.test(value);

		if (isTrue === true || value === '') {
			return true;
		}

		return false;
	};

	const inputNutrientCheck = (value: string) => {
		const numbericRegex = /^[0-9]+$/;
		const isTrue = numbericRegex.test(value);

		if (isTrue === true || value === '') {
			return true;
		}

		return false;
	};

	const [check, setCheck] = useState<boolean>(false);

	useEffect(() => {
		if (name.length > 0 && cal.length > 0) {
			setCheck(true);
		} else {
			setCheck(false);
		}
	}, [name, cal]);

	const registerButton = () => {
		const energy = parseFloat(cal === '' ? '0' : cal);
		const carbohydrates = parseFloat(tan === '' ? '0' : tan);
		const protein = parseFloat(dan === '' ? '0' : dan);
		const dietaryFiber = parseFloat(sik === '' ? '0' : sik);
		const calcium = parseFloat(sume === '' ? '0' : sume);
		const sodium = parseFloat(na === '' ? '0' : na);
		const iron = parseFloat(ir === '' ? '0' : ir);
		const fats = parseFloat(ji === '' ? '0' : ji);
		const vitaminA = parseFloat(vitaA === '' ? '0' : vitaA);
		const vitaminC = parseFloat(vitaC === '' ? '0' : vitaC);

		setRegistMealk({
			name,
			nutrientRequest: {
				energy,
				carbohydrates,
				protein,
				dietaryFiber,
				calcium,
				sodium,
				iron,
				fats,
				vitaminA,
				vitaminC,
			},
		});
		setSelfName(name);
		setWrite(false);
	};

	const handleMove = () => {
		setWrite(false);
	};

	return (
		<div>
			<SubHeader title="직접 입력하기" isBack={false} handleMove={handleMove} />
			<Layout marginBottom={LayoutBottomMargin.mb20} marginTop={LayoutTopMargin.mt10} padding={LayoutPadding.p30}>
				<MealInput
					isValid={inputNameCheck}
					onChangeValue={setName}
					placeholder="ex. 김치찌개"
					requireIcon
					title="음식이름"
					unit=""
					value={name}
				/>
				<MealInput
					isValid={inputNutrientCheck}
					onChangeValue={setCal}
					placeholder="ex. 370"
					requireIcon
					title="1회 섭취 칼로리"
					unit="kcal"
					value={cal}
				/>
				<p className="register-info">영양정보</p>
				<MealInput
					isValid={inputNutrientCheck}
					onChangeValue={setTan}
					placeholder="ex. 100"
					requireIcon={false}
					title="탄수화물"
					unit="g"
					value={tan}
				/>
				<MealInput
					isValid={inputNutrientCheck}
					onChangeValue={setDan}
					placeholder="ex. 100"
					requireIcon={false}
					title="단백질"
					unit="g"
					value={dan}
				/>
				<MealInput
					isValid={inputNutrientCheck}
					onChangeValue={setJi}
					placeholder="ex. 100"
					requireIcon={false}
					title="지방"
					unit="g"
					value={ji}
				/>
				<MealInput
					isValid={inputNutrientCheck}
					onChangeValue={setSik}
					placeholder="ex. 100"
					requireIcon={false}
					title="식이섬유"
					unit="g"
					value={sik}
				/>
				<MealInput
					isValid={inputNutrientCheck}
					onChangeValue={setNa}
					placeholder="ex. 100"
					requireIcon={false}
					title="나트륨"
					unit="mg"
					value={na}
				/>
				<MealInput
					isValid={inputNutrientCheck}
					onChangeValue={setSume}
					placeholder="ex. 100"
					requireIcon={false}
					title="칼슘"
					unit="mg"
					value={sume}
				/>
				<MealInput
					isValid={inputNutrientCheck}
					onChangeValue={setIron}
					placeholder="ex. 100"
					requireIcon={false}
					title="철분"
					unit="mg"
					value={ir}
				/>
				<MealInput
					isValid={inputNutrientCheck}
					onChangeValue={setVitaA}
					placeholder="ex. 100"
					requireIcon={false}
					title="비타민A"
					unit="ug"
					value={vitaA}
				/>
				<MealInput
					isValid={inputNutrientCheck}
					onChangeValue={setVitaC}
					placeholder="ex. 100"
					requireIcon={false}
					title="비타민C"
					unit="mg"
					value={vitaC}
				/>
				<LargeButton
					buttonClick={registerButton}
					buttonColor={check ? LargeButtonColor.Green : LargeButtonColor.Gray}
					value="등록하기"
					imgsrc=""
				/>
			</Layout>
		</div>
	);
}

export default RegisterMealSelf;
