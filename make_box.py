from matplotlib.ticker import ScalarFormatter
def f(ls):
    ls = ls[1:]
    res = []
    for row in ls:
        if row:
            row = row.split(',')
            res.append(mk(row))
    return res
def mk(r):
    return [r[0], True if r[1] == 'True' else False,
            'TIMEOUT' if r[2] == 'TIMEOUT' else float(r[2]), True if r[3] == 'True' else False, int(r[4])]

def partition(sbw_data):
    complete = [row for row in sbw_data if row[3]]
    cannotcomplete = [row for row in sbw_data if not row[3] and type(row[2]) == float]
    timeout = [row for row in sbw_data if type(row[2]) == str]
    return complete, cannotcomplete, timeout

def times(ls):
    return [row[2] for row in ls]
def sloc(ls):
    return [row[4] for row in ls]

sbw_data = f(open('benchmarking/springboot/statistics.csv').read().split('\n'))
bcb_data = f(open('benchmarking/bigclonebench_statistics.csv').read().split('\n'))
bcbsub_data = f(open('benchmarking/bigclonebench_generics/statistics.csv').read().split('\n'))
custom_data = f(open('benchmarking/custom/statistics.csv').read().split('\n'))

sbw_complete, sbw_nocomplete, sbw_timeout = partition(sbw_data)
bcb_complete, bcb_nocomplete, bcb_timeout = partition(bcb_data)
bcbsub_complete, bcbsub_nocomplete, bcbsub_timeout = partition(bcbsub_data)
custom_complete, custom_cannotcomplete, custom_timeout = partition(custom_data)

import matplotlib.pyplot as plt

sf = ScalarFormatter()
sf.set_useOffset(True)
# sf.set_powerlimits((0,3))
# sf.set_scientific(True)

fig, axs = plt.subplots(1, 4)
# axs[0].violinplot(list(map(times, [sbw_complete, sbw_nocomplete])), showmeans=True)
# axs[0].set_title('SBW')
# axs[0].set_xticks([1, 2])
# axs[0].set_xticklabels(['Complete', 'Cannot Complete'], rotation=90)
# axs[0].set_ylabel('Time (s)')
# axs[0].set_yscale('log')
# axs[0].set_yticks([0.3, 0.4, 0.6, 1])
# axs[0].set_yticklabels(['0.3', '0.4', '0.6', '1'])
#
# axs[1].violinplot(list(map(times, [bcb_complete, bcb_nocomplete])), showmeans=True)
# axs[1].set_title('BCB')
# axs[1].set_xticks([1, 2])
# axs[1].set_xticklabels(['Complete', 'Cannot Complete'], rotation=90)
# axs[1].set_yscale('log')
# axs[1].set_yticks([1, 10])
# axs[1].set_yticklabels(['1', '10'])
#
# axs[2].violinplot(list(map(times, [bcbsub_complete, bcbsub_nocomplete])), showmeans=True)
# axs[2].set_title('BCB Subset')
# axs[2].set_xticks([1, 2])
# axs[2].set_xticklabels(['Complete', 'Cannot Complete'], rotation=90)
# axs[2].set_yscale('log')
# axs[2].set_yticks([1, 10])
# axs[2].set_yticklabels(['1', '10'])
#
# axs[3].violinplot(list(map(times, [custom_complete, custom_cannotcomplete])), showmeans=True)
# axs[3].set_title('Custom')
# axs[3].set_xticks([1, 2])
# axs[3].set_xticklabels(['Complete', 'Cannot Complete'], rotation=90)
# axs[3].set_yscale('log')
# axs[3].set_yticks([0.3, 0.4, 0.6, 1])
# axs[3].set_yticklabels(['0.3', '0.4', '0.6', '1'])

axs[0].violinplot(list(map(sloc, [sbw_complete, sbw_nocomplete, sbw_timeout])), showmeans=True)
axs[0].set_title('SBW')
axs[0].set_xticks([1, 2, 3])
axs[0].set_xticklabels(['Complete', 'Cannot Complete', 'Timeout'], rotation=90)
axs[0].set_ylabel('SLoC')
axs[0].set_yscale('log')
axs[0].set_yticks([10, 100])
axs[0].set_yticklabels(['10', '$10^2$'])

axs[1].violinplot(list(map(sloc, [bcb_complete, bcb_nocomplete, bcb_timeout])), showmeans=True)
axs[1].set_title('BCB')
axs[1].set_xticks([1, 2, 3])
axs[1].set_xticklabels(['Complete', 'Cannot Complete', 'Timeout'], rotation=90)
axs[1].set_yscale('log')
axs[1].set_yticks([10, 100, 1000])
axs[1].set_yticklabels(['10', '$10^2$', '$10^3$'])

axs[2].violinplot(list(map(sloc, [bcbsub_complete, bcbsub_nocomplete, bcbsub_timeout])), showmeans=True)
axs[2].set_title('BCB Subset')
axs[2].set_xticks([1, 2, 3])
axs[2].set_xticklabels(['Complete', 'Cannot Complete', 'Timeout'], rotation=90)
axs[2].set_yscale('log')
axs[2].set_yticks([10, 100])
axs[2].set_yticklabels(['10', '$10^2$'])

axs[3].violinplot(list(map(sloc, [custom_complete, custom_cannotcomplete])), showmeans=True)
axs[3].set_title('Custom')
axs[3].set_xticks([1, 2])
axs[3].set_xticklabels(['Complete', 'Cannot Complete'], rotation=90)
axs[3].set_yscale('log')
axs[3].set_yticks([10, 20, 30])
axs[3].set_yticklabels(['10', '20', '30'])

plt.subplots_adjust(wspace = 0.5)



# plt.show()
plt.savefig('sloc_violinplots.svg')
