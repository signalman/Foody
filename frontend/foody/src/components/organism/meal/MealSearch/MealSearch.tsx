import React, { Dispatch, useEffect, useState } from 'react';
import './MealSearch.scss';
import { useRecoilState } from 'recoil';
import tabbarState from 'recoil/atoms/tabbarState';
import InputSearch from 'components/atom/InputSearch/InputSearch';
import SearchTemplate from 'components/template/SearchTemplate/SearchTemplate';
import { HiPencil } from 'react-icons/hi';
import LargeButton from 'components/atom/LargeButton/LargeButton';
import LargeButtonColor from 'constants/color';
import { CustomIngredientItemType } from 'types/refrigerator';
import toast from 'react-hot-toast';
import FloatingMenu from 'components/molecule/FloatingMenu/FloatingMenu';
import IngredientRegistAlbum from 'components/organism/IngredientRegistAlbum/IngredientRegistAlbum';
import { getMealNutrient, getSearchMeal, postRegistMealText } from 'utils/api/meal';
import MealSearchResultList from 'components/molecule/MealSearchResultList/MealSearchResultList';
import SelectedMealList from 'components/molecule/SelectedMealList/SelectedMealList';
import { RegistMeal, RegistSendData } from 'types/meal';
import MealCamera from 'components/atom/MealCamera/MealCamera';
import SubHeader from '../../SubHeader/SubHeader';

interface IngredientSearchProps {
	setOpen: Dispatch<React.SetStateAction<boolean>>;
	meal: string;
	selectedDate: string;
}

function removeItemFromArray(item: string, array: string[]): string[] {
	const index = array.indexOf(item);
	if (index !== -1) {
		array.splice(index, 1);
	}
	return array;
}

function MealSearch(props: IngredientSearchProps) {
	const { setOpen, meal, selectedDate } = props;
	const [tabbarOn, setTabbarOn] = useRecoilState(tabbarState);
	setTabbarOn(false);
	const [searchKeyword, setSearchKeyword] = useState<string>('');
	const [searchResultList, setSearchResultList] = useState<string[] | null>(null);
	const [selectedMealList, setSelectedMealList] = useState<string[] | null>(null);
	const [customIngredientList, setCustomIngredientList] = useState<CustomIngredientItemType[] | []>([]);
	const [menuOpen, setMenuOpen] = useState(false);
	const [selectedMenu, setSelectedMenu] = useState<string | null>(null);
	const [sendMeal, setSendMeal] = useState<string>('');
	const [registMeal, setRegistMeal] = useState<RegistMeal>({
		name: '',
		nutrientRequest: {
			energy: 0,
			carbohydrates: 0,
			protein: 0,
			dietaryFiber: 0,
			calcium: 0,
			sodium: 0,
			iron: 0,
			fats: 0,
			vitaminA: 0,
			vitaminC: 0,
		},
	});

	const [sendData, setSendData] = useState<RegistMeal[]>([]);

	const handleMenuSelect = (menu: string) => {
		setMenuOpen(!menuOpen);
		setSelectedMenu(menu);
		setTabbarOn(!tabbarOn);
	};

	const handleMove = () => {
		setOpen((prev) => !prev);
		setTabbarOn(!tabbarOn);
	};

	const handleWrite = () => {
		console.log('handleWrite');
		toast.success('재료 직접 등록');
		setCustomIngredientList([
			...customIngredientList,
			{
				ingredientCategoryId: 2,
				ingredientName: '닭고기',
			},
		]);
	};

	const handleMealSelect = (data: string) => {
		getMealNutrient(data).then((response) => {
			console.log(response);
			setRegistMeal({
				name: response.data.name,
				nutrientRequest: {
					energy: response.data.energy,
					carbohydrates: response.data.carbohydrates,
					protein: response.data.protein,
					dietaryFiber: response.data.dietaryFiber,
					calcium: response.data.calcium,
					sodium: response.data.sodium,
					iron: response.data.iron,
					fats: response.data.fats,
					vitaminA: response.data.vitaminA,
					vitaminC: response.data.vitaminC,
				},
			});
		});

		setSelectedMealList((prevList) => [...(prevList || []), data]);
		console.log(selectedMealList);
	};

	const handleDelete = (data: string) => {
		if (selectedMealList) {
			setSelectedMealList(removeItemFromArray(data, selectedMealList));
		}
	};
	useEffect(() => {
		setSendData((prev) => [...prev, registMeal]);
	}, [registMeal]);

	const handleSubmitMealRegist = () => {
		if (selectedMealList && selectedMealList.length !== 0) {
			console.log('등록');

			console.log(meal);
			console.log(sendMeal);
			const totalData: RegistSendData = {
				type: sendMeal,
				date: selectedDate,
				foodRequestList: sendData,
			};

			console.log(totalData);
			// const emptyFile2 = new File([], 'empty-image', { type: 'image/jpeg' });
			// const temp = [emptyFile2];
			postRegistMealText(totalData).then((response) => {
				console.log(response);
			});
		}
	};

	useEffect(() => {
		if (searchKeyword !== '') {
			getSearchMeal(searchKeyword)
				.then((res) => {
					if (res.data && res.data.length > 0) {
						setSearchResultList(res.data);
						return;
					}
					setSearchResultList(null);
				})
				.catch((err) => console.error(err));
		}
	}, [searchKeyword]);

	useEffect(() => {
		if (meal === '아침') {
			setSendMeal('BREAKFAST');
		} else if (meal === '점심') {
			setSendMeal('LUNCH');
		} else if (meal === '저녁') {
			setSendMeal('DINNER');
		} else {
			setSendMeal('SNACK');
		}
	}, [meal]);

	if (menuOpen) {
		if (selectedMenu === 'camera') return <MealCamera />;
		if (selectedMenu === 'album') return <IngredientRegistAlbum setOpen={setMenuOpen} />;
		return <MealSearch meal={meal} setOpen={setMenuOpen} selectedDate={selectedDate} />;
	}

	return (
		<div className="meal-regist-album-container">
			<SubHeader isBack title="식단 찾기" handleMove={handleMove} />
			<SearchTemplate>
				{/* 인풋 */}
				<InputSearch value={searchKeyword} placeholder="식단 이름을 입력하세요" onChangeValue={setSearchKeyword} />

				{/* 직접 입력하기 버튼 */}
				<LargeButton
					buttonColor={LargeButtonColor.Gray}
					value="직접 입력하기"
					buttonClick={handleWrite}
					icon={HiPencil}
				/>

				{/* 선택된 재료 */}
				<SelectedMealList onDelete={handleDelete} selectedMealList={selectedMealList} />
				{/* 검색 결과 */}
				<MealSearchResultList
					searchKeyword={searchKeyword}
					searchResultList={searchResultList}
					handleMealSelect={handleMealSelect}
				/>

				{/* Floating 메뉴 */}
				<FloatingMenu menuList={['camera', 'album']} onMenuSelect={handleMenuSelect} />

				{/* 검색/등록 버튼 */}
				<LargeButton
					buttonColor={
						selectedMealList && selectedMealList.length !== 0 ? LargeButtonColor.Green : LargeButtonColor.Gray
					}
					value="식단 등록하기"
					buttonClick={handleSubmitMealRegist}
				/>
			</SearchTemplate>
		</div>
	);
}

export default MealSearch;
