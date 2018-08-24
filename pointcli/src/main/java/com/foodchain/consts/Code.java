package com.foodchain.consts;

import java.util.HashMap;
import java.util.Map;

public final class Code {

   private Code() {}

    // 工单状态：未读|已读|关闭|已发送
    public static final class WorkOrderState {

        private WorkOrderState () {}

        public static final String UNREAD = "UNREAD";

        public static final String READ = "READ";

        public static final String CLOSE = "CLOSE";

        public static final String SEND = "SEND";

        public static final Map<String, String> CODE_MAP = new HashMap<>();
        static {
            CODE_MAP.put(UNREAD, "未读");
            CODE_MAP.put(READ, "已读");
            CODE_MAP.put(CLOSE, "关闭");
            CODE_MAP.put(SEND, "已发送");
        }

        public static final String get(String code) {
            return getMap(CODE_MAP, code);
        }
    }

    // 工单类型：账户类|交易类|投诉类|建议类
    public static final class WorkOrderType {

        private WorkOrderType () {}

        public static final String ACCOUNT = "ACCOUNT";

        public static final String TRADE = "TRADE";

        public static final String COMPLAINT = "COMPLAINT";

        public static final String SUGGEST = "SUGGEST";

        public static final Map<String, String> CODE_MAP = new HashMap<String, String>();
        static {
            CODE_MAP.put(ACCOUNT, "账户类");
            CODE_MAP.put(TRADE, "交易类");
            CODE_MAP.put(COMPLAINT, "投诉类");
            CODE_MAP.put(SUGGEST, "建议类");
        }

        public static final String get(String code) {
            return getMap(CODE_MAP, code);
        }

    }

    // 工单方向:用户|系统
    public static final class WorkOrderDirection {

        private WorkOrderDirection () {};

        public static final String USER = "USER";

        public static final String SYSTEM = "SYSTEM";

        public static final Map<String, String> CODE_MAP = new HashMap<String, String>();
        static {
            CODE_MAP.put(USER, "用户");
            CODE_MAP.put(SYSTEM, "系统");
        }

        public static final String get(String code) {
            return getMap(CODE_MAP, code);
        }

    }

    private static final String getMap(Map<String, String> map, String code) {
        if (map.containsKey(code)) {
            return map.get(code);
        }
        return "";
    }
}
