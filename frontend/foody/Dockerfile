# 빌드 단계
FROM node:16 AS build

WORKDIR /app

# 매개변수 선언
ARG REACT_APP_SERVER_BASE_URL
ARG REACT_APP_OAUTH_SERVER_URL
ARG REACT_APP_OAUTH_DEVELOPE_URL

# 환경 변수로 설정
ENV REACT_APP_SERVER_BASE_URL=${REACT_APP_SERVER_BASE_URL}
ENV REACT_APP_OAUTH_SERVER_URL=${REACT_APP_OAUTH_SERVER_URL}
ENV REACT_APP_OAUTH_DEVELOPE_URL=${REACT_APP_OAUTH_DEVELOPE_URL}

# 의존성 설치
COPY package.json package-lock.json ./
RUN npm install

# 소스 코드 복사 및 빌드
COPY . ./
RUN npm run build

# 런타임 단계
FROM nginx:alpine

# 빌드 단계에서 생성된 빌드 아티팩트를 Nginx에 복사
COPY --from=build /app/build /usr/share/nginx/html

# Nginx 포트 설정 (생략 가능)
EXPOSE 80

# Nginx 시작
CMD ["nginx", "-g", "daemon off;"]
