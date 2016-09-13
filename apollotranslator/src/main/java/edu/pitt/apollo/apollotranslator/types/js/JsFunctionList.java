package edu.pitt.apollo.apollotranslator.types.js;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;

import org.mozilla.javascript.ast.AstNode;
import org.mozilla.javascript.ast.FunctionNode;
import org.mozilla.javascript.ast.NodeVisitor;

import edu.pitt.apollo.apollotranslator.ApolloLogger;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Aug 12, 2013
 * Time: 2:14:23 PM
 * Class: JsFunctionList
 * IDE: NetBeans 6.9.1
 */
public class JsFunctionList extends ArrayList<JsFunction> implements NodeVisitor {

	public JsFunctionList() {
		super();
	}

	public boolean visit(AstNode node) {
		if (node instanceof FunctionNode) {
			FunctionNode funcNode = (FunctionNode) node;
			if (funcNode.depth() == 1) { // this will ignore nested functions, which can't be called externally
				String funcName = funcNode.getFunctionName().getIdentifier();

				JsFunction function = new JsFunction();
				function.setFunctionName(funcName);

				List<AstNode> list = funcNode.getParams();
				addArgumentNamesToFunctionFromAstNodes(list, function);

				this.add(function);
			}
		}

		return true;
	}

	private void addArgumentNamesToFunctionFromAstNodes(List<AstNode> argumentAstNodes, JsFunction function) {
		for (AstNode node : argumentAstNodes) {
			function.addJsFunctionArgument(node.toSource());
		}
	}

	public void checkForDuplicateFunctions() {

		Set<JsFunction> jsFunctionSet = new HashSet<JsFunction>();
		for (JsFunction function : this) {
			if (!jsFunctionSet.add(function)) {
				ApolloLogger.log(Level.WARNING, "The function " + function.getFunctionName() + " has multiple definitions in the"
						+ " JavaScript file. The last definition of the function in the file will be used.");
			}
		}
	}

	public JsFunction getJsFunctionByName(String functionName) {
        // This will try to find a function in the list by name
		//
		// Keeping the JsFunctions defined in the JavaScript seperate from the
		// function names referenced in the translation instructions
		// allows the user to specify the argument mappings in a different order
		// than the arguments in the JavaScript function. This method will
		// find a JsFunction from the JavaScript which matches one (by name) from
		// the translation instructions, and then the correct argument
		// order can be obtained.

        // At this point the list has already been checked for duplicate function names
		for (JsFunction thisFunction : this) {
			if (thisFunction.getFunctionName().equals(functionName)) {
				return thisFunction;
			}
		}

		return null;
	}
}
