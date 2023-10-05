import React from 'react';
import 'styles/index.scss';
import { BrowserRouter, Navigate, Route, Routes } from 'react-router-dom';
import HomePage from 'components/page/HomePage';
import MealPage from 'components/page/MealPage';
import RefriPage from 'components/page/RefriPage';
import RecommendPage from 'components/page/RecommendPage';
import DevPage from 'components/page/DevPage';
import LoginPage from 'components/page/LoginPage';
import NotFoundPage from 'components/page/NotFoundPage';
import SignupPage from 'components/page/SignupPage';
import RegistTodayMeal from 'components/molecule/RegistTodayMeal/RegistTodayMeal';
import { Toaster } from 'react-hot-toast';
import FoodMBTI from 'components/page/FoodMBTI';
import RecipeDetailPage from 'components/page/RecipeDetailPage';
import ScrollToTop from 'components/atom/ScrollToTop/ScrollToTop';
import BookmarkPage from 'components/page/BookmarkPage';

function AppRouter() {
	return (
		<BrowserRouter>
			<Routes>
				<Route path="/" element={<Navigate replace to="/home" />} />
				<Route path="/home" element={<HomePage />} />
				<Route path="/meal" element={<MealPage />} />
				<Route path="/refri" element={<RefriPage />} />
				<Route path="/recommend" element={<RecommendPage />} />
				<Route path="/recipe/:id" element={<RecipeDetailPage />} />
				<Route path="/bookmark" element={<BookmarkPage />} />
				<Route path="/login" element={<LoginPage />} />
				<Route path="/signup" element={<SignupPage />} />
				<Route path="/fbti" element={<FoodMBTI />} />
				<Route path="/dev" element={<DevPage />} />

				{/* <Route path="/*" element={<Navigate replace to="/notfound" />} /> */}
				<Route path="/notfound" element={<NotFoundPage />} />
			</Routes>

			<RegistTodayMeal />
			<ScrollToTop />
			<Toaster
				containerStyle={{
					top: 10,
					fontSize: 14,
				}}
				toastOptions={{
					duration: 1500,
				}}
			/>
		</BrowserRouter>
	);
}

export default AppRouter;
