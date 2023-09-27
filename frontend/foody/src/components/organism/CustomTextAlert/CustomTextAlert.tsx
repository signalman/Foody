import React from 'react';
import 'react-confirm-alert/src/react-confirm-alert.css';
import './CustomTextAlert.scss';
import { confirmAlert } from 'react-confirm-alert';
import classNames from 'classnames';
import { BsTrash } from 'react-icons/bs';

interface ICustomAlertProps {
	title: string;
	desc?: string;
	contents?: JSX.Element;
	isDelete?: boolean;
	confirmTitle?: string;
	btnTitle: string;
	params: object;
	onAction: (params: object) => void;
	onDelete?: (params: object) => void;
}

function CustomAlert(props: ICustomAlertProps) {
	const { title, desc, contents, isDelete, confirmTitle, btnTitle, params, onAction, onDelete } = props;
	// 제목, 내용, 버튼 내용, 인자, confirm 함수, close 함수
	confirmAlert({
		customUI: ({ onClose }) => (
			<div className="popup-overlay">
				<div className="contents-group">
					<div className="title-container">
						<h3>{title}</h3>
						{isDelete && (
							<button
								type="button"
								onClick={() => {
									if (onDelete) {
										onDelete(params);
									}
									onClose();
								}}
							>
								<BsTrash size={19} />
							</button>
						)}
					</div>
					{desc && (
						<p>
							{desc.split('\n').map((line) => (
								<React.Fragment key={line}>
									{line}
									<br />
								</React.Fragment>
							))}
						</p>
					)}
					{contents && contents}
				</div>
				<div className="btn-group">
					{confirmTitle && (
						<button
							type="button"
							onClick={() => {
								onAction(params);
								onClose();
							}}
							className={classNames('btn', 'btn-confirm')}
						>
							{confirmTitle}
						</button>
					)}
					<button type="button" onClick={onClose} className="btn-cancel">
						<span className="txt-wrap">{btnTitle}</span>
					</button>
				</div>
			</div>
		),
	});
}

export default CustomAlert;
