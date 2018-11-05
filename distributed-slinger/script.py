envir_statement = '''
# A naive factorization method. Take integer 'n', return list of factors.
# Ref: https://eli.thegreenplace.net/2012/01/24/distributed-computing-in-python-with-multiprocessing
def example_factorize_naive(n):
    if n < 2:
        return []
    factors = []
    p = 2
    while True:
        if n == 1:
            return factors
        r = n % p
        if r == 0:
            factors.append(p)
            n = n / p
        elif p * p >= n:
            factors.append(n)
            return factors
        elif p > 2:
            p += 2
        else:
            p += 1
    assert False, "unreachable"
'''

#Create N large numbers to factorize.
def make_nums(N):
    nums = [999999999999]
    for i in range(N):
        nums.append(nums[-1] + 2)
    return nums

master.load_args(make_nums(5000))
master.load_envir(envir_statement, from_file = False)
master.register_target_function("example_factorize_naive")

result = master.execute()

for x in result.items()[:10]: # if running on Python 3, use `list(result.items())` rather than `result.items()`
    print(x)
