package com.yxqm.console.cache;

import com.taobao.tair.DataEntry;
import com.taobao.tair.Result;
import com.taobao.tair.ResultCode;
import com.taobao.tair.impl.DefaultTairManager;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * 无默认namespace，所有操作必须指定namespace
 */
public class TairManager extends DefaultTairManager {

	private String servers;

	private String sysFix = TairConstants.TAIR_KEY_CONSOLE_BUSINESS_CODE;

	public String getServers() {
		return servers;
	}

	public void setServers(String servers) {
		this.servers = servers;
	}

	@Override
	public void init() {
		String[] arr = servers.split(",");
		configServerList = Arrays.asList(arr);

		super.init();
	}

	// /**
	// * 默认名字空间
	// * @param key
	// * @param <T>
	// * @return
	// */
	//// public <T> T getValue(Serializable key) {
	//// return this.getValue( TairConstants.NS_AUTH,this.getKey(key));
	//// }

	/**
	 * 指定名字空间
	 * 
	 * @param namespace
	 * @param key
	 * @param <T>
	 * @return
	 */
	public <T> T getValue(int namespace, Serializable key) {
		Result<DataEntry> resultEntry = super.get(namespace, this.getKey(key));
		if (resultEntry.isSuccess()) {
			DataEntry dataEntry = resultEntry.getValue();
			return null != dataEntry ? (T) dataEntry.getValue() : null;
		}
		return null;
	}

	// /**
	// * 指定失效时间的数据缓存
	// * @param key
	// * @param value
	// * @param expireTime
	// */
	// public void putDataCache(Serializable key, Serializable value, int
	// expireTime){
	// putCache(TairConstants.NS_AUTH, key, value, 0, expireTime);
	// }

	/**
	 * 指定名字空间的永久缓存
	 * 
	 * @param namespace
	 * @param key
	 * @param value
	 */
	public void putCache(int namespace, Serializable key, Serializable value) {
		putCache(namespace, key, value, 0, 0);
	}

	/**
	 * 带版本号校验的缓存更新操作
	 * 
	 * @param namespace
	 * @param key
	 * @param value
	 * @param version
	 */
	public void putCache(int namespace, Serializable key, Serializable value, int version) {
		putCache(namespace, key, value, version, 0);
	}

	/**
	 * 带返回状态码，所有参数可指定
	 * 
	 * @param namespace
	 * @param key
	 * @param value
	 * @param version
	 * @param expireTime
	 * @return
	 */
	public ResultCode put(int namespace, Serializable key, Serializable value, int version, int expireTime) {
		ResultCode resultCode = super.put(namespace, this.getKey(key), value, version, expireTime);
		return resultCode;
	}

	public void putCache(int namespace, Serializable key, Serializable value, int version, int expireTime) {
		ResultCode resultCode = super.put(namespace, this.getKey(key), value, version, expireTime);
		if (!resultCode.isSuccess()) {
			throw new RuntimeException("putCache error...ResultCode:" + resultCode.getCode());
		}
	}

	/**
	 * 带前缀，指定名字空间
	 * 
	 * @param prefix
	 * @param namespace
	 * @param key
	 * @param <T>
	 * @return
	 */
	public <T> T getValue(String prefix, int namespace, Serializable key) {
		return this.getValue(namespace, prefix + "_" + key);
	}

	// /**
	// * @Description: 支持KEY带前缀的同时，支持自定义过期时间和版本信息，采用DATA命名空间
	// * @param: @param prefix
	// * @param: @param key
	// * @param: @param value
	// * @param: @param version
	// * @param: @param expireTime
	// * @return: void
	// * @throws:
	// */
	// public void putCache(String prefix, Serializable key, Serializable value,
	// int version, int expireTime) {
	// putCache(TairConstants.NS_DATA, prefix+"_"+key, value, version,
	// expireTime);
	// }

	@Override
	public ResultCode delete(int namespace, Serializable key) {
		return super.delete(namespace, this.getKey(key));
	}

	@Override
	public Result<DataEntry> get(int namespace, Serializable key) {
		return super.get(namespace, this.getKey(key));
	}

	@Override
	public Result<Integer> incr(int namespace, Serializable key, int value, int defaultValue, int expireTime) {
		return super.incr(namespace, this.getKey(key), value, defaultValue, expireTime);
	}

	@Override
	public Result<Integer> getItemCount(int namespace, Serializable key) {
		return super.getItemCount(namespace, this.getKey(key));
	}

	@Override
	public ResultCode removeItems(int namespace, Serializable key, int offset, int count) {
		return super.removeItems(namespace, this.getKey(key), offset, count);
	}

	@Override
	public Result<DataEntry> getItems(int namespace, Serializable key, int offset, int count) {
		return super.getItems(namespace, this.getKey(key), offset, count);
	}

	@Override
	public Result<DataEntry> getAndRemove(int namespace, Serializable key, int offset, int count) {
		return super.getAndRemove(namespace, this.getKey(key), offset, count);
	}

	@Override
	public ResultCode addItems(int namespace, Serializable key, List<? extends Object> items, int maxCount, int version,
			int expireTime) {
		return super.addItems(namespace, this.getKey(key), items, maxCount, version, expireTime);
	}

	@Override
	public Result<Integer> decr(int namespace, Serializable key, int value, int defaultValue, int expireTime) {
		return super.decr(namespace, this.getKey(key), value, defaultValue, expireTime);
	}

	@Override
	public ResultCode invalid(int namespace, Serializable key) {
		return super.invalid(namespace, this.getKey(key));
	}

	/**
	 * 若TairManager在初始化的时候指定了系统前缀(sysFix)，则 key = sysFix + key
	 * 
	 * @param key
	 * @return
	 */
	private Serializable getKey(Serializable key) {
		if ((key instanceof String) && this.sysFix != null && "".equals(this.sysFix)) {
			return this.sysFix + (String) key;
		}

		return key;
	}
}
