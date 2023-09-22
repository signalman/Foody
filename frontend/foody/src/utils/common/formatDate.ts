import moment from 'moment';

/**
 * Date 객체를 0000-00-00과 같은 형태로 바꿔주는 함수
 * @param date Date 객체
 * @returns 0000-00-00 형태의 string 날짜
 */
const formatDate = (date: Date | string): string => {
	return moment(date).format('YYYY-MM-DD');
};

export default formatDate;
