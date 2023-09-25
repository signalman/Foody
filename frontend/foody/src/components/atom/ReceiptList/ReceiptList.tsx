import React from 'react';
import 'ReceiptList.scss';
import ReceiptListType from 'types/receipt';

interface BoundingBoxProps {
	vertices: {
		x: number;
		y: number;
	}[];
}

function BoundingBox({ vertices }: BoundingBoxProps) {
	const points = vertices.map((vertex) => `${vertex.x},${vertex.y}`).join(' ');

	return <polygon className="bounding-box" points={points} />;
}

function ReceiptList({
	containerSize,
	receiptList,
}: {
	containerSize: { width: number; height: number };
	receiptList: ReceiptListType;
}) {
	return (
		<div
			className="receipt-list-conatiner receipt-list"
			style={{
				width: `${containerSize.width}px`,
				height: `${containerSize.height}px`,
			}}
		>
			<svg>
				{receiptList.boundingPolys.map((item, idx) => (
					<g key={item.vertices[idx].x}>
						<BoundingBox key={item.vertices[idx].x} vertices={item.vertices} />
					</g>
				))}
			</svg>
		</div>
	);
}

export default ReceiptList;
