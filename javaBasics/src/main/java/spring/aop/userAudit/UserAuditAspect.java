package spring.aop.userAudit;


import com.beust.jcommander.internal.Maps;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import utils.JsonUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * AOP自定义注解
 * 实现AOP自定义注解的功能
 */
@Aspect
@Component
public class UserAuditAspect {

    private final Logger logger = LoggerFactory.getLogger(UserAuditAspect.class);

    @Pointcut("@annotation(spring.aop.userAudit.UserAudited)")//切入点：指定注解读取的位置
    private void cut() {
    }

    /**
     * 解析请求参数
     */
    private static String parseParams(HttpServletRequest request) {
        Map<String, String> map = Maps.newHashMap();
        Enumeration paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String paramName = (String) paramNames.nextElement();

            String[] paramValues = request.getParameterValues(paramName);
            if (paramValues.length == 1) {
                String paramValue = paramValues[0];
                if (paramValue.length() != 0) {
                    if ("password".equalsIgnoreCase(paramName)) paramValue = "******";
                    map.put(paramName, paramValue);
                }
            }
        }
//        String ua = getHeader("User-Agent", request);
//        //转成UserAgent对象
//        UserAgent userAgent = UserAgent.parseUserAgentString(ua);
//        //获取浏览器信息
//        Browser browser = userAgent.getBrowser();
//        //获取系统信息
//        OperatingSystem os = userAgent.getOperatingSystem();
//
//        map.put("system", os.getName());
//        map.put("browse", browser.getName() + "/" + userAgent.getBrowserVersion());

        String jsonStr = Optional.ofNullable(JsonUtils.toJson(map)).orElse("");
        return strCutOff(jsonStr);
    }

    private static String strCutOff(String str) {
        if (null == str) return "";
        if (str.length() <= 5000)
            return str;
        else
            return str.substring(0, 5000 - 1);
    }

    /**
     * 获取头信息
     */
