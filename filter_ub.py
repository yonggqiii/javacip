import csv

def get_dir(dir):
    return f'tests/bigclonebench_generics/{dir}'

# get statistics
with open(get_dir('new_statistics.csv')) as f:
    stats = list(csv.reader(f))

res_stats = [stats[0]]
for row in stats[1:]:
    if row[1] == 'False' or row[3] == 'False':
        if row[4] == 'True':
            continue
    res_stats.append(row)

with open(get_dir('filter_ub_statistics.csv'), 'w+') as f:
    w = csv.writer(f)
    w.writerows(res_stats)
