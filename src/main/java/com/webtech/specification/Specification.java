package com.webtech.specification;

import java.util.List;

public interface Specification {
    String toSql();

    List<Object> getParameters();
}
