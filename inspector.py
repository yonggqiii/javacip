import csv

def get_dir(dir):
    return f'tests/bigclonebench_generics/{dir}'

# get statistics
with open(get_dir('statistics.csv')) as f:
    stats = list(csv.reader(f))

res_stats = [stats[0]]
for row in stats[1:]:
    new_row = row[:]
    file = new_row[0]
    with open(get_dir(f'in/{file}')) as f:
        contents = f.read()
        new_row.append('Exception' in contents or 
                'throw' in contents or 
                'try' in contents or 
                'catch' in contents)
    res_stats.append(new_row)

with open(get_dir('new_statistics.csv'), 'w+') as f:
    w = csv.writer(f)
    w.writerows(res_stats)
