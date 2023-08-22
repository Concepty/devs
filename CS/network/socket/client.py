import time
import socket

HOST = '127.0.0.1'; PORT = 44343

with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as s:
    s.connect((HOST, PORT))
    while True:
        cmd = input('input cmd: ')
        if cmd == 'sleep': time.sleep(100000)
        else: s.send(cmd.encode('utf-8'))
        data = s.recv(1024)
        print(data.decode('utf-8'))
