import os
total = 0
dirs = filter(lambda x: '.py' not in x, os.listdir())
for i in dirs:
    os.chdir(i)
    files = list(filter(lambda x: '.java' in x, os.listdir()))
    if len(files) == 1:
        print(i)
        total += 1
    os.chdir('..')

print(total)
