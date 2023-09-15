# # 빌드 단계
# FROM node:16 AS build

# WORKDIR /app

# # 의존성 설치
# COPY package.json package-lock.json ./
# RUN npm install

# # 소스 코드 복사 및 빌드
# COPY . ./
# RUN npm run build

# # 런타임 단계
# FROM nginx:alpine

# # 빌드 단계에서 생성된 빌드 아티팩트를 Nginx에 복사
# COPY --from=build /app/build /usr/share/nginx/html

# run rm /etc/nginx/conf.d/default.conf

# COPY ./nginx.conf /etc/nginx/conf.d

# # Nginx 포트 설정 (생략 가능)
# EXPOSE 3000

# # Nginx 시작
# CMD ["nginx", "-g", "daemon off;"]
FROM nginx

WORKDIR /app

RUN mkdir ./build

ADD ./build ./build

run rm /etc/nginx/conf.d/default.conf

COPY ./nginx.conf /etc/nginx/conf.d

EXPOSE 3000

CMD [ "nginx", "-g", "daemon off;" ]
