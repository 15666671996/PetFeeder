FROM python:3.8

WORKDIR /app/

COPY py/ /app/
RUN apt-get update && apt-get install -y python3-opencv
RUN pip install --no-cache-dir -r /app/requirements.txt

CMD [ "python", "/app/iot.py" ]