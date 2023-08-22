import socket
import time

def start_client():
    client = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    client.connect(('127.0.0.1', 11111))
    client.sendall(b'Hello, Server!')
    time.sleep(3)
    client.close()

if __name__ == "__main__":
    start_client()
