import os
import subprocess
import csv
from time import time

BATCH_SIZE = 10
TIMEOUT = 10

os.chdir('tests')
os.chdir('bigclonebench_generics')
all_files = os.listdir()
os.chdir('..')

with open('generic_files.txt') as f:
    files = [i.strip() for i in f.read().strip().split('\n')]

with open('statistics.csv') as f:
    stats = list(csv.reader(f))

dw_files = {row[0] for row in stats[1:]}

untouched_files = [file for file in all_files if file not in dw_files]

failed_files = []
for row in stats:
    if row[1] == 'False' or row[3] == 'False':
        failed_files.append(row[0])

stats = [row for row in stats if row[0]
         not in failed_files and row[0] not in untouched_files]
with open('statistics.csv', 'w') as f:
    w = csv.writer(f)
    w.writerows(stats)

files = untouched_files + failed_files + files
with open('generic_files.txt', 'w') as f:
    f.write('\n'.join(files))

while files:
    batch = files[:BATCH_SIZE]
    for file in batch:
        print(file)
        # create directory
        outdir = f'out/{file[:-5]}'
        if not os.path.exists(outdir):
            os.mkdir(outdir)
            infile = f'bigclonebench_generics/{file}'
            start_time = time()
            p = subprocess.Popen(
                ['java', '-jar', '../target/scala-3.0.2/javacip-0.0.1.jar', '-o', f'{outdir}', infile])
            try:
                p.wait(TIMEOUT)
                time_taken = time() - start_time
                # by this point, should be done
                os.chdir(outdir)
                java_files = os.listdir()
                if not java_files:
                    stats.append([file, False, time_taken, False])
                    print('disambiguation failed')
                    os.chdir('../..')
                    os.rmdir(outdir)
                else:
                    print('output found; attempting to compile...')
                    subprocess.run(['javac'] + java_files)
                    class_files = [
                        i for i in os.listdir() if i[-6:] == '.class']
                    if len(class_files) < len(java_files):
                        print('did not compile!')
                        os.system(f'rm {" ".join(java_files)}')
                        if class_files:
                            os.system(f'rm {" ".join(class_files)}')
                        os.chdir('../..')
                        os.rmdir(outdir)
                        stats.append([file, True, time_taken, False])
                    else:
                        print('compiled successfully!')
                        stats.append([file, True, time_taken, True])
                        os.system(f'rm {" ".join(class_files)}')
                        os.chdir('../..')
            except subprocess.TimeoutExpired:
                p.kill()
                os.rmdir(outdir)
                print('timeout!')
                stats.append([file, False, TIMEOUT, False])
    files = files[BATCH_SIZE:]
    # write files and stats
    with open('generic_files.txt', 'w') as f:
        f.write('\n'.join(files))
    with open('statistics.csv', 'w') as f:
        w = csv.writer(f)
        w.writerows(stats)
