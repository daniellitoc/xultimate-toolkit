package org.danielli.xultimate.context.format.support;

import java.util.Map;

import org.danielli.xultimate.context.format.FormatException;
import org.danielli.xultimate.context.format.Formatter;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.ParserContext;
import org.springframework.expression.spel.support.StandardEvaluationContext;

/**
 * 格式化器。是SpEl的实现。
 * 
 * @author Daniel Li
 * @since 18 Jun 2013
 * @see Formatter
 * @see ExpressionParser
 * @see ParserContext
 * @see EvaluationContext
 */
public class SpelFormatter implements Formatter<String, Map<String, ? extends Object>, String> {

	/** 表达式解析器。 */
	private ExpressionParser expressionParser;
	
	/** 解析器上下文，定义了表达式结构 */
	private final ParserContext parserContext = new ParserContext() {
		
		public boolean isTemplate() {
			return true;
		}
		
		public String getExpressionSuffix() {
			return "}";
		}
		
		public String getExpressionPrefix() {
			return "${";
		}
		
	};
	
	@Override
	public String format(String source, Map<String, ? extends Object> parameter) throws FormatException {
		
		EvaluationContext context = new StandardEvaluationContext();
		for (Map.Entry<String, ? extends Object> entry : parameter.entrySet()) {
			context.setVariable(entry.getKey(), entry.getValue());
		}
		try {
			Expression expression = expressionParser.parseExpression(source, parserContext);
			return expression.getValue(context).toString();
		} catch (Exception e) {
			throw new FormatException(e.getMessage(), e);
		}
	}

	/**
	 * 设置表达式解析器。
	 * 
	 * @param expressionParser 表达式解析器。
	 */
	public void setExpressionParser(ExpressionParser expressionParser) {
		this.expressionParser = expressionParser;
	}

}
