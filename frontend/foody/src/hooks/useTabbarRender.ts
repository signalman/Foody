import { useLocation } from 'react-router-dom';
import { useCallback, useEffect, useState } from 'react';
import NOT_ARROWED_PATH from 'constants/noTabbarPageList';
import { useRecoilState } from 'recoil';
import tabbarState from 'recoil/atoms/tabbarState';

/**
 * 허용되지 않은 경로에 대해 Tabbar의 표시 유무를 확인하는 커스텀 훅.
 * @returns true (Tabbar 보여짐) / false (Tabbar 숨김)
 */
function useTabbarRender(newPath: string | RegExp = '') {
	const [isTabbarRender, setIsTabbarRender] = useState(false);
	const location = useLocation();
	const [, setTabbar] = useRecoilState(tabbarState);

	useEffect(() => {
		const handleBackButton = () => {
			// 뒤로가기 버튼이 눌릴 때 Recoil Atom 초기화
			setTabbar(true); // initialValue에는 Atom의 초기값을 넣어주세요
		};

		// 뒤로가기 이벤트를 감지할 때마다 handleBackButton 함수가 실행됩니다.
		window.addEventListener('popstate', handleBackButton);

		// // 컴포넌트가 언마운트될 때 이벤트 리스너를 제거합니다.
		// return () => {
		// 	window.removeEventListener('popstate', handleBackButton);
		// };
	}, [setTabbar]);

	const excludedCheck = useCallback(
		(path_list: (string | RegExp)[]) => {
			return path_list.some((path) => {
				if (typeof path === 'string') {
					return path === location.pathname;
				}
				if (path instanceof RegExp) {
					return path.test(location.pathname);
				}
				return false;
			});
		},
		[location.pathname],
	);

	useEffect(() => {
		const pathList = NOT_ARROWED_PATH;

		if (newPath !== '') {
			pathList.push(newPath);
		}

		const isExcludedPath = excludedCheck(pathList);
		setIsTabbarRender(!isExcludedPath);
	}, [excludedCheck, location, newPath]);

	return isTabbarRender;
}

export default useTabbarRender;
