import React from 'react';
import 'react-confirm-alert/src/react-confirm-alert.css';
import './CustomTextAlert.scss';
import { confirmAlert } from 'react-confirm-alert';
import classNames from 'classnames';
import { BsTrash } from 'react-icons/bs';

interface ICustomTextAlertProps {
	title: string;
	desc?: string;
	contents?: JSX.Element;
	isDelete?: boolean;
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
	const { title, desc, contents, isDelete, confirmBtnTitle, closeBtnTitle, params, onAction } = props;
	// 제목, 내용, 버튼 내용, 인자, confirm 함수, close 함수
	return confirmAlert({
		customUI: ({ onClose }) => (
			<div className="popup-overlay">
				<div className="contents-group">
					<div className="title-container">
						<h3>{title}</h3>
						{isDelete && (
							<button
								type="button"
								onClick={() => {
									onAction(params);
									onClose();
								}}
							>
								<BsTrash size={19} />
							</button>
						)}
					</div>
					{desc && (
						<p>
							<Item text={`${desc}`} />
						</p>
					)}
					{contents && contents}
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
