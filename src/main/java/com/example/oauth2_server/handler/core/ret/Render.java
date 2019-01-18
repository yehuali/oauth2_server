package com.example.oauth2_server.handler.core.ret;

import com.example.oauth2_server.handler.core.Return;
import com.example.oauth2_server.handler.core.util.HttpReaderUtil;
import io.netty.handler.codec.http.FullHttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 字符型的输出
 */
public class Render implements Return {
	
	Logger logger = LoggerFactory.getLogger(Render.class);
	
	private String data;
	public RenderType renderType;
	
	public Render(RenderType renderType, String data) {
		this.data = data;
		this.renderType = renderType;
	}
	
	@Override
	public FullHttpResponse process() throws Exception {
		FullHttpResponse response;
		switch (renderType) {
		case JSON:
			response = HttpReaderUtil.renderJSON(data);
			break;
		case TEXT:
			response = HttpReaderUtil.renderText(data);
			break;
		case XML:
			response = HttpReaderUtil.renderXML(data);
			break;
		case HTML:
			response = HttpReaderUtil.renderHTML(data);
			break;
		default:
			response = HttpReaderUtil.getErroResponse();
			logger.error("unkown render type");
		}
		return response;
	}

}
