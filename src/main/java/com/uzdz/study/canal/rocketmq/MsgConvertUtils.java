package com.uzdz.study.canal.rocketmq;



import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MsgConvertUtils {

    public static DEvent msgConvert(String msg) {
        Map<String, Object> change = JsonUtils.JsonToMap(msg);
        if (change != null && !change.isEmpty()) {

            List<String> pkNames = (List<String>) change.get("pkNames");
            if (pkNames == null || pkNames.isEmpty()) {
                return null;
            }

            DEvent dEvent = new DEvent();
            dEvent.setPkName(pkNames.get(0));
            dEvent.setType(String.valueOf(change.get("type")));
            dEvent.setDynamicDataList(new ArrayList(
                    (List<Map<String, String>>) change.get("data")));
            return dEvent;
        }
        return null;
    }
}
