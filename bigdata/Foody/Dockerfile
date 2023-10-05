# Uvicorn을 실행하기 위한 베이스 이미지 선택
FROM tiangolo/uvicorn-gunicorn-fastapi:python3.11

# 작업 디렉터리 설정
WORKDIR /app

# 의존성을 설치하기 위한 파일들 복사
COPY requirements.txt .

# 의존성 설치
RUN pip install --no-cache-dir -r requirements.txt

# 모든 소스 코드 복사
COPY ./ /app

# Uvicorn으로 FastAPI 앱 실행 (이 부분은 tiangolo의 베이스 이미지에 이미 설정되어 있으므로 생략해도 됩니다)
CMD ["uvicorn", "main:app", "--host", "0.0.0.0", "--port", "80", "--reload"]
