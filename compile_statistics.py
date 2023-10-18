import sys
import csv
import os

def to_bool(s):
    return True if s == 'True' else False

def partition(ls):
    totally_failed = []
    complete_but_uncompilable = []
    succeeded = []
    to = []
    for row in ls:
        if row[2] == 'TIMEOUT':
            to.append(row)
        elif not row[1]:
            totally_failed.append([row[0], row[1], float(row[2]), row[3], row[4]])
        elif not row[3]:
            complete_but_uncompilable.append([row[0], row[1], float(row[2]), row[3], row[4]])
        else:
            succeeded.append([row[0], row[1], float(row[2]), row[3], row[4]])
    return totally_failed, complete_but_uncompilable, succeeded, to

def getLoC(ls):
    for row in ls:
        filename = row[0]
        row.append(len(list(filter(bool, map(lambda x: x.strip(), open(f'in/{filename}').read().split('\n'))))))
    return ls

def get_stats(ls):
    if not ls:
        return 0, 0, 0
    avg_time = 'TIMEOUT' if ls[0][2] == 'TIMEOUT' else sum(row[2] for row in ls) / len(ls)
    avg_loc = sum(row[4] for row in ls) / len(ls)
    return len(ls), avg_time, avg_loc

def print_stats(name, a, b, c):
    print(f'{name}:\n\tOccurrences: {a} ({round(a / total_size * 100, 1)}%)\n\tAverage Time: {b}s\n\tAverage LoC: {c}')

directory = sys.argv[1]
stats_file = sys.argv[2]

os.chdir(directory)

with open(stats_file) as f:
    stats = list(csv.reader(f))

stats = getLoC([[row[0], to_bool(row[1]), row[2], to_bool(row[3])] for row in stats[1:]])

total_size = len(stats)


t, c, s, to = partition(stats)

print_stats('Failed', *get_stats(t))
print(t)
print_stats('Failed to Compile', *get_stats(c))
print(c)
print_stats('Succeeded', *get_stats(s))
print_stats('Timeout', *get_stats(to))
print(to)
