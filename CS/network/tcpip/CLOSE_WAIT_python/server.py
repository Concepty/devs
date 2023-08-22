import socket

def start_server():
    server = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    server.bind(('127.0.0.1', 11111))
    server.listen(5)
    print("Server started. Waiting for connections...")
    conn, addr = server.accept()
    print(f"Connection from {addr} established!")
    data = conn.recv(1024)
    print("Received:", data.decode())
    conn.shutdown(socket.SHUT_RDWR)  # Send FIN
    conn.close()
    print("Server connection closed")

if __name__ == "__main__":
    start_server()