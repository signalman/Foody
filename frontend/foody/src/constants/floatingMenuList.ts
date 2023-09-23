import { BiCamera, BiSearch } from 'react-icons/bi';
import { AiOutlinePicture } from 'react-icons/ai';
import { IconType } from 'react-icons/lib';

enum FloatingMenuList {
	camera = '카메라',
	album = '앨범',
	search = '검색',
}

export type FloatingMenuListIconsType = {
	[key in string]: IconType;
};

export const FloatingMenuListIcons: FloatingMenuListIconsType = {
	camera: BiCamera,
	album: AiOutlinePicture,
	search: BiSearch,
};

export default FloatingMenuList;
