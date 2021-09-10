/*
       <dependency>
            <groupId>com.github.jsqlparser</groupId>
            <artifactId>jsqlparser</artifactId>
            <version>4.0</version>
        </dependency>
		<dependency>
            <groupId>org.apache.commons</groupId>
            <artifactId>commons-lang3</artifactId>
            <version>3.11</version>
            <scope>compile</scope>
        </dependency>
*/

import java.util.Map;
import java.util.HashMap;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.ExpressionVisitorAdapter;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import org.apache.commons.lang3.StringUtils;

public class QueryWhereClauseParser {

    public static Map parseQuery(String where) {
        if (StringUtils.isEmpty(where)) {
            return null;
        }
        Map queryParams = new HashMap<>();
        Expression expr = null;
        try {
            expr = CCJSqlParserUtil.parseCondExpression(where);
            expr.accept(new ExpressionVisitorAdapter() {
                @Override
                public void visit(AndExpression expr) {
                    if (expr.getLeftExpression() instanceof AndExpression) {
                        expr.getLeftExpression().accept(this);
                    } else if ((expr.getLeftExpression() instanceof EqualsTo)) {
                        expr.getLeftExpression().accept(this);
                    }
                    expr.getRightExpression().accept(this);
                }

                @Override
                public void visit(EqualsTo expr) {
                    String key = expr.getLeftExpression().toString();
                    String value = SMSStringUtils.removeQuotes(expr.getRightExpression().toString());
                    queryParams.put(key, value);
                }
            });
        } catch (JSQLParserException e) {
            throw new Exception("Invalid where clause");
        }
        return queryParams;
    }
