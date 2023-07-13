from Cryptodome.Protocol.KDF import PBKDF2 as pbkdf2
from Cryptodome.Random import get_random_bytes

class EncryptedPayLoad:
    NONCE_LENGTH_BYTES = 3

    def get_payload(self):
        cursor = 0
        # salt
        salt_len = int.from_bytes(self._encrypted_payload[cursor:cursor + EncryptedPayLoad.NONCE_LENGTH_BYTES], byteorder="big")
        cursor += EncryptedPayLoad.NONCE_LENGTH_BYTES
        salt = self._encrypted_payload[cursor:cursor + salt_len]
        cursor += salt_len
        # iv
        iv_len = int.from_bytes(self._encrypted_payload[cursor:cursor + EncryptedPayLoad.NONCE_LENGTH_BYTES], byteorder="big")
        cursor += EncryptedPayLoad.NONCE_LENGTH_BYTES
        iv = self._encrypted_payload[cursor:cursor + iv_len]
        cursor += iv_len
        # encrypted_byte
        encrypted_byte = self._encrypted_payload[cursor:]
        return salt, iv, encrypted_byte
    def set_payload(self, values):
        salt, iv, encrypted_byte = tuple(values)
        self._encrypted_payload = len(salt).to_bytes(EncryptedPayLoad.NONCE_LENGTH_BYTES, \
            byteorder="big") + salt + len(iv).to_bytes(EncryptedPayLoad.NONCE_LENGTH_BYTES, \
            byteorder="big") + iv + encrypted_byte
    payload = property(fget = get_payload, fset = set_payload, doc = "(00)-salt_len + salt + (00)-iv_len + iv + encrypted_byte")
    
    def __init__(self, salt = b'', iv = b'', encrypted_byte = b''):
        self._encrypted_payload = b''
        if not isinstance(salt, (bytes,bytearray)) \
            or not isinstance(iv, (bytes,bytearray)) \
            or not isinstance(encrypted_byte, (bytes,bytearray)):
            raise TypeError("EncryptedPayLoad type error")
        self.salt = salt
        self.iv = iv
        self.encrypted_byte = encrypted_byte
        self.set_payload((salt, iv, encrypted_byte))

a = EncryptedPayLoad(b'salt', b'iv', b'encrypted_byte')
print(a.payload)
a.payload = (b'salt2', b'iv2', b'encrypted_byte2')
print(a.payload)