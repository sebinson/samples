package net.sebinson.common.cache.encache;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import net.sebinson.common.cache.Cache;
import net.sebinson.common.cache.CacheException;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

public class EhCacheCache implements Cache {

	private final Ehcache ehcache;

	public EhCacheCache(Ehcache ehcache) {
		if (ehcache == null) {
			throw new CacheException("error ehcache");
		}
		this.ehcache = ehcache;
	}

	@Override
	public String getName() throws CacheException {
		return this.ehcache.getName();
	}


	@Override
	public Object get(Object key) throws CacheException {

		if (key == null) {
			return null;
		} else {
			Element element = ehcache.get(key);
			if (element == null) {
				return null;
			} else {
				return element.getObjectValue();
			}
		}

	}

	@Override
	public void put(Object key, Object value) throws CacheException {
		Element element = new Element(key, value);
		ehcache.put(element);
	}

	@Override
	public void evict(Object key) throws CacheException {
		ehcache.remove(key);
	}

	@Override
	public void clear() throws CacheException {
		ehcache.removeAll();
	}

	@Override
	public Set<Object> keys() throws CacheException {
		@SuppressWarnings("unchecked")
		List<Object> keys = ehcache.getKeys();
		if (!(keys == null || keys.isEmpty())) {
			return Collections.unmodifiableSet(new LinkedHashSet<Object>(keys));
		} else {
			return Collections.emptySet();
		}
	}

	@Override
	public long size() throws CacheException {
		return ehcache.getSize();
	}

}
