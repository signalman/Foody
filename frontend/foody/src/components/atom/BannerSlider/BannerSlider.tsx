import React from 'react';
import 'swiper/css';
// import 'swiper/modules/navigation/navigation.scss'; // Navigation module
// import 'swiper/modules/pagination/pagination.scss'; // Pagination module
import './BannerSlider.scss';
import { Autoplay, Pagination, Navigation } from 'swiper/modules';
import { Swiper, SwiperSlide } from 'swiper/react';
import BANNER_IMG_LIST from 'constants/bannerImageList';

function BannerSlider() {
	return (
		<Swiper
			className="banner-slider-container"
			spaceBetween={20}
			centeredSlides
			autoplay={{
				delay: 4000,
				disableOnInteraction: false,
			}}
			pagination={{
				clickable: true,
			}}
			navigation
			modules={[Autoplay, Pagination, Navigation]}
		>
			{BANNER_IMG_LIST.map((item) => (
				<SwiperSlide key={item.id}>
					<img src={item.src} alt="" />
				</SwiperSlide>
			))}
		</Swiper>
	);
}

export default BannerSlider;