//    private static String getHeader(String s, HttpServletRequest request) {
//        return Optional.ofNullable(request.getHeader("User-Agent")).orElse("");
//    }

    /**
     * 获取IP地址
     */
    private static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");

        if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip))
            ip = request.getHeader("Proxy-Client-IP");

        if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip))
            ip = request.getHeader("WL-Proxy-Client-IP");

        if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip))
            ip = request.getHeader("HTTP_CLIENT_IP");

        if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip))
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");

        if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip))
            return request.getRemoteAddr();
        else {
            int index = ip.indexOf(',');
            if (index != -1) {
                return ip.substring(0, index);
            } else {
                return ip;
            }
        }
    }

    @Around("cut() && @annotation(userAudited)")
    public Object advice(ProceedingJoinPoint joinPoint, UserAudited userAudited) throws Throwable {
        Object result;
        UserAudit userAudit = new UserAudit();

        userAudit.setOpTypeEnum(userAudited.opType());//OpTypeEnum
        userAudit.setAction(userAudited.action());//Action
        userAudit.setRemark(userAudited.remark());//Remark

        String className = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        userAudit.setClassName(className);//ClassName
        userAudit.setMethodName(methodName);//MethodName

        String[] s = className.split("\\.");
        if (s.length >= 3)
            userAudit.setModule(s[2]);// module
        else
            userAudit.setModule(className);

        HttpServletRequest httpRequest = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        String inParams = parseParams(httpRequest);
        if("{}".equals(inParams)) {
            try {
                Object[] atgsArr = joinPoint.getArgs();
                inParams = JsonUtils.toJson(atgsArr);
                if (inParams.length() > 5000) {
                    inParams = inParams.substring(0, 5000);
                }
            }catch (Exception e){
                logger.error("用户审计获取参数出错",e);
                inParams = "获取参数出错";
            }
        }
        userAudit.setInParam(inParams);//InParam

        userAudit.setUrlPath(httpRequest.getRequestURL().toString());//URL
        userAudit.setIp(getIpAddress(httpRequest));// IP

//        userAudit.setUaInfo(getHeader("User-Agent", httpRequest));//UA

        try {
            Long start = System.currentTimeMillis();
            // proceed
            result = joinPoint.proceed();

            Long end = System.currentTimeMillis();

            userAudit.setExecutionTime(end - start);//ExecutionTime
            if (Objects.nonNull(result)) {
//                if (result instanceof Object) {//todo
//                    userAudit.setOutParam(strCutOff(result.toString())); //输出参数
//                } else if (result instanceof String) {
//                    userAudit.setOutParam((String) result);
//                }
                userAudit.setOutParam(result.toString());
            } else {
                userAudit.setOutParam("");
            }
        } catch (Throwable throwable) {
            userAudit.setError(strCutOff(throwable.getMessage()));// error
            logger.error(throwable.getMessage());
            throw throwable;
        } finally {
//            userAudit.setCreator(getLoginLastName(httpRequest, userAudit.getOpTypeEnum()));//Creator
//            userAudit.setCreatorId(getLoginUserId(httpRequest, userAudit.getOpTypeEnum()));//CreatorId
            setUserInfo(userAudit, httpRequest);// CreatorId Creator
//            int row = userAuditService.insertAuditRecord(userAudit);// insert

            System.out.println("保存到数据库");

            logger.info("insert：{}，UserAuditInfo：{}", 1, userAudit.toString());
        }
        return result;
    }

    private void setUserInfo(UserAudit userAudit, HttpServletRequest httpRequest) {

        // 从ThreadLocal获取
//        Integer loginUserId = MBox.getLoginUserId();
//        String loginUserName = MBox.getLoginUserName();
//
//        if (null == loginUserId || loginUserId < 1) {
//            // 从redis中获取
//            UserDto userDto = ssoUserService.getCurrentUserDto(httpRequest);
//
//            if (Objects.isNull(userDto)) {
//                String loginId = CommonUtils.getStringValue(httpRequest.getAttribute("loginid"));
//                if (Strings.isNullOrEmpty(loginId)) {
//                    loginId = CommonUtils.getStringValue(httpRequest.getParameter("loginid"));
//                }
//
//                if (!Strings.isNullOrEmpty(loginId)) {
//                    User user = userService.getByLoginid(loginId);
//
//                    if (!Objects.isNull(user)) {
//                        userAudit.setCreatorId(user.getId());
//                        userAudit.setCreator(user.getName());
//
//                        return;
//                    }
//                }
//
//                // 无法获取到用户信息，只能给默认值
//                userAudit.setCreatorId(0);
//                userAudit.setCreator("无法获取");
//
//                return;
//            }
//            userAudit.setCreatorId(userDto.getId());
//            userAudit.setCreator(userDto.getLastName());
//
//            return;
//        }
//
//        userAudit.setCreatorId(loginUserId);
//        userAudit.setCreator(loginUserName);
    }


//    private String getLoginLastName(HttpServletRequest httpRequest, OpTypeEnum opTypeEnum) {
//
//        String loginLastName = MBox.getLoginUserName();
//        if (!Strings.isNullOrEmpty(loginLastName)) return MBox.getLoginUserName();
//
//        switch (opTypeEnum) {
//            case login:
//            case logout:
//                String loginUserName = CommonUtils.getStringValue(httpRequest.getAttribute("loginid"));
//                if (Strings.isNullOrEmpty(loginUserName)) {
//                    loginUserName = CommonUtils.getStringValue(httpRequest.getParameter("loginid"));
//                }
//                User user = userService.getByLoginid(loginUserName);
//                loginLastName = user.getLastname();
//                break;
//            default:
//                loginLastName = MBox.getLoginUserName();
//                break;
//        }
//
//
//        return loginLastName;
//
//    }
//
//    private Integer getLoginUserId(HttpServletRequest httpRequest, OpTypeEnum opTypeEnum) {
//        Integer loginUserId = MBox.getLoginUserId();
//        if (null == loginUserId || loginUserId < 1) return MBox.getLoginUserId();
//
//
//        switch (opTypeEnum) {
//            case login:
//            case logout:
//                String loginUserName = CommonUtils.getStringValue(httpRequest.getAttribute("loginid"));
//                if (Strings.isNullOrEmpty(loginUserName)) {
//                    loginUserName = CommonUtils.getStringValue(httpRequest.getParameter("loginid"));
//                }
//                User user = userService.getByLoginid(loginUserName);
//                loginUserId = user.getId();
//                break;
//            default:
//                loginUserId = MBox.getLoginUserId();
//                break;
//        }
//
//        return loginUserId;
//
//    }

}
