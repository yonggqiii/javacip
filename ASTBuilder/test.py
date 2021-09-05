from antlr4 import FileStream, CommonTokenStream
from Java9Lexer import Java9Lexer
from Java9Parser import Java9Parser

def play():
        input_stream = FileStream('test2.java')
        lexer = Java9Lexer(input_stream)
        stream = CommonTokenStream(lexer)
        parser = Java9Parser(stream)
        tree = parser.compilationUnit()
        return tree
