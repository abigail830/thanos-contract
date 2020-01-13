package com.thanos.contract.mockserver.domain.contract.validate;

public interface Validator {
    String name();

    boolean validate(String actualValue);

    String getExpectedValue();

}
