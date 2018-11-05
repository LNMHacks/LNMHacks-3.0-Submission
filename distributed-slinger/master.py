from minimalcluster import MasterNode

your_host = '192.168.137.122' # or use '0.0.0.0' if you have high enough privilege
your_port= 6969
your_authkey = 'asdff'

master = MasterNode(HOST = your_host, PORT = your_port, AUTHKEY = your_authkey)
master.start_master_server()
master.stop_as_worker()