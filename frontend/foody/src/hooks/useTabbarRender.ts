import { useLocation } from 'react-router-dom';
import { useCallback, useEffect, useState } from 'react';
import NOT_ARROWED_PATH from 'constants/noTabbarPageList';

/**
 * 허용되지 않은 경로에 대해 Tabbar의 표시 유무를 확인하는 커스텀 훅.
 * @returns true (Tabbar 보여짐) / false (Tabbar 숨김)
 */
function useTabbarRender(newPath: string | RegExp = '') {
	const [isTabbarRender, setIsTabbarRender] = useState(false);
	const location = useLocation();

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
