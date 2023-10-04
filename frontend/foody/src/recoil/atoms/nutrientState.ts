import { atom } from 'recoil';
import { recoilPersist } from 'recoil-persist';
import { NutrientTotal } from 'types/meal';

const { persistAtom } = recoilPersist();
// 전체영양소 상태를 저장
const nutrientState = atom<NutrientTotal>({
	key: 'nutrientState',
	default: {
		energy: 0,
		carbohydrates: 0,
		protein: 0,
		fats: 0,
		dietaryFiber: 0,
		calcium: 0,
		sodium: 0,
		iron: 0,
		vitaminA: 0,
		vitaminC: 0,
	}, // on일 경우 true
	effects_UNSTABLE: [persistAtom],
});

export const breakfastState = atom<NutrientTotal>({
	key: 'breakfastState',
	default: {
		energy: 0,
		carbohydrates: 0,
		protein: 0,
		fats: 0,
		dietaryFiber: 0,
		calcium: 0,
		sodium: 0,
		iron: 0,
		vitaminA: 0,
		vitaminC: 0,
	},
	effects_UNSTABLE: [persistAtom],
});

export const lunchState = atom<NutrientTotal>({
	key: 'lunchState',
	default: {
		energy: 0,
		carbohydrates: 0,
		protein: 0,
		fats: 0,
		dietaryFiber: 0,
		calcium: 0,
		sodium: 0,
		iron: 0,
		vitaminA: 0,
		vitaminC: 0,
	},
	effects_UNSTABLE: [persistAtom],
});

export const dinnerState = atom<NutrientTotal>({
	key: 'dinnerState',
	default: {
		energy: 0,
		carbohydrates: 0,
		protein: 0,
		fats: 0,
		dietaryFiber: 0,
		calcium: 0,
		sodium: 0,
		iron: 0,
		vitaminA: 0,
		vitaminC: 0,
	},
	effects_UNSTABLE: [persistAtom],
});

export const snackState = atom<NutrientTotal>({
	key: 'snackState',
	default: {
		energy: 0,
		carbohydrates: 0,
		protein: 0,
		fats: 0,
		dietaryFiber: 0,
		calcium: 0,
		sodium: 0,
		iron: 0,
		vitaminA: 0,
		vitaminC: 0,
	},
	effects_UNSTABLE: [persistAtom],
});

export default nutrientState; // 변수를 기본 내보내기로 설정
