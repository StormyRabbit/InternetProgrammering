from Crypto.Cipher import AES
import hashlib
from Crypto import Random

class EncryptionMgr:
    @staticmethod
    def _pad_input(msg):
        while len(msg) % 16 != 0:
            msg += " "
        return msg

    @staticmethod
    def create_key_file(key, key_file_name):
        padded_key = hashlib.sha256(key.encode()).digest()
        with open(key_file_name, 'wb') as f:
            f.write(padded_key)

    @staticmethod
    def write_encrypted_to_file(encrypted_data, file_name):
        with open(file_name, 'wb') as f:
            f.write(encrypted_data)

    @staticmethod
    def load_encrypted_file(file_name):
        with open(file_name, 'rb') as f:
            return f.read()

    def encrypt(self, msg, key):
        test = self._pad_input(msg)
        padded_key = hashlib.sha256(key.encode()).digest()
        iv = Random.new().read(AES.block_size)
        encryption_box = AES.new(padded_key, AES.MODE_CBC, iv)
        encrypted_data = encryption_box.encrypt(test)
        return iv + encrypted_data

    @staticmethod
    def decrypt(encrypted_data, key):
        padded_key = hashlib.sha256(key.encode()).digest()
        iv = encrypted_data[:AES.block_size]
        cipher = AES.new(padded_key, AES.MODE_CBC, iv)
        decrypted_msg = cipher.decrypt(encrypted_data[AES.block_size:]).decode('utf-8')
        msg = decrypted_msg.strip()
        print(msg)
