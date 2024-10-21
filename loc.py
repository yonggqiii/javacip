import os

DIR = 'benchmarking/springboot'

os.chdir(DIR + '/in')
files = os.listdir()

for i in files:
    with open(i) as f:
        c = len(list(filter(bool, f.read().split('\n'))))
        print(f'{i},{c}')
