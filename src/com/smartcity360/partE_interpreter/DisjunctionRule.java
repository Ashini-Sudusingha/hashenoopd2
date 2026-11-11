package com.smartcity360.partE_interpreter;

/**
 * Non-terminal expression performing boolean logical OR.
 * Candidate: Hashen Liyanaarachchi | JIAT/OODP II /EX/01
 */
public class DisjunctionRule implements CityRule {
    private final CityRule operandA;
    private final CityRule operandB;

    public DisjunctionRule(CityRule operandA, CityRule operandB) {
        this.operandA = operandA;
        this.operandB = operandB;
    }

    @Override
    public boolean evaluate(UrbanRuleContext context) {
        return operandA.evaluate(context) || operandB.evaluate(context);
    }
}
