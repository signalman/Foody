import React from 'react';
import { Link } from 'react-router-dom';

function NotFoundPage() {
	return (
		<div>
			Not Found Page <Link to="/">홈으로</Link>
		</div>
	);
}

export default NotFoundPage;
