package com.uzdz.study.proxy;

import java.io.InputStream;
import java.net.*;

public class ClientProxyBasicHttp {

	public static void main(String args[]) throws Exception {
		// 随机获取一个IP代理
		String proxyLine = ProxyServer.getProxyLine(ProxyServer.ServerGetProxyUrl);

		// 代理服务器
		String proxyHost = ProxyServer.getIp(proxyLine);
		Integer proxyPort = ProxyServer.getPort(proxyLine);

		// 目标地址
		String targetUrl = "https://www.baidu.com";

		// http代理: Proxy.Type.HTTP, socks代理: Proxy.Type.SOCKS
		Proxy.Type proxyType = Proxy.Type.HTTP;
		
		// 代理验证
		String proxyUser = "xxx";
		String proxyPwd = "xxx";

		try {
			// 设置验证
			Authenticator.setDefault(new ProxyAuthenticator(proxyUser, proxyPwd));
			
			// 创建代理服务器
			InetSocketAddress addr = new InetSocketAddress(proxyHost, proxyPort);
			Proxy proxy = new Proxy(proxyType, addr);
			// 访问目标网页
			URL url = new URL(targetUrl);
			URLConnection conn = url.openConnection(proxy);
			// 读取返回数据
			InputStream in = conn.getInputStream();
			// 将返回数据转换成字符串
			System.out.println(ProxyServer.IO2String(in));

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	static class ProxyAuthenticator extends Authenticator {
		private String authUser, authPwd;
		
		public ProxyAuthenticator(String authUser, String authPwd) {
			this.authUser = authUser;
			this.authPwd = authPwd;
		}
		
        public PasswordAuthentication getPasswordAuthentication() {
            return (new PasswordAuthentication(authUser, authPwd.toCharArray()));
        }
    }
}
