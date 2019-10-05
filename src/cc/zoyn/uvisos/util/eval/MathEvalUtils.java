package cc.zoyn.uvisos.util.eval;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class MathEvalUtils {

    //    private static final String ONLY_ENGLISH = "^[0-9+\\-*/%()]+$";
    private static final ScriptEngineManager MANAGER = new ScriptEngineManager();
    private static final ScriptEngine ENGINE = MANAGER.getEngineByName("js");
//    private static final Pattern PATTERN = Pattern.compile(ONLY_ENGLISH);

    public static String eval(String script) throws ScriptException, ScriptContainsErrorKeywordExcpetion {
//        Matcher matcher = PATTERN.matcher(script);
        // 限制无限循环这类语句
        if (script.contains("for") || script.contains("while") || script.contains("var")
                || script.contains("=") || script.contains("let") || script.contains("const")
                || script.contains("function") || script.contains("eval")) {
            throw new ScriptContainsErrorKeywordExcpetion();
        }

        Object result = ENGINE.eval(script);
        if (result instanceof String) {
            return (String) result;
        } else {
            return String.valueOf(Double.parseDouble("" + result));
        }
    }

    public static void main(String[] args) {
//        System.out.println(Math.atan(Math.tan(Math.toRadians(57.5))) == Math.toRadians(57.5));
        try {
            System.out.println(eval("1*1+1"));
            System.out.println(eval("console.log(\"123\")"));
//            eval("for(i = 0; i < 10; i++){x *=i};i");
        } catch (ScriptException | ScriptContainsErrorKeywordExcpetion e) {
            e.printStackTrace();
        }
    }

}
