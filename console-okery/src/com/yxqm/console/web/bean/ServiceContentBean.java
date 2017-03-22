package com.yxqm.console.web.bean;

import java.util.Map;


public class ServiceContentBean {
    private String serviceName;
    private Map<String, Object> serviceData;

    public String getServiceName() {
        if (null != serviceData) {
            return (String) serviceData.get("service");
        }

        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public Map<String, Object> getServiceData() {
        return serviceData;
    }

    public void setServiceData(Map<String, Object> serviceData) {
        this.serviceData = serviceData;
    }
}
