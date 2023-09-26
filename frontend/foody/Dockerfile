# 빌드 단계
FROM node:16 AS build

WORKDIR /app

# 의존성 설치
COPY package.json package-lock.json ./
RUN npm install

# 소스 코드 복사 및 빌드
COPY . ./
RUN echo "REACT_APP_OAUTH_SERVER_URL: $REACT_APP_OAUTH_SERVER_URL"
RUN npm run build

# 런타임 단계
FROM nginx:alpine

# 기본 Nginx 설정 제거
RUN rm /etc/nginx/conf.d/default.conf

# 사용자 정의 Nginx 설정 복사
COPY ./nginx.conf /etc/nginx/conf.d

# 빌드 단계에서 생성된 빌드 아티팩트를 Nginx에 복사
COPY --from=build /app/build /app/build

# 시간대 설정
RUN apk add --no-cache tzdata
ENV TZ=Asia/Seoul

# Nginx 포트 설정 (생략 가능)
EXPOSE 80

# Nginx 시작
CMD ["nginx", "-g", "daemon off;"]
