/**
 * Project Name:console-business
 * File Name:ILogService.java
 * Package Name:com.yxqm.console.system
 * Date:2016Âπ?Êú?6Êó•‰∏ãÂç?:38:57
 * Copyright (c) 2016, Èõ∑ÊπòÂâ?All Rights Reserved.
 *
*/
package com.yxqm.console.web.bussiness;

import com.yxqm.console.exception.ConsoleBusinessException;
import com.yxqm.console.system.bean.LogBean;

import java.util.List;


/**
 * ClassName:ILogService <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:   TODO ADD REASON. <br/>
 * Date:     2016Âπ?Êú?6Êó?‰∏ãÂçà1:38:57 <br/>
 * @author  Èõ∑ÊπòÂâ?
 * @version
 * @since    JDK 1.7
 * @see
 */
public interface ILogService {
    void insertLog(LogBean logBean) throws ConsoleBusinessException;

    List<LogBean> querySysLog(LogBean logBean) throws ConsoleBusinessException;
}
