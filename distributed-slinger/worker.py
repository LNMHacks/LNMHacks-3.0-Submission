from minimalcluster import WorkerNode

your_host = '192.168.137.122'
your_port= 6969
your_authkey = 'asdff'
N_processors_to_use = 8

worker = WorkerNode(your_host, your_port, your_authkey, nprocs = N_processors_to_use)

worker.join_cluster()
