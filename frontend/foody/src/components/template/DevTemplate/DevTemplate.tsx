import React from 'react';
import './DevTemplate.scss';

function DevTemplate() {
	return (
		<>
			<header>
				<h1>헤더입니다</h1>
			</header>

			<div className="container">
				<div className="content-default-container">
					<h2>h2입니다</h2>
					<div>h2의 컨텐츠입니다</div>
				</div>

				<div className="content-default-container">
					<h2>h2입니다</h2>
					<div>h2의 컨텐츠입니다</div>
				</div>

				<div className="content-default-container">
					<h2>h2입니다</h2>
					<div>h2의 컨텐츠입니다</div>
				</div>
			</div>

			<hr />

			<h1>바텀시트</h1>

			<div className="sheet-container">
				<div className="content-default-container">
					<h3>영양소</h3>
					<div>영양소 컨텐츠입니다</div>
				</div>
				<div className="content-default-container">
					<h3>영양소</h3>
					<div>영양소 컨텐츠입니다</div>
				</div>
			</div>

			<div className="container">
				<h1>h1</h1>
				<h2>h2</h2>
				<h3>h3</h3>
				<h4>h4</h4>
				<p className="p0">p0</p>
				<p className="p1">p1</p>
				<p className="p2">p2</p>
				<p className="p3">p3</p>
				<p className="p4">p4</p>

				<h1>h1</h1>
				<h2>h2</h2>
				<h3>h3</h3>
				<h4>h4</h4>
				<p className="p0">p0</p>
				<p className="p1">p1</p>
				<p className="p2">p2</p>
				<p className="p3">p3</p>
				<p className="p4">p4</p>
			</div>

			<nav className="tabbar-container">탭바입니다</nav>
		</>
	);
}

export default DevTemplate;
