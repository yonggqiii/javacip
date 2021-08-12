from Java9Parser import Java9Parser
from Java9ParserListener import Java9ParserListener

class JavaCPPListener(Java9ParserListener):

    def __init__(self):
        super().__init__()
        self.types = {}
        self.current_class = None
        self.current_method = None

    def enterAssignment(self, ctx:Java9Parser.AssignmentExpressionContext):
        pass
    def exitAssignmentExpression(self, ctx: Java9Parser.AssignmentExpressionContext):
        pass
    def enterAssignmentExpression(self, ctx:Java9Parser.AssignmentExpressionContext):
        pass
    def enterMethodDeclaration(self, ctx: Java9Parser.MethodDeclarationContext):
        pass
    def enterMethodBody(self, ctx:Java9Parser.MethodBodyContext):
        print('MethodBody')
        print(ctx.getText())
    def enterBlock(self, ctx:Java9Parser.BlockContext):
        print('Block')
        print(ctx.getText())
    def enterBlockStatements(self, ctx:Java9Parser.BlockStatementsContext):
        print('Block Statements')
        print(ctx.getText())

    def enterBlockStatement(self, ctx: Java9Parser.BlockStatementContext):
        print('Block Statement')
        print(ctx.getText())

    def enterClassDeclaration(self, ctx:Java9Parser.ClassDeclarationContext):
        print('Class Declaration')
        i = ctx.getChildren()
        i = ctx.getTokens()
        for i in ctx.getChildren():
            print(i)
        #print(ctx.getText())


