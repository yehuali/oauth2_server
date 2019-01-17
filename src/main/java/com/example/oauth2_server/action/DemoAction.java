package com.example.oauth2_server.action;


import com.example.oauth2_server.handler.core.BaseAction;
import com.example.oauth2_server.handler.core.annotation.Namespace;

/**
 * 基本类型参数 测试 demo
 * @author yunfeng.cheng
 * @create 2016-08-22
 */
public class DemoAction extends BaseAction {
	
	//测试基本参数类型
	public String primTypeTest( String author){
		System.out.println("Receive parameters: author=" + author);
		return author;
	}
	
//	//使用 @Namespace 注解
//	@Namespace("/nettp/pri/")
//	public Render primTypeTestWithNamespace(@Read(key="id") Integer id, @Read(key="proj") String proj, @Read(key="author") String author){
//		System.out.println("Receive parameters: id=" + id + ",proj=" + proj + ",author=" + author);
//		return new Render(RenderType.TEXT, "Received your primTypeTestWithNamespace request.[from primTypeTestWithNamespace]");
//	}
	
	
	
}
