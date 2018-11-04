# Distributed Slinger

Making volunteer computing made easy.

## Motivation

We are aware that amazing computing power of our laptops and phones (not currently integrated). What if one can access them and use them as they please. Today's world provides no help when it comes to providing computational resouces. Our project will help laymen to have access to these idle "computers".

There are several computing platforms such as BIONIC, GridCoin, etc. But all of them have a common problem of complex and heavy installation process.

## Introduction

Our project consists of a script that a master node will run and script that all other worker nodes run. Everything else is handled by backend that handles and distributes all the code and results.

## Installation

1. Create a `virtualenv`: `virtualenv --system-site-packages -p python2 cluster`

2. source cluster/bin/activate

3. `git clone git@github.com:r1walz/distributed-slinger` or `git clone https://github.com/r1walz/distributed-slinger`

4. cd distributed-slinger

5. pip install -r requirements.txt

then you are ready to go!

## Short Tutorial

### Step 1 - Start master node

Open your Python terminal on your machine which will be used as **Master Node**, and run

```python
from minimalcluster import MasterNode

your_host =  '<your master node hostname or IP>'  # or use '0.0.0.0' if you have high enough privilege
your_port=  <port to use>
your_authkey =  '<the authkey for your cluster>'

master =  MasterNode(HOST  = your_host,  PORT  = your_port,  AUTHKEY  = your_authkey)
master.start_master_server()

```

Please note the master node will join the cluster as worker node as well by default. If you prefer otherwise, you can have argument `if_join_as_worker` in `start_master_server()` to be `False`. In addition, you can also remove it from the cluster by invoking `master.stop_as_worker()` and join as worker node again by invoking `master.join_as_worker()`.

### Step 2 - Start worker nodes

On all your **Worker Nodes**, run the command below in your Python terminal

```python
from minimalcluster import WorkerNode

your_host =  '<your master node hostname or IP>'
your_port=  <port to use>
your_authkey =  '<the authkey for your cluster>'
N_processors_to_use =  <how many processors on your worker node do you want to use>

worker =  WorkerNode(your_host, your_port, your_authkey,  nprocs  = N_processors_to_use)
worker.join_cluster()
```

Note: if your `nprocs` is bigger than the number of processors on your machine, it will be changed to be the number of processors.

After the operations on the worker nodes, you can go back to your **Master node** and check the list of connected **Worker nodes**.

```python
master.list_workers()
```

### Step 3 - Prepare environment to share with worker nodes

We need to specify the task function (as well as its potential dependencies) and the arguments to share with worker nodes, including

- **Environment**: The `environment` is simply the codes that's going to run on worker nodes. There are two ways to set up environment. The first one is to prepare a separate `.py` file as environment file and declare all the functions you need inside, then use `master.load_envir('<path of the environment file>')` to load the environment. Another way is for simple cases. You can use `master.load_envir('<your statements>', from_file = False)` to load the environment, for example `master.load_envir("f = lambda x: x * 2", from_file = False)`.

- **Task Function**: We need to register the task function using `master.register_target_function('<function name>')`, like `master.register_target_function("f")`. Please note the task function itself must be declared in the environment file or statement.

- **Arguments**: The argument must be a list. It will be passed to the task function. Usage: `master.load_args(args)`. **Note the elements in list `args` must be unique.**

### Step 4 - Submit jobs

Now your cluster is ready. you can try the examples below in your Python terminal on your **Master node**.

## Example

Lets build a fractal image generator.

1. Activate virtualenv `cluster`
2. Open python shell
3. Copy code of `master.py`
4. Connect worker nodes to master node
5. Copy code of `fractal.py` to python shell
6. Beautiful picture of fractal will appear.

Our code will handle the work of how to distribute to nodes and will give statistics about who performed how much.

### Misc

Our project also includes a front-end made in Django [WIP]. We will continue to work on the project so that one day even mobile devices can join this revolution.
