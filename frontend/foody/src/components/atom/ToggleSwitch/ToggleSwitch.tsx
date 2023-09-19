import React from 'react';
import './ToggleSwitch.scss';
import useToggle from 'hooks/useToggle';

interface ToggleSwitchProps {
	isRefri: boolean;
	onText: string;
	offText: string;
}

function ToggleSwitch({ isRefri, onText, offText }: ToggleSwitchProps) {
	const [check, setCheck] = useToggle(isRefri);
	console.log(check);
	return (
		<div className="flex justify-center">
			<div className={`toggle-switch ${check ? 'checked' : 'unchecked'}`}>
				<button
					type="button"
					className={`switch ${check ? 'checked' : 'unchecked'}`}
					onClick={() => {
						setCheck();
					}}
				>
					<span className={`on-text ml-2 ${check ? 'checked' : 'unchecked'}`}>{onText}</span>
					<span className={`off-text ml-16 ${check ? 'checked' : 'unchecked'}`}>{offText}</span>
					<div className={`slider ${check ? 'checked' : 'unchecked'}`} />
				</button>
			</div>
		</div>
	);
}

export default ToggleSwitch;
