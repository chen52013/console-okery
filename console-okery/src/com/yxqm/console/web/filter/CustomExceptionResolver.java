package com.yxqm.console.web.filter;

import com.alibaba.fastjson.JSON;

import com.yxqm.console.utils.Consts;
import com.yxqm.console.web.bean.ForumResponse;
import com.yxqm.console.web.bean.GatewayResponse;
import com.yxqm.console.web.exception.GateWayActionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 *
 * ClassName: CustomExceptionResolver <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(å¯é?). <br/>
 * date: 2015å¹?æœ?æ—?ä¸‹åˆ6:29:50 <br/>
 *
 * @author é›·æ¹˜å‰?
 * @version
 * @since JDK 1.7
 */
public class CustomExceptionResolver extends SimpleMappingExceptionResolver {
    private static final String DEFAULT_CHARSET = "UTF-8";
    private static final Logger LOG = LoggerFactory.getLogger(CustomExceptionResolver.class);

    protected ModelAndView doResolveException(HttpServletRequest request,
        HttpServletResponse response, Object handler, Exception ex) {
        //ç½‘å…³å¼‚å¸¸
        if (ex instanceof GateWayActionException) {
            GatewayResponse gatewayResponse = new GatewayResponse();

            gatewayResponse.setMsg_code(Consts.ERROR_1000);
            writeData(response, JSON.toJSONString(gatewayResponse));

            return null;
        }

        //æ–‡ä»¶ä¸Šä¼ å¤§å°é™åˆ¶å¼‚å¸¸
        if (ex instanceof MaxUploadSizeExceededException) {
            ForumResponse forumResponse = new ForumResponse();
            forumResponse.setRes_code("3");
            forumResponse.setRes_msg("æ ‡é¢˜å›¾ç‰‡å¤ªå¤§");
            writeData(response, JSON.toJSONString(forumResponse));

            return null;
        }

        return super.doResolveException(request, response, handler, ex);
    }

    /**
     *
     * methodName: writeData
     *
     * å†™å‡ºæ•°æ®.<br/>
     *
     * @param response
     * @param msg
     */
    private void writeData(HttpServletResponse response, String msg) {
        OutputStream out = null;

        try {
            out = response.getOutputStream();
            out.write(msg.getBytes(DEFAULT_CHARSET));
        } catch (UnsupportedEncodingException e1) {
            LOG.error("ä¸æ”¯æŒçš„ç¼–ç æ ¼å¼å¼‚å¸¸", e1);
        } catch (IOException e1) {
            LOG.error("IOå¼‚å¸¸", e1);
        } finally {
            if (null != out) {
                try {
                    out.flush();
                    out.close();
                } catch (IOException e) {
                    LOG.error("IOå¼‚å¸¸", e);
                }
            }
        }
    }

    protected GatewayResponse getResponse(String msg_code) {
        GatewayResponse gatewayResponse = new GatewayResponse();

        gatewayResponse.setMsg_code(msg_code);

        return gatewayResponse;
    }
}
