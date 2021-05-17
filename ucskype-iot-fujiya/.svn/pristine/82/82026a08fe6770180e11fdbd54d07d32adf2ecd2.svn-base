/*
 * 
 * 
 * 
 */
package com.taojin.iot.base.comm.service.impl;

import com.taojin.iot.base.comm.Filter;
import com.taojin.iot.base.comm.Order;
import com.taojin.iot.base.comm.Page;
import com.taojin.iot.base.comm.Pageable;
import com.taojin.iot.base.comm.dao.BaseDao;
import com.taojin.iot.base.comm.entity.BaseEntity;
import com.taojin.iot.base.comm.service.BaseService;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Service - 基类
 * 
 * 王杰
 * 
 */
@Transactional
public class BaseServiceImpl<T, ID extends Serializable> implements BaseService<T, ID> {

	/** 更新忽略属性 */
	private static final String[] UPDATE_IGNORE_PROPERTIES = new String[] { BaseEntity.ID_PROPERTY_NAME, BaseEntity.CREATE_DATE_PROPERTY_NAME, BaseEntity.MODIFY_DATE_PROPERTY_NAME };

	/** baseDao */
	private BaseDao<T, ID> baseDao;

	public void setBaseDao(BaseDao<T, ID> baseDao) {
		this.baseDao = baseDao;
	}

	@Override
	@Transactional(readOnly = true)
	public T find(ID id) {
		return baseDao.find(id);
	}
	
	@Override
	@SuppressWarnings("static-access")
	@Transactional(readOnly = true)
	public T getByParam(String field,Object uniqueParam){
		List<Filter> filters = new ArrayList<Filter>();
		filters.add(new Filter().eq(field, uniqueParam));
		List<T> tList = this.baseDao.findList(null, 1, filters, null);
		if(tList!=null&&tList.size()>0){
			return tList.get(0);
		}
		return null;
	}
	
	@Override
	@SuppressWarnings("static-access")
	@Transactional(readOnly = true)
	public T getByParams(String field0,Object uniqueParam0,String field1,Object uniqueParam1){
		List<Filter> filters = new ArrayList<Filter>();
		filters.add(new Filter().eq(field0, uniqueParam0));
		filters.add(new Filter().eq(field1, uniqueParam1));
		List<T> tList = this.baseDao.findList(null, 1, filters, null);
		if(tList!=null&&tList.size()>0){
			return tList.get(0);
		}
		return null;
	}
	
	@Override
	@SuppressWarnings("static-access")
	@Transactional(readOnly = true)
	public T getByParams(String field0,Object uniqueParam0,String field1,Object uniqueParam1,String field2,Object uniqueParam2,String field3,Object uniqueParam3){
		List<Filter> filters = new ArrayList<Filter>();
		filters.add(new Filter().eq(field0, uniqueParam0));
		filters.add(new Filter().eq(field1, uniqueParam1));
		filters.add(new Filter().eq(field2, uniqueParam2));
		filters.add(new Filter().eq(field3, uniqueParam3));
		List<T> tList = this.baseDao.findList(null, 1, filters, null);
		if(tList!=null&&tList.size()>0){
			return tList.get(0);
		}
		return null;
	}
	
