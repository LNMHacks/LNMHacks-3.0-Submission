envir_statement = '''
# hw[0:1] -> position of height and width
# hw[2:3] -> original height and width

def fractal_calculation_worker(hw):
    x, y, zoom = hw[1],hw[0],hw[4]
    w, h = hw[3], hw[2]
    import socket
    host = socket.gethostname()
    cX, cY = -0.7, 0.27015
    moveX, moveY = 0.0, 0.0
    maxIter = 255

    zx = 1.5*(x - w/2)/(0.5*zoom*w) + moveX 
    zy = 1.0*(y - h/2)/(0.5*zoom*h) + moveY 
    i = maxIter 
    while zx*zx + zy*zy < 4 and i > 1: 
        tmp = zx*zx - zy*zy + cX 
        zy,zx = 2.0*zx*zy + cY, tmp 
        i -= 1
    return (i << 21) + (i << 10) + i*8, host
    '''

from PIL import Image
import time

h, w = 500, 500

bitmap = Image.new("RGB", (w, h), "black")

pix = bitmap.load()


def get_args(zoom):
    args = []
    for x in range(w):
        for y in range(h):
            args.append((y, x, h, w, zoom))
    return args


start_global = time.time()

for i in range(1, 2):
    start_each = time.time()
    master.load_args(get_args(i))
    master.load_envir(envir_statement, from_file=False)
    master.register_target_function("fractal_calculation_worker")
    result = master.execute()
    unique_host = set()
    for items in result.items():
        pix[items[0][1], items[0][0]] = items[1][0]
        unique_host.add(items[1][1])
    host_count = dict.fromkeys(unique_host, 0)
    for item in result.items():
        host_count[item[1][1]] += 1
    print(host_count)
    end_each = time.time()
    total = 0
    for key, value in host_count.iteritems():
        total += value
    print("Percentage work done by each worker :")
    for key, value in host_count.iteritems():
        print(key, "=>", 100.0*value/total, "%")
    print("Time elapsed : ", end_each - start_each)
    bitmap.show()

end_global = time.time()
print("Total time : ", end_global - start_global)
