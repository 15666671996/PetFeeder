import base64
import json
import tempfile
import threading

import cv2
import numpy as np
from flask import Flask, jsonify, send_file
from paho.mqtt import client as mqtt_client

broker = 'mqtt.superfish.me'
port = 1883
topic = "esp32/photo"
client_id = f'python-mqtt-6'
client = mqtt_client.Client(client_id)

app = Flask(__name__)


def get_dist(file):
  img = cv2.imread(file, cv2.IMREAD_GRAYSCALE)
  roi = img[:, 20:-50]
  dist = np.zeros((256), dtype=np.int32)
  for r in roi:
    for c in r:
      dist[c] = dist[c] + 1

  return dist


@app.route('/take-photo-req')
def take_photo_req():
  return send_file("tmp.jpg", mimetype='image/jpeg')


@app.route('/is-empty-req')
def is_empty_req():
  img = cv2.imread("tmp.jpg", cv2.IMREAD_GRAYSCALE)

  empty_dist = get_dist("template_empty.jpg")
  full_dist = get_dist("template_full.jpg")
  cur_dist = get_dist("tmp.jpg")

  diff_empty = np.sum(np.abs(cur_dist - empty_dist))
  diff_full = np.sum(np.abs(cur_dist - full_dist))

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

  return jsonify({"message":"success"})


@app.route('/disable-pump')
def disable_pump():
  payload = {
      "message": "c"
  }
  client.publish("esp32/sub", json.dumps(payload))

  return jsonify({"message":"success"})

@app.route('/serve-food')
def serve_food():
  payload = {
      "message": "s"
  }

  client.publish("esp32/sub", json.dumps(payload))

  return jsonify({"message":"success"})


def connect_mqtt():
  def on_connect(client, userdata, flags, rc):
    if rc == 0:
      print("Connected to MQTT Broker!")
    else:
      print("Failed to connect, return code %d\n", rc)

  def on_message(client, userdata, msg):
    with open("tmp.jpg", "wb") as f:
      f.write(msg.payload)

  client.on_connect = on_connect
  client.on_message = on_message
  client.connect(broker, port)

  client.subscribe(topic)
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