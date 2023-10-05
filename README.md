<div align=center>
<img src="https://github.com/kkyu-min/AlgoRhythmAndBlues/assets/81220782/826147bb-3551-4cf8-b383-7671395cf4c9" width=300/>
</div>

## 서비스 소개
<div align=center>

사용자의 부족 영양소를 분석하여 맞춤 식단(음식)을 추천해 주는 서비스

사용자의 입맛을 분석하여 맞춤 음식을 추천해주는 서비스

냉장고 재료를 분석하여 냉장고 재료를 활용할 수 있는 음식 레시피 제공 서비스


[FOODY 바로가기](https://j9c106.p.ssafy.io)

</div>

## 프로젝트 개요


**1. 진행 기간**
    
    2023.08.28 ~ 2023.10.06(6주)
    
**2. 주요 기능**

    - 사용자의 식사를 AI 기술을 활용하여 사진으로 음식과 영양소를 분석
    - 일일 섭취 권장 영양소 대비 부족한 영양소에 대한 정보 제공
    - 사용자 취향과 부족 영양소에 대한 지표를 기준으로 식사 추천
    - 소유하고 있는 냉장고 재료오 부족 영양소에 기반한 추천
    - 유사한 다른 유저, 선호도를 종합한 추천하기 위한 다양한 알고리즘
    - OCR 기술을 통해 구매한 음식 재료에 대해 냉장고 현황 현실화

**3. 팀원 소개**
| 이름 | 역할 | 개발 내용 |
|:--:|:--:|:--:|
|신호인|팀장, Back-end| 추후 작성 예정|
|신상원|Back-end| 추후 작성 예정|
|이규민|Back-end| 추후 작성 예정|
|조희라|Back-end| 추후 작성 예정|
|임희선|Front-end| 추후 작성 예정|
|김승현|Front-end| 추후 작성 예정|



**4. 컨벤션**



## 기술 특이점

- OCR 기술 활용한 영수증 인식 및 카테고리화
- 이미지 분류 인공지능 모델인 YOLO모델에 추가학습을 진행하여 음식 사진 인식 및 분석
- 10만개 이상의 비정형 음식데이터 가공
- 사용자의 선호도(취향)에 맞는 음식 추천 및 추천 로직 최적화
- 비정형 레시피 데이터의 전처리 Flask서버, FastAPI 서버를 이용한 yolo와 빅데이터 추천 시스템 분리
- Redis를 활용한 검색어 자동완성 기능 최적화



## 주요 화면

추후 작성 예정

## 사용 기술

- FrontEnd
    - TypeScript : 4.9.5
    - React : 18.2.0
    - Recoil
    - Axios
    - styled-component
    - ESLint
    - Prettier
- BackEnd
    - JAVA : 17
    - Kotlin - smile(python의 sklearn과 대응, kmath - numpy와 대응)
    - Spring : 2.7.15
        - Spring Data JPA
        - Spring Security
        - Spring Rest Docs
    - MySQL
    - Redis
- CI/CD
    - AWS EC2
    - Jenkins
    - Docker - 24.0.6, build ed223bc
    - Amazon S3
    - NginX - nginx/1.18.0 (Ubuntu)
- Tool
    - Git
    - Jira
    - Notion
    - Mattermost
    - Figma
    - VS Code
    - Intellij



## 프로젝트 구조

1. 아키텍쳐

    추후 추가 예정


2. 와이어 프레임
    [자세히 보기](./exec/wireframe/wireframe.md)


3. ERD
    <img src="https://github.com/kkyu-min/AlgoRhythmAndBlues/assets/81220782/c9177de4-1357-48ae-8d87-e7cc9cb8d88f">
