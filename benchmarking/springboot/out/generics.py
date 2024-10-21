import os
dirs = filter(lambda x: '.py' not in x, os.listdir())
total = 0
for dir in dirs:
    os.chdir(dir)
    files = filter(lambda x: '.java' in x, os.listdir())
    for file in files:
        x = open(file).read()
        if '<' in x and '>' in x:
            total += 1
            break
    os.chdir('..')
print(total)
