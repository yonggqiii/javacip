import argparse
from ASTBuilder.TypeBuilder import TypeBuilder
from Inference.inference import inference
from time import time

def parse(files):
    return TypeBuilder(files)

def seedPass(tb):
    return tb.build()

def stubGeneration(mt, out_path):
    res = ''
    for v in mt.values():
        res += str(v)
    with open(out_path[0], 'w') as f:
        f.write(res)

def main():
    # Command line args parsing.
    command_line_parser = argparse.ArgumentParser(description='Performs ' \
            'type verification on Java 9 partial programs and builds stubs '\
            'to make it compilable, if possible.')
    command_line_parser.add_argument('-o', metavar='out_file',
            nargs=1, default='out.java', required=False)
    command_line_parser.add_argument('input_files', nargs='+', metavar='in_file')
    args = command_line_parser.parse_args()

    # Main algorithm
    t_start = time()



    ######### PARSE STEP #############
    ast = parse(args.input_files)




    t_end = time()
    print(f'Parse done in {t_end - t_start}s')
    t_start = t_end



    ######### SEED PASS STEP ############
    td, mt, w = seedPass(ast)
    t_end = time()
    print(f'Seed pass done in {t_end - t_start}s')
    t_start = t_end
    
    print('======= Declared Types =======')
    for v in td.values():
        print(v)
        print()

    print('========= Missing types ========')
    for v in mt.values():
        print(v)
        print()

    print('========= Worklist ==========')
    for i in w:
        print(i)

    print()
    print('========= Beginning Inference ==========')



    ######## INFERENCE STEP ###########
    '''
    mt, compilable = inference(td, mt, w)
    
    t_end = time()
    print(f'Inference done in {t_end - t_start}s')
    t_start = t_end
    if not compilable:
        print('Program is not compilable.')
    else:


        ######### STUB GENERATION STEP #########
        stubGeneration(mt, args.o)
        t_end = time()
        print(f'Stub generation done in {t_end - t_start}s')
        t_start = t_end
    '''
if __name__ == '__main__':
    main()
