from antlr4 import Lexer

class Java9LexerBase(Lexer):
    def __init__(self, input, output):
        super().__init__(input, output)
    '''
    def isJavaIdentifierPart(self, c):
        print('part')
        print(c)
        print(type(c))
        raise Exception
        # return False

    def isJavaIdentifierStart(self, c):
        print('start')
        print(c)
        print(type(c))
        raise Exception

    def Check1(self):
        print(self._input.LA(-1))
        raise Exception

    def Check2(self):
        print(self._input.LA(-1))
        raise Exception
    def Check3(self):
        print(self._input.LA(-1))
        raise Exception

    def Check4(self):
        print(self._input.LA(-1))
        raise Exception
    '''
