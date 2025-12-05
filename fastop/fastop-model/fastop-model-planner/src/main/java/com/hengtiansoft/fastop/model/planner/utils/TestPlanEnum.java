package com.hengtiansoft.fastop.model.planner.utils;

public enum TestPlanEnum {
    // 未开始/已派工
    UNEXE(0, "UNEXE"),
    // 待检验
    VERIFY(1, "VERIFY"),
    // 执行中/已开工
    EXEING(2, "EXEING"),
    // 暂停
    PAUSE(3, "PAUSE"),
    // 待军检
    MVERIFY(4, "MVERIFY"),
    // 待派工/已下发
    DISPATCH(5, "DISPATCH"),
    // 完成状态/已完工
    FINISH(6, "FINISH");

    private Integer key;
    private String value;

    TestPlanEnum(int key, String value) {
        this.key = key;
        this.value = value;
    }

    // 根据key获取value
    public static String getValue(int key) {
        for (TestPlanEnum item : TestPlanEnum.values()) {
            if (item.getKey().equals(key)) {
                return item.value;
            }
        }
        return null;
    }

    // 根据value获取key
    public static int getKey(String value) {
        for (TestPlanEnum item : TestPlanEnum.values()) {
            if (item.getValue().equals(value)) {
                return item.getKey();
            }
        }
        return -1;
    }

    public Integer getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}

