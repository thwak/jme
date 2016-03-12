package method.extractor;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.MethodDeclaration;

public class MethodVisitor extends ASTVisitor {
	private List<Block> methodBodies = new ArrayList<Block>();

	public List<Block> getMethodBodies(){
		return methodBodies;
	}

	@Override
	public boolean visit(MethodDeclaration node){
		methodBodies.add(node.getBody());
		return super.visit(node);
	}
}
