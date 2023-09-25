from flask import Flask, request, jsonify
import torch
from pathlib import Path
import subprocess
import sys
sys.path.append('./yolov5')

from detect import run

# YOLOv5 모델 로드 (여기서는 yolov5s 모델을 사용한다고 가정)
model_path = './models/best.pt'  # 모델 파일의 경로 (적절하게 수정)
model = torch.load(model_path, map_location=torch.device('cpu'))
# model.eval()  # 평가 모드로 설정
app = Flask(__name__)

@app.route('/detect', methods=['POST'])
def detect():
    image = request.files.get('image')
    if not image:
        return jsonify({'error': 'No image provided'}), 400
    # 이미지 저장 (임시)
    # image_path = Path('./tmp/tmp.png')
    # image.save(image_path)

    image_extension = Path(image.filename).suffix

    # 이미지 저장 (임시)
    image_path = Path(f'./tmp/tmp{image_extension}')
    print(image_path)
    image.save(image_path)

    foody_detect = run('./models/best.pt',
        image_path,
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
    image_path.unlink()
    return foody_detect

if __name__ == '__main__':
    app.run(debug=True)