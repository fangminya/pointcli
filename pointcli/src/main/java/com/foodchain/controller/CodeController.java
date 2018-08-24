package com.foodchain.controller;

import com.foodchain.consts.Code;
import com.foodchain.consts.Consts;
import com.foodchain.util.Misc;
import com.foodchain.util.ReflectUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Iterator;
import java.util.Map;

/**
 * @Author: yuanZ
 * @Date: 2018/8/21 16:27
 * @Description: 动态加载下拉框中的值
**/
@Controller
public class CodeController extends BaseController {

    @ResponseBody
    @RequestMapping(value = "select/load", produces = Consts.Mapping.PRODUCES_TEXT)
    public String select(String clazz, String dataId) {
        if (!clazz.contains(".")) {
            clazz = Code.class.getName() + "$" + clazz;
        }

        Object obj = ReflectUtil.getField(clazz, "CODE_MAP");
        if (null == obj && !clazz.contains("$")) {
            int position = clazz.lastIndexOf(".");
            clazz = clazz.substring(0, position) + "$" + clazz.substring(position + 1);
            obj = ReflectUtil.getField(clazz, "CODE_MAP");
        }

        if (Misc.isEmpty(obj) || !(obj instanceof Map)) {
            return "[]";
        }

        Map<?, ?> codeMap = (Map<?, ?>) obj;
        StringBuilder buf = new StringBuilder("[");
        Iterator<?> it = codeMap.keySet().iterator();
        while (it.hasNext()) {
            Object code = it.next();
            Object name = codeMap.get(code);
            buf.append("{code:\"").append(code).append("\", name:\"").append(name).append("\"}");
            if (it.hasNext()) {
                buf.append(", ");
            }
        }
        buf.append("]");

        return toJson(true, dataId, buf.toString());
    }

}
