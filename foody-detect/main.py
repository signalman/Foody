from flask import Flask, request, jsonify
from flask_cors import CORS
import torch
import os
from PIL import Image
import sys
import uuid

from security import token_required

sys.path.append('./yolov5')
from detect import run

# YOLOv5 모델 로드
model_path = './models/best.pt'  # 모델 파일의 경로
model = torch.load(model_path, map_location=torch.device('cpu'))
app = Flask(__name__)
CORS(app)

@app.route('/detect', methods=['POST'])
# @token_required
def detect():
    if not os.path.exists('tmp'):
        os.makedirs('tmp')
    image = request.files.get('image')
    if not image:
        return jsonify({'error': '파일이 없습니다.'}), 400

    # UUID를 사용하여 고유한 파일 이름 생성
    file_name = f"{uuid.uuid4()}.jpg"
    filePath = os.path.join("tmp", file_name)
    image.save(filePath)

    # 이미지 파일인지 확인
    try:
        Image.open(filePath)
    except IOError:
        os.remove(filePath)
        return jsonify({'error': '이미지 파일이 아닙니다.'}), 400

    foody_detect = run('./models/best.pt',
        filePath,
        'data/coco128.yaml', # dataset.yaml path
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
        1 # video frame-rate stride)
    )
    os.remove(filePath)
    return foody_detect

if __name__ == '__main__':
    app.run(debug=True)