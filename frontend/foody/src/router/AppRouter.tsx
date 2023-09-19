import React from 'react';
import 'styles/index.scss';
import { BrowserRouter, Navigate, Route, Routes } from 'react-router-dom';
import HomePage from 'components/page/HomePage';
import MealPage from 'components/page/MealPage';
import RefriPage from 'components/page/RefriPage';
import RecommendPage from 'components/page/RecommendPage';
import DevPage from 'components/page/DevPage';
import NotFoundPage from 'components/page/NotFoundPage';

function AppRouter() {
	return (
		<BrowserRouter>
			<Routes>
				<Route path="/" element={<HomePage />} />
				<Route path="/meal" element={<MealPage />} />
				<Route path="/refri" element={<RefriPage />} />
				<Route path="/recommend" element={<RecommendPage />} />

				<Route path="/dev" element={<DevPage />} />

				<Route path="/*" element={<Navigate replace to="/notfound" />} />
				<Route path="/notfound" element={<NotFoundPage />} />
			</Routes>
		</BrowserRouter>
	);
}

export default AppRouter;
