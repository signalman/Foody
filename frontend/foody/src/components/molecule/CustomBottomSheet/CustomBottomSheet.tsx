import React, { Dispatch, ReactNode } from 'react';
import { BottomSheet } from 'react-spring-bottom-sheet';
import 'react-spring-bottom-sheet/dist/style.css';
import SheetContent from 'components/atom/SheetContent/SheetContent';
import './CustomBottomSheet.scss';

interface CustomBottomSheetProps {
	children: ReactNode;
	open: boolean;
	setOpen: Dispatch<React.SetStateAction<boolean>>;
}

function CustomBottomSheet({ children, open, setOpen }: CustomBottomSheetProps) {
	return (
		<div className="custom-bottom-sheet-container">
			<BottomSheet open={open} onDismiss={() => setOpen(false)}>
				<SheetContent>
					<div className="sheetcontent-container no-scrollbar">{children}</div>
					{/* <div style={{ height: '60vh' }} /> */}
				</SheetContent>
			</BottomSheet>
		</div>
	);
}

export default CustomBottomSheet;
