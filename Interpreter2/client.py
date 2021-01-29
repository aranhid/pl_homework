import socket

s = socket.create_connection(("127.0.0.1", 5555))
s.send(b"Hello, server!\n")