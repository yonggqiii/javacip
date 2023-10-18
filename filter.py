import os
import subprocess
import csv
from time import time

BATCH_SIZE = 10
TIMEOUT = 60

# maximum required breadth and height for dataset
BIGCLONEBENCH_GENERICS = ('bigclonebench_generics/in', 2, 3)
BIGCLONEBENCH_NOGENERICS = ('bigclonebench_nogenerics/in', 0, 2)
CUSTOM = ('custom/in', 3, 5)


# choose dataset here
PATH, MAX_BREADTH, MAX_DEPTH = BIGCLONEBENCH_NOGENERICS


CUSTOM_DATASET = [['1.java', False, 1, 1, 1000000, True],
                  ['2.java', False, 1, 2, 1000000, False],
                  ['3.java', False, 3, 3, 1000000, False],
                  ['4.java', False, 3, 3, 1000000, False],
                  ['5.java', True, 3, 3, 1000000, False],
                  ['6.java', False, 1, 1, 1000000, True],
                  ['7.java', False, 1, 1, 1000000, True],
                  ['8.java', False, 1, 1, 1000000, True],
                  ['9.java', False, 1, 1, 1000000, True],
                  ['10.java', False, 1, 1, 1000000, True],
                  ['11.java', False, 1, 1, 1000000, False],
                  ['12.java', False, 1, 1, 1000000, False],
                  ['13.java', False, 3, 3, 1000000, False],
                  ['14.java', False, 3, 3, 1000000, False],
                  ['15.java', False, 3, 3, 1000000, False],
                  ['16.java', False, 1, 1, 1000000, True],
                  ['17.java', False, 1, 1, 1000000, True],
                  ['18.java', False, 3, 3, 1000000, False],
                  ['19.java', False, 3, 3, 1000000, False],
                  ['20.java', False, 3, 3, 1000000, False],
                  ['21.java', False, 3, 3, 1000000, False],
                  ['22.java', False, 3, 4, 1000000, False],
                  ['23.java', False, 3, 4, 1000000, False],
                  ['24.java', False, 1, 1, 1000000, True],
                  ['25.java', False, 3, 3, 1000000, False],
                  ['26.java', False, 3, 3, 1000000, False],
                  ['27.java', False, 1, 1, 1000000, False],
                  ['28.java', False, 3, 3, 1000000, False],
                  ['29.java', False, 3, 3, 1000000, False],
                  ['30.java', True, 3, 3, 1000000, True]]



os.chdir('benchmarking')
os.chdir(PATH)
all_files = os.listdir()
os.chdir('..')
if not os.path.exists('out'):
    os.mkdir('out')

stats = [['Filename', 'Output Written', 'Time Taken for Disambiguation', 'Compilable']]

def cmd(outdir, infile, heuristic = False, maxBreadth = MAX_BREADTH, maxDepth = MAX_DEPTH, iterations = 2000000, noOverload = False):
    header = ['java', '-jar', '../../target/scala-3.0.2/javacip-0.0.1.jar',
            '--maxBreadth', str(maxBreadth),
            '--maxDepth', str(maxDepth),
            '-o', f'{outdir}',
            '-i', str(iterations)]
    if heuristic:
        header.append('-h')
    if noOverload:
        header.append('-n')
    header.append(infile)
    return header

def execute(filename, outdir, infile, heuristic = False, maxBreadth = MAX_BREADTH, maxDepth = MAX_DEPTH, iterations = 1000000, noOverload = False):
    os.mkdir(outdir)
    command = cmd(outdir, infile, heuristic, maxBreadth, maxDepth, iterations , noOverload)
    start_time = time()
    p = subprocess.Popen(command)
    try:
        p.wait(TIMEOUT)
        time_taken = time() - start_time
    except subprocess.TimeoutExpired:
        p.kill()
        print('Timeout!')
        os.rmdir(outdir)
        return [filename, False, 'TIMEOUT', False]
    os.chdir(outdir)
    java_files = os.listdir()
    if not java_files:
        print('Disambiguation failed.')
        os.chdir('../..')
        os.rmdir(outdir)
        return [filename, False, time_taken, False]
    print('output found; attempting to compile...')
    subprocess.run(['javac'] + java_files)
    class_files = [i for i in os.listdir() if i[-6:] == '.class']
    if len(class_files) < len(java_files):
        print('did not compile!')
        os.system(f'rm {" ".join(java_files)}')
        if class_files:
            os.system(f'rm {" ".join(class_files)}')
        os.chdir('../..')
        os.rmdir(outdir)
        return [filename, True, time_taken, False]
    print('compiled successfully!')
    os.system(f'rm {" ".join(class_files)}')
    os.chdir('../..')    
    return [filename, True, time_taken, True]

if PATH != CUSTOM[0]:
    for file in all_files:
        print(file)
        outdir = f'out/{file[:-5]}'
        infile = f'in/{file}'
        res = execute(file, outdir, infile)
        if res[1]:
            stats.append(res)
            continue
        print('Trying heuristic mode')
        res = execute(file, outdir, infile, heuristic=True)
        if res[1]:
            stats.append(res)
            continue
        print('Trying no overloading mode')
        res = execute(file, outdir, infile, noOverload=True)
        if res[1]:
            stats.append(res)
            continue
        print('Trying heuristic + no overloading')
        res = execute(file, outdir, infile, heuristic = True, noOverload=True)
        stats.append(res)

else:
    for file, heuristic, maxBreadth, maxDepth, iterations, noOverload in CUSTOM_DATASET:
        print(file)
        outdir = f'out/{file[:-5]}'
        infile = f'in/{file}'
        res = execute(file, outdir, infile, heuristic, maxBreadth, maxDepth, iterations, noOverload)
        stats.append(res)

with open('statistics.csv', 'w') as f:
    w = csv.writer(f)
    w.writerows(stats)
