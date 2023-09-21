import React from 'react';
import 'react-confirm-alert/src/react-confirm-alert.css';
import './CustomTextAlert.scss';
import { confirmAlert } from 'react-confirm-alert';
import classNames from 'classnames';

interface ICustomTextAlertProps {
	title: string;
	desc: string;
	confirmBtnTitle?: string;
	closeBtnTitle: string;
	params: object;
	onAction: (params: object) => void;
}

function Item({ text }: { text: string }) {
	return (
		<p>
			{text.split('\n').map((txt) => (
				<>
					{txt}
					<br />
				</>
			))}
		</p>
	);
}

function CustomTextAlert(props: ICustomTextAlertProps) {
	const { title, desc, confirmBtnTitle, closeBtnTitle, params, onAction } = props;
	// 제목, 내용, 버튼 내용, 인자, confirm 함수, close 함수
	return confirmAlert({
		customUI: ({ onClose }) => (
			<div className="popup-overlay">
				<div>
					<h3>{title}</h3>
					<p>
						<Item text={desc} />
					</p>
				</div>
				<div className="btn-group">
					{confirmBtnTitle && (
						<button
							type="button"
							onClick={() => {
								onAction(params);
								onClose();
							}}
							className={classNames('btn', 'btn-danger')}
						>
							{confirmBtnTitle}
						</button>
					)}
					<button type="button" onClick={onClose} className="btn btn-cancel">
						{closeBtnTitle}
					</button>
				</div>
			</div>
		),
	});
}

export default CustomTextAlert;
