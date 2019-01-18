package com.example.oauth2_server.handler.core;

import com.example.oauth2_server.handler.core.annotation.Namespace;
import com.example.oauth2_server.handler.core.config.ActionWrapper;
import com.example.oauth2_server.handler.core.config.RouterConfig;
import com.example.oauth2_server.handler.core.exception.DuplicateActionException;
import com.example.oauth2_server.handler.core.invocation.ActionInvocation;
import com.example.oauth2_server.handler.core.invocation.ActionProxy;
import com.example.oauth2_server.handler.core.util.PackageUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * 路由上下文
 */
@Slf4j
@Component
public class RouterContext implements ApplicationContextAware {
    @Autowired
    private ActionProxy proxy;
    @Autowired
    private ActionInvocation invocation;

    /**
     * 上下文对象实例
     */
    private ApplicationContext applicationContext;

    public Map<String, ActionWrapper> actions = new HashMap<String, ActionWrapper>();


    public void  loadRouterContext(String configFilePath) throws Exception{
        RouterConfig config = RouterConfig.parse(configFilePath);
        initActionMap(config, actions, ".action");
    }



    private void initActionMap(RouterConfig config, Map<String, ActionWrapper> actionMap, String suffix){
        List<String> packages = config.getActionPacages();
        for(String packagee : packages){
            String[] classNames = PackageUtil.findClassesInPackage(packagee + ".*"); // 目录下通配
            for(String className : classNames){
                try {

//                    Class<?> actionClass = Class.forName(className);
//                    if(!BaseAction.class.isAssignableFrom(actionClass)){
//                        continue;
//                    }
//                    BaseAction baseAction = (BaseAction) actionClass.newInstance();
                    Object object = applicationContext.getBean(className);
                    if(!(object instanceof  BaseAction)){
                        continue;
                    }
                    BaseAction baseAction =(BaseAction)applicationContext.getBean(className);
                    Class<?> actionClass = object.getClass();
                    for(Method method : actionClass.getDeclaredMethods()){
                        if(method.getModifiers() == Modifier.PUBLIC){
                            String actionPath = findNamespace(actionClass, method, config) + method.getName() + suffix;
                            if(actionMap.get(actionPath) != null){
                                throw new DuplicateActionException(actionMap.get(actionPath).method, method, actionPath);
                            }
                            ActionWrapper actionWrapper  = new ActionWrapper(baseAction, method, actionPath);
                            actionMap.put(actionPath, actionWrapper);
                        }
                    }
                }catch(Exception e){
                    log.error("该类{} 没有扫描到",className,e);
                }
            }
        }

    }

    /**
     * 根据方法和配置返回命名空间。规则是以方法的Namespace注解优先，假如没有该注解，则使用包名正则匹配
     * @param actionClass
     * @param method
     * @param config
     * @return
     */
    private String findNamespace(Class<?> actionClass, Method method, RouterConfig config){
        Namespace nsAnnotation = method.getAnnotation(Namespace.class);
        if(nsAnnotation != null){
            return nsAnnotation.value();
        }else{
            String retNs = null;
            for(com.example.oauth2_server.handler.core.config.Namespace ns : config.getNamespaces()){
                if(Pattern.matches(ns.packages, actionClass.getPackage().getName())){
                    retNs = ns.name;
                }
            }
            if(retNs != null){
                return retNs;
            }else{
                return "/";
            }
        }

    }

    public ActionWrapper getActionWrapper(String path){
        return actions.get(path);
    }

    public ActionProxy getActionProxy(ActionWrapper actionWrapper) throws Exception{
        invocation.init(proxy);
        proxy.setActionObject(actionWrapper.actionObject);
        proxy.setMethod(actionWrapper.method);
        proxy.setMethodName(actionWrapper.method.getName());
        proxy.setInvocation(invocation);
        return proxy;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext =applicationContext;
    }
}