	@Override
	@SuppressWarnings("static-access")
	@Transactional(readOnly = true)
	public T getByParams(String field0,Object uniqueParam0,String field1,Object uniqueParam1,String field2,Object uniqueParam2,String field3,Object uniqueParam3,String field4,Object uniqueParam4){
		List<Filter> filters = new ArrayList<Filter>();
		filters.add(new Filter().eq(field0, uniqueParam0));
		filters.add(new Filter().eq(field1, uniqueParam1));
		filters.add(new Filter().eq(field2, uniqueParam2));
		filters.add(new Filter().eq(field3, uniqueParam3));
		filters.add(new Filter().eq(field4, uniqueParam4));
		List<T> tList = this.baseDao.findList(null, 1, filters, null);
		if(tList!=null&&tList.size()>0){
			return tList.get(0);
		}
		return null;
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<T> findAll() {
		return findList(null, null, null, null);
	}

	@Override
	@Transactional(readOnly = true)
	public List<T> findList(ID... ids) {
		List<T> result = new ArrayList<T>();
		if (ids != null) {
			for (ID id : ids) {
				T entity = find(id);
				if (entity != null) {
					result.add(entity);
				}
			}
		}
		return result;
	}

	@Override
	@Transactional(readOnly = true)
	public List<T> findList(Integer count, List<Filter> filters, List<Order> orders) {
		return findList(null, count, filters, orders);
	}

	@Override
	@Transactional(readOnly = true)
	public List<T> findList(Integer first, Integer count, List<Filter> filters, List<Order> orders) {
		return baseDao.findList(first, count, filters, orders);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<T> findPage(Pageable pageable) {
		return baseDao.findPage(pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public long count() {
		return count(new Filter[] {});
	}

	@Override
	@Transactional(readOnly = true)
	public long count(Filter... filters) {
		return baseDao.count(filters);
	}

	@Override
	@Transactional(readOnly = true)
	public boolean exists(ID id) {
		return baseDao.find(id) != null;
	}

	@Override
	@Transactional(readOnly = true)
	public boolean exists(Filter... filters) {
		return baseDao.count(filters) > 0;
	}

	@Override
	@Transactional
	public void save(T entity) {
		baseDao.persist(entity);
	}

	@Override
	@Transactional
	public T update(T entity) {
		return baseDao.merge(entity);
	}

	@Override
	@Transactional
	public T update(T entity, String... ignoreProperties) {
		Assert.notNull(entity);
		if (baseDao.isManaged(entity)) {
			throw new IllegalArgumentException("Entity must not be managed");
		}
		T persistant = baseDao.find(baseDao.getIdentifier(entity));
		if (persistant != null) {
			copyProperties(entity, persistant, (String[]) ArrayUtils.addAll(ignoreProperties, UPDATE_IGNORE_PROPERTIES));
			return update(persistant);
		} else {
			return update(entity);
		}
	}

	@Override
	@Transactional
	public void delete(ID id) {
		delete(baseDao.find(id));
	}

	@Override
	@Transactional
	public void delete(ID... ids) {
		if (ids != null) {
			for (ID id : ids) {
				delete(baseDao.find(id));
			}
		}
	}

	@Override
	@Transactional
	public void delete(T entity) {
		baseDao.remove(entity);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void copyProperties(Object source, Object target, String[] ignoreProperties) throws BeansException {
		Assert.notNull(source, "Source must not be null");
		Assert.notNull(target, "Target must not be null");

		PropertyDescriptor[] targetPds = BeanUtils.getPropertyDescriptors(target.getClass());
		List<String> ignoreList = (ignoreProperties != null) ? Arrays.asList(ignoreProperties) : null;
		for (PropertyDescriptor targetPd : targetPds) {
			if (targetPd.getWriteMethod() != null && (ignoreProperties == null || (!ignoreList.contains(targetPd.getName())))) {
				PropertyDescriptor sourcePd = BeanUtils.getPropertyDescriptor(source.getClass(), targetPd.getName());
				if (sourcePd != null && sourcePd.getReadMethod() != null) {
					try {
						Method readMethod = sourcePd.getReadMethod();
						if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
							readMethod.setAccessible(true);
						}
						Object sourceValue = readMethod.invoke(source);
						Object targetValue = readMethod.invoke(target);
						if (sourceValue != null && targetValue != null && targetValue instanceof Collection) {
							Collection collection = (Collection) targetValue;
							collection.clear();
							collection.addAll((Collection) sourceValue);
						} else {
							Method writeMethod = targetPd.getWriteMethod();
							if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
								writeMethod.setAccessible(true);
							}
							writeMethod.invoke(target, sourceValue);
						}
					} catch (Throwable ex) {
						throw new FatalBeanException("Could not copy properties from source to target", ex);
					}
				}
			}
		}
	}
	
}