package com.rybacki.user.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CalculationAlgorithm {

  private static final int SCALE = 6;
  private static final RoundingMode ROUNDING_MODE = RoundingMode.HALF_UP;

  public BigDecimal calculate(Long followers, Long repos) {
    return new BigDecimal(6)
        .divide(new BigDecimal(followers), SCALE, ROUNDING_MODE)
        .multiply(new BigDecimal(2).add(new BigDecimal(repos)));
  }
}
