from fastapi import FastAPI, File, UploadFile, HTTPException
from fastapi.responses import JSONResponse
from fastapi.middleware.cors import CORSMiddleware
import torch
import os
from PIL import Image
import sys
import uuid
import aiofiles

sys.path.append('./yolov5')
from detect import run

# # YOLOv5 모델 로드
# model_path = './models/best.pt'  # 모델 파일 경로 적절하게 수정
# model = torch.load(model_path, map_location=torch.device('cpu'))

app = FastAPI()

# CORS 설정
app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],  # 모든 도메인에서의 접근을 허용
    allow_credentials=True,
    allow_methods=["*"],  # 모든 HTTP 메소드를 허용
    allow_headers=["*"],  # 모든 HTTP 헤더를 허용
)

async def save_image_file(image: UploadFile):
    file_name = f"{uuid.uuid4()}.jpg"
    file_path = os.path.join("tmp", file_name)

    # tmp 디렉토리가 존재하지 않는 경우 생성
    if not os.path.exists('tmp'):
        os.makedirs('tmp')

    # 비동기적으로 이미지 파일 저장
    async with aiofiles.open(file_path, 'wb') as out_file:
        # UploadFile.read()는 이미지의 전체 내용을 바이트로 반환
        content = await image.read()
        await out_file.write(content)

    # 파일 경로 반환 (필요한 경우)
    return file_path
  
  
@app.post("/detect")
async def detect(image: UploadFile = File(...)):
    file_path = await save_image_file(image)

    foody_detect = run('./models/best.pt',
                       file_path,
                       'data/coco128.yaml',  # dataset.yaml path
                       (640, 640),  # inference size (height, width)
                       0.25,  # confidence threshold
                       0.45,  # NMS IOU threshold
                       1000,  # maximum detections per image
                       '',  # cuda device, i.e. 0 or 0,1,2,3 or cpu
                       False,  # show results
                       False,  # save results to *.txt
                       False,  # save results in CSV format
                       False,  # save confidences in --save-txt labels
                       False,  # save cropped prediction boxes
                       False,  # do not save images/videos
                       None,  # filter by class: --class 0, or --class 0 2 3
                       False,  # class-agnostic NMS
                       False,  # augmented inference
                       False,  # visualize features
                       False,  # update all models
                       'runs/detect',  # save results to project/name
                       'exp',  # save results to project/name
                       True,  # existing project/name ok, do not increment
                       3,  # bounding box thickness (pixels)
                       False,  # hide labels
                       False,  # hide confidences
                       False,  # use FP16 half-precision inference
                       False,  # use OpenCV DNN for ONNX inference
                       1  # video frame-rate stride
                       )
    os.remove(file_path)
    return foody_detect


if __name__ == "__main__":
    import uvicorn

    uvicorn.run(app, host="0.0.0.0", port=5000)
