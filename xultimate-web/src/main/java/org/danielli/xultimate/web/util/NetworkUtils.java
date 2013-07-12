package org.danielli.xultimate.web.util;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * 网络工具类。
 * 
 * @author Daniel Li
 * @since 15 Jun 2013
 * @see InetAddress
 * @see Inet4Address
 * @see Inet6Address
 * @see NetworkInterface
 */
public class NetworkUtils {
	
	/**
	 * 本机默认主机IP地址。
	 */
	public static final String LOCAL_HOST_DEFAULT_ADDRESS = "127.0.0.1";
	
	/**
	 * 本机默认主机名称。
	 */
	public static final String LOCAL_HOST_DEFAULT_NAME = "localhost";
	
	/**
	 * 获取本机主机IP地址。
	 * 
	 * @return	本机主机IP地址。
	 */
	public static String getLocalHostAddress() {
		try {
			return InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			return LOCAL_HOST_DEFAULT_ADDRESS;
		}
	}
	
	/**
	 * 获取本机主机名称。
	 * 
	 * @return	本机主机名称。
	 */
	public static String getLocalHostName() {
		try {
			return InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException e) {
			return LOCAL_HOST_DEFAULT_NAME;
		}
	}
	
	/**
	 * 获取所有IP4类型的网卡地址。
	 * 
	 * @return 所有IP4类型的网卡地址
	 */
	public static List<Inet4Address> getInet4AddressList() {
		List<Inet4Address> inet4Addresses = new ArrayList<>();
		try {
			Enumeration<NetworkInterface> netInterfaces = NetworkInterface.getNetworkInterfaces();
			while (netInterfaces.hasMoreElements()) {
				Enumeration<InetAddress> ipInetAddresses = netInterfaces.nextElement().getInetAddresses();
				while (ipInetAddresses.hasMoreElements()) {
					InetAddress address = ipInetAddresses.nextElement();
					if (address instanceof Inet4Address) {
						inet4Addresses.add((Inet4Address) address);
					}
				}
			}
		} catch (SocketException e) {
		}
		return inet4Addresses;
	}
	
	/**
	 * 获取所有IP6类型的网卡地址。
	 * 
	 * @return 所有IP6类型的网卡地址
	 */
	public static List<Inet6Address> getInet6AddressList() {
		List<Inet6Address> inet6Addresses = new ArrayList<>();
		try {
			Enumeration<NetworkInterface> netInterfaces = NetworkInterface.getNetworkInterfaces();
			while (netInterfaces.hasMoreElements()) {
				Enumeration<InetAddress> ipInetAddresses = netInterfaces.nextElement().getInetAddresses();
				while (ipInetAddresses.hasMoreElements()) {
					InetAddress address = ipInetAddresses.nextElement();
					if (address instanceof Inet6Address) {
						inet6Addresses.add((Inet6Address) address);
					}
				}
			}
		} catch (SocketException e) {
		}
		return inet6Addresses;
	}
}
