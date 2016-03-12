package method.extractor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.Block;

public class JavaMethodExtractor {

	public static void main(String[] args) {
		String source = "public void foo() { \n"
				+ "\t//comment1\n"
				+ "\tint i=0;\n"
				+ "\t/*\n"
				+ "\t* comment2 \n"
				+ "\t*/  \n"
				+ "\tfor(i=0; i<10; i++) { \n"
				+ "\t\tSystem.out.println(i); //comment3 \n"
				+ "\t}\n"
				+ "\tint j=0;\n"
				+ "}";
		ASTNode node = parse(source);
		MethodVisitor v = new MethodVisitor();
		node.accept(v);
		List<Block> methodBodies = v.getMethodBodies();
		List<String> bodies = convertToString(methodBodies);
		for(String body : bodies) {
			System.out.println(body);
		}
	}

	public static List<String> convertToString(List<Block> methodBodies) {
		List<String> bodies = new ArrayList<String>();
		for(Block block : methodBodies) {
			String strBlock = block.toString();
			//Remove {\n }\n.
			bodies.add(strBlock.substring(strBlock.indexOf('{')+2, strBlock.lastIndexOf('}')-1));
		}
		return bodies;
	}

	public static ASTNode parse(String source){
		ASTParser parser = ASTParser.newParser(AST.JLS8);
		parser.setKind(ASTParser.K_CLASS_BODY_DECLARATIONS);
		parser.setEnvironment(new String[] { "" }, new String[] { "" }, null, true);
		Map options = JavaCore.getOptions();
		JavaCore.setComplianceOptions(JavaCore.VERSION_1_8, options);
		parser.setCompilerOptions(options);
		parser.setUnitName("Test.java");
		parser.setSource(source.toCharArray());
		ASTNode node = parser.createAST(null);
		return node;
	}

}
