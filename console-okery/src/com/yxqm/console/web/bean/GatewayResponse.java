package com.yxqm.console.web.bean;

import com.alibaba.fastjson.annotation.JSONField;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import java.io.Serializable;

/**
 * 数据交换协议
 * @author Administrator
 *
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class GatewayResponse implements java.io.Serializable {
    private static final long serialVersionUID = -7422128238295439761L;
    private String service;
    private String msg_code;
    private Object data;

    @JSONField(name = "service")
    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    @JSONField(name = "msg_code")
    public String getMsg_code() {
        return msg_code;
    }

    public void setMsg_code(String msg_code) {
        this.msg_code = msg_code;
    }

    @JSONField(name = "data")
    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @JsonIgnore
    public String toString() {
        return "GatewayResponse [service=" + service + ", msg_code=" +
        msg_code + ", data=" + data + "]";
    }
}
