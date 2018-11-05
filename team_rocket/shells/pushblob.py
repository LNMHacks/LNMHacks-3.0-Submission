# Create a file in Documents to test the upload and download.
import sys
from azure.storage.blob import BlockBlobService

block_blob_service = BlockBlobService(account_name='garbagee', account_key='cLwSD22JHD/aj6dpERvAf/3MqaeRvgR2Eh1jIyJN4/i0awP9Q8SGeSITLQftN5PcnvRx+H4ad5Z/penz2xCzcQ==')
# Upload the created file, use local_file_name for the blob name
block_blob_service.create_blob_from_path
('blobee', sys.argv[1], sys.argv[2])

print("hogya upload")
