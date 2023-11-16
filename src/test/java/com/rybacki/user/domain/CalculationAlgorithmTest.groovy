package com.rybacki.user.domain

import spock.lang.Specification
import spock.lang.Unroll

class CalculationAlgorithmTest extends Specification {

    def calculationAlgorithm = new CalculationAlgorithm()

    @Unroll
    def "should return #expected for #followers followers and #repos repos" () {
        when:
        BigDecimal result = calculationAlgorithm.calculate(followers, repos);

        then:
        result == expected

        where:
        followers | repos | expected
        5L        | 1L    | new BigDecimal('3.6')
        6L        | 0L    | new BigDecimal('2')
        17L       | 102L  | new BigDecimal('36.705864')
    }

    @Unroll
    def "should throw exception when divide by 0"() {
        when:
        calculationAlgorithm.calculate(0, 0);

        then:
        thrown ArithmeticException
    }
}
