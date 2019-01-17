package com.example.oauth2_server.handler.core;

import com.example.oauth2_server.handler.core.annotation.Namespace;
import com.example.oauth2_server.handler.core.config.ActionWrapper;
import com.example.oauth2_server.handler.core.config.RouterConfig;
import com.example.oauth2_server.handler.core.exception.DuplicateActionException;
import com.example.oauth2_server.handler.core.util.PackageUtil;
import lombok.extern.slf4j.Slf4j;

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
public class RouterContext {
    public Map<String, ActionWrapper> actions = new HashMap<String, ActionWrapper>();

    public RouterContext(String configFilePath) throws Exception{
        RouterConfig config = RouterConfig.parse(configFilePath);
        initActionMap(config, actions, ".action");
    }

    private void initActionMap(RouterConfig config, Map<String, ActionWrapper> actionMap, String suffix){
        List<String> packages = config.getActionPacages();
        for(String packagee : packages){
            String[] classNames = PackageUtil.findClassesInPackage(packagee + ".*"); // 目录下通配
            for(String className : classNames){
                try {
                    Class<?> actionClass = Class.forName(className);
                    if(!BaseAction.class.isAssignableFrom(actionClass)){
                        continue;
                    }
                    BaseAction baseAction = (BaseAction) actionClass.newInstance();
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
                    log.error("该类{} 没有扫描到",className);
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

}
