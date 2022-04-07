import base64
import json
import tempfile
import threading

import cv2
import numpy as np
from flask import Flask, jsonify, send_file
from matplotlib import pyplot as plt
from paho.mqtt import client as mqtt_client


def get_hist(file):
  img = cv2.imread(file, cv2.IMREAD_GRAYSCALE)
  roi = img[:, 20:-50]
  hist = np.zeros((256), dtype=np.int32)
  for r in roi:
    for c in r:
      hist[c] = hist[c] + 1

  return hist


# full_hist = get_hist("template_full.jpg")
# empty_hist = get_hist("template_empty.jpg")

# plt.bar(range(0, 256), full_hist, label="hist whtn full")
# plt.bar(range(0, 256), empty_hist, label="hist whtn empty")

# plt.legend()

# plt.show()

img = cv2.imread("template_full.jpg", cv2.IMREAD_GRAYSCALE)
roi = img[:, 20:-50]
cv2.imwrite("roi_full.jpg", roi)

img = cv2.imread("template_empty.jpg", cv2.IMREAD_GRAYSCALE)
roi = img[:, 20:-50]
cv2.imwrite("roi_empty.jpg", roi)
