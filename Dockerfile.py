FROM python:3.8

COPY py /app/
RUN "pip install --no-cache-dir -r /app/requirements.txt"

CMD [ "python", "/app/iot.py" ]