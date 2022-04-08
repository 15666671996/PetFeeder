import base64
import json
import tempfile
import threading
import uuid

import cv2
import numpy as np
from flask import Flask, jsonify, send_file
from paho.mqtt import client as mqtt_client

broker = 'mqtt.superfish.me'
port = 1883
topic_photo = "esp32/photo"
topic_pub = "esp32/pub"
client_id = f'python-mqtt-{uuid.uuid4()}'
client = mqtt_client.Client(client_id)
weight = 0
water = 0
app = Flask(__name__)


def get_hist(file):
  img = cv2.imread(file, cv2.IMREAD_GRAYSCALE)
  roi = img[30:-30, 60:-80]
  _, roi = cv2.threshold(roi, 0, 255, type=cv2.THRESH_OTSU)
  hist = np.zeros((256), dtype=np.int32)
  for r in roi:
    for c in r:
      hist[c] = hist[c] + 1

  return hist


@app.route('/take-photo-req')
def take_photo_req():
  return send_file("tmp.jpg", mimetype='image/jpeg')


@app.route('/is-empty-req')
def is_empty_req():
  img = cv2.imread("tmp.jpg", cv2.IMREAD_GRAYSCALE)

  empty_hist = get_hist("template_empty.jpg")
  full_hist = get_hist("template_full.jpg")
  cur_hist = get_hist("tmp.jpg")

  diff_empty = np.sum(np.abs(cur_hist - empty_hist))
  diff_full = np.sum(np.abs(cur_hist - full_hist))

  resp = {
      "message": bool(diff_empty < diff_full)
  }

  print(resp)

  return jsonify(resp)


@app.route('/enable-pump')
def enable_pump():
  payload = {
      "message": "o"
  }
  client.publish("esp32/sub", json.dumps(payload))

  return jsonify({"message": "success"})


@app.route('/disable-pump')
def disable_pump():
  payload = {
      "message": "c"
  }
  client.publish("esp32/sub", json.dumps(payload))

  return jsonify({"message": "success"})


@app.route('/serve-food')
def serve_food():
  payload = {
      "message": "s"
  }

  client.publish("esp32/sub", json.dumps(payload))

  return jsonify({"message": "success"})


@app.route('/water-status')
def get_water():
  payload = {
      "message": str(water)
  }

  return jsonify(payload)


@app.route('/weight-status')
def get_weight():
  payload = {
      "message": str(weight)
  }

  return jsonify(payload)


def connect_mqtt():
  def on_connect(client, userdata, flags, rc):
    if rc == 0:
      print("Connected to MQTT Broker!")
    else:
      print("Failed to connect, return code %d\n", rc)

  def on_message(client, userdata, msg):
    if msg.topic == topic_photo:
      with open("tmp.jpg", "wb") as f:
        f.write(msg.payload)
    elif msg.topic == topic_pub:
      payload = str(msg.payload, encoding="utf-8")
      obj = json.loads(payload)

      global water, weight
      water = obj["Water"]
      weight = obj["Weight"]

  client.on_connect = on_connect
  client.on_message = on_message
  client.connect(broker, port)

  client.subscribe(topic_photo)
  client.subscribe(topic_pub)

  return client


def mqtt_run():
  client = connect_mqtt()
  client.loop_forever()


def app_run():
  app.run(host="0.0.0.0")


if __name__ == '__main__':
  mqtt = threading.Thread(target=mqtt_run)
  mqtt.start()

  flask = threading.Thread(target=app_run)
  flask.start()
