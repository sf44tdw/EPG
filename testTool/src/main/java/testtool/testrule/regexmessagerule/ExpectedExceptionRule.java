/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testtool.testrule.regexmessagerule;

import java.lang.invoke.MethodHandles;
import java.util.regex.Pattern;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import static org.junit.Assert.*;

/**
 * 例外メッセージの正規表現マッチングを行う。<br>
 * クラス内に下記をRuleアノテーションをつけて定義し、<br>
 * public ExpectedExceptionRule rule = new ExpectedExceptionRule();<br>
 * 例外メッセージのテストメソッドのTestアノテーションの下に、ExpectedExceptionMessageアノテーションを正規表現と共に付けると、<br>
 * そのメソッドで発生した例外のメッセージが正規表現にマッチした時点でテスト成功と判定する。<br>
 * ※ExpectedExceptionMessageアノテーションを指定した場合、例外はTestアノテーションに取られてしまうので、Testアノテーションに例外を指定しないこと。
 */
public class ExpectedExceptionRule implements TestRule {

    private static final Log LOG;

    static {
        final Class<?> myClass = MethodHandles.lookup().lookupClass();
        LOG = LogFactory.getLog(myClass);
    }

    @Override
    public Statement apply(Statement stmnt, Description d) {
        final ExpectedExceptionMessage expectedExceptionMessage = d.getAnnotation(ExpectedExceptionMessage.class);
        if (expectedExceptionMessage == null) {
            return stmnt;
        } else {
            return new Statement() {
                @Override
                public void evaluate() throws Throwable {
                    try {
                        stmnt.evaluate();
                        fail("No exception is thrown");
                    } catch (Throwable e) {
                        StringBuilder sb = new StringBuilder();
                        sb.append("Regex = ");
                        sb.append(expectedExceptionMessage.value());
                        sb.append("\n");
                        sb.append("Message = ");
                        sb.append(e.getMessage());
                        sb.append("\n");
                        final Pattern expectedPattern = Pattern.compile(expectedExceptionMessage.value());
                        boolean x = e.getMessage() != null && expectedPattern.matcher(e.getMessage()).find();
                        if (x == false) {
                            throw new AssertionError(sb.toString(), e);
                        }

                    }
                }

            };
        }
    }

}
