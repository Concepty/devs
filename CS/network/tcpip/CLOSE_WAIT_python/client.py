import socket
import time

def start_client():
    client = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    client.connect(('127.0.0.1', 11111))
    client.sendall(b'Hello, Server!')
    time.sleep(300)  # Sleep for 2 minutes, keeping the socket in CLOSE_WAIT
    client.close()

if __name__ == "__main__":
    start_client()
