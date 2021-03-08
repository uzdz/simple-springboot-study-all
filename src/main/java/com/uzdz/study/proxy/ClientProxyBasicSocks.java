package com.uzdz.study.proxy;

import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;

public class ClientProxyBasicSocks {

	public static void main(String args[]) throws Exception {

		// 随机获取一个IP代理
		String proxyLine = ProxyServer.getProxyLine(ProxyServer.ServerGetProxyUrl);

		// 代理服务器
		String proxyHost = ProxyServer.getIp(proxyLine);
		Integer proxyPort = ProxyServer.getPort(proxyLine);

		// 目标地址
		String targetUrl = "http://httpbin.org/get";

		// http代理: Proxy.Type.HTTP, socks代理: Proxy.Type.SOCKS
		Proxy.Type proxyType = Proxy.Type.SOCKS;
		try {
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
}
