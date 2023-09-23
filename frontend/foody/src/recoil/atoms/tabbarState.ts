import { atom } from 'recoil';

// Tabbar 상태를 저장
const tabbarState = atom({
	key: 'tabbarState',
	default: true, // on일 경우 true
});

export const quickTodayMealRegiState = atom({
	key: 'quickTodayMealRegiState',
	default: false, // off일 경우 true
});

export default tabbarState; // 변수를 기본 내보내기로 설정
