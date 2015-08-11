package test.fmc.simpleclient.utils;

import java.util.HashMap;
import java.util.Map;

import javax.naming.Context;
import javax.naming.NameClassPair;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;

@Deprecated
public class LookupUtil {

//	private static Log _log = LogFactory.getLog(ContextUtil.class);

	/**
	 * Возвращает значение указанного параметра из контекста веб-приложения.
	 * 
	 * @param name
	 *            ниаменование параметра
	 * @return значение параметра
	 * 
	 * @throws NamingException
	 */
	@SuppressWarnings("unchecked")
	public static <T> T lookup(String name) {

		try {
			Context context = new javax.naming.InitialContext();
			T object;
			// только tomcat
			Context envCtx = (Context) context.lookup("java:comp/env");
			object = (T) envCtx.lookup(name);
			if (object == null) {
				object = (T) context.lookup("java:" + name);
			}
			return object;
		} catch (NamingException e) {
	//		_log.warn(e);
			return null;
		}

	}

	/**
	 * Возвращает все параметры пути контекста веб-приложения указанного класса.
	 * 
	 * @param path
	 *            путь к параметрам контекста
	 * @param cls
	 *            класс получаемых объектов
	 * 
	 * @return список параметров
	 */
	@SuppressWarnings("unchecked")
	public static <T> Map<String, T> lookupAll(String path, Class<T> cls) {
		Map<String, T> result = new HashMap<String, T>();
		try {
			Context context = new javax.naming.InitialContext();
			Context envCtx = (Context) context.lookup("java:comp/env");
			NamingEnumeration<NameClassPair> en = envCtx.list(path);

			while (en.hasMore()) {
				NameClassPair pair = en.next();
				String envName = pair.getName();
				Object value = envCtx.lookup(path + envName);
				if (value != null && cls.isAssignableFrom(value.getClass())) {
					result.put(envName, (T) value);
				}
			}
		} catch (NamingException e) {

		}
		return result;
	}
}