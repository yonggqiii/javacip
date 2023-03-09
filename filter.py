import os
import subprocess

BATCH_SIZE = 10
TIMEOUT = 10

os.chdir('tests')

with open('generic_files.txt') as f:
    files = [i.strip() for i in f.read().strip().split('\n')]

while files:
    batch = files[:BATCH_SIZE]
    for file in batch:
        print(file)
        # create directory
        outdir = f'out/{file[:-5]}'
        if not os.path.exists(outdir):
            os.mkdir(outdir)
            infile = f'bigclonebench_generics/{file}'
            p = subprocess.Popen(
                ['java', '-jar', '../target/scala-3.0.2/javacip-0.0.1.jar', '-b', '-o', f'{outdir}', infile])
            try:
                p.wait(TIMEOUT)
                # by this point, should be done
                os.chdir(outdir)
                java_files = os.listdir()
                if not java_files:
                    print('disambiguation failed')
                    os.chdir('../..')
                    os.rmdir(outdir)
                else:
                    subprocess.run(['javac'] + java_files)
                    class_files = [
                        i for i in os.listdir() if i[-6:] == '.class']
                    if len(class_files) < len(java_files):
                        print('did not compile!')
                    else:
                        print('compiled successfully!')
                        os.system(f'rm {" ".join(class_files)}')
                    os.chdir('../..')
            except subprocess.TimeoutExpired:
                p.kill()
                print('timeout!')
    files = files[BATCH_SIZE:]
