import React, { Dispatch } from 'react';
import './ToggleSwitch.scss';

interface ToggleSwitchProps {
	type: boolean;
	setType: Dispatch<React.SetStateAction<boolean>>;
	onText: string;
	offText: string;
}

function ToggleSwitch({ type, setType, onText, offText }: ToggleSwitchProps) {
	return (
		<div className={`toggle-switch ${type ? 'checked' : 'unchecked'}`}>
			<button
				type="button"
				className={`switch ${type ? 'checked' : 'unchecked'}`}
				onClick={() => {
					setType((prev) => !prev);
				}}
			>
				<span className={`on-text ml-2 ${type ? 'checked' : 'unchecked'}`}>{onText}</span>
				<span className={`off-text ml-16 ${type ? 'checked' : 'unchecked'}`}>{offText}</span>
				<div className={`slider ${type ? 'checked' : 'unchecked'}`} />
			</button>
		</div>
	);
}

export default ToggleSwitch;
