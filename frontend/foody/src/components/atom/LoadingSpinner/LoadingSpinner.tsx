import React from 'react';
import './LoadingSpinner.scss';
import { PuffLoader } from 'react-spinners';

function LoadingSpinner() {
	return (
		<div className="loading-spinner-container">
			<PuffLoader
				color="#36d7b7"
				cssOverride={{
					position: 'absolute',
					zIndex: 'inherit',
					top: '50%',
					left: '50%',
					transform: 'translate(-50%, -50%)',
				}}
				size={80}
			/>
		</div>
	);
}

export default LoadingSpinner;
