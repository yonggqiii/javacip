import matplotlib.pyplot as plt
import sys

fig, ax = plt.subplots()
ax.set_yscale('log')
ax.set_xscale('log')
ax.set_xlabel('SLoC')
ax.set_ylabel('Time (s)')
filename = sys.argv[1]
points = []
for row in open(filename).read().split('\n')[1:]:
    if not row:
        continue
    row = row.split()
    x, y = int(row[0]), float(row[1])
    ax.scatter(x, y, c='#000000', marker='.')

plt.savefig('test.svg')
