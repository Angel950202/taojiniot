package com.taojin.iot.base.comm.dao.impl;

import com.taojin.iot.base.comm.Filter;
import com.taojin.iot.base.comm.Filter.Operator;
import com.taojin.iot.base.comm.Order;
import com.taojin.iot.base.comm.Order.Direction;
import com.taojin.iot.base.comm.Page;
import com.taojin.iot.base.comm.Pageable;
import com.taojin.iot.base.comm.dao.BaseDao;
import com.taojin.iot.base.comm.entity.BaseEntity;
import com.taojin.iot.base.comm.entity.OrderEntity;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.persistence.criteria.*;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Dao - 基类
 * 
 * 王杰
 * 
 */
public abstract class BaseDaoImpl<T, ID extends Serializable> implements BaseDao<T, ID> {

	/** 实体类类型 */
	private Class<T> entityClass;

	/** 别名数 */
	private static volatile long aliasCount = 0;

	@PersistenceContext
	protected EntityManager entityManager;

	@SuppressWarnings("unchecked")
	public BaseDaoImpl() {
		Type type = getClass().getGenericSuperclass();
		Type[] parameterizedType = ((ParameterizedType) type).getActualTypeArguments();
		entityClass = (Class<T>) parameterizedType[0];
	}

	@Override
	public T find(ID id) {
		if (id != null) {
			return entityManager.find(entityClass, id);
		}
		return null;
	}

	@Override
	public T find(ID id, LockModeType lockModeType) {
		if (id != null) {
			if (lockModeType != null) {
				return entityManager.find(entityClass, id, lockModeType);
			} else {
				return entityManager.find(entityClass, id);
			}
		}
		return null;
	}

	@Override
	public List<T> findList(Integer first, Integer count, List<Filter> filters, List<Order> orders) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(entityClass);
		criteriaQuery.select(criteriaQuery.from(entityClass));
		return findList(criteriaQuery, first, count, filters, orders);
	}

	@Override
	public Page<T> findPage(Pageable pageable) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(entityClass);
		criteriaQuery.select(criteriaQuery.from(entityClass));
		return findPage(criteriaQuery, pageable);
	}

	@Override
	public long count(Filter... filters) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(entityClass);
		criteriaQuery.select(criteriaQuery.from(entityClass));
		return count(criteriaQuery, filters != null ? Arrays.asList(filters) : null);
	}

	@Override
	public void persist(T entity) {
		Assert.notNull(entity);
		entityManager.persist(entity);
	}

	@Override
	public T merge(T entity) {
		Assert.notNull(entity);
		return entityManager.merge(entity);
	}

	@Override
	public void remove(T entity) {
		if (entity != null) {
			entityManager.remove(entity);
		}
	}

	@Override
	public void refresh(T entity) {
		if (entity != null) {
			entityManager.refresh(entity);
		}
	}

	@Override
	public void refresh(T entity, LockModeType lockModeType) {
		if (entity != null) {
			if (lockModeType != null) {
				entityManager.refresh(entity, lockModeType);
			} else {
				entityManager.refresh(entity);
			}
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public ID getIdentifier(T entity) {
		Assert.notNull(entity);
		return (ID) entityManager.getEntityManagerFactory().getPersistenceUnitUtil().getIdentifier(entity);
	}

	@Override
	public boolean isManaged(T entity) {
		return entityManager.contains(entity);
	}

	@Override
	public void detach(T entity) {
		entityManager.detach(entity);
	}

	@Override
	public void lock(T entity, LockModeType lockModeType) {
		if (entity != null && lockModeType != null) {
			entityManager.lock(entity, lockModeType);
		}
	}

	@Override
	public void clear() {
		entityManager.clear();
	}

	@Override
	public void flush() {
		entityManager.flush();
	}

	protected List<T> findList(CriteriaQuery<T> criteriaQuery, Integer first, Integer count, List<Filter> filters, List<Order> orders) {
		Assert.notNull(criteriaQuery);
		Assert.notNull(criteriaQuery.getSelection());
		Assert.notEmpty(criteriaQuery.getRoots());

//		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
//		Root<T> root = getRoot(criteriaQuery);
		addRestrictions(criteriaQuery, filters);
		addOrders(criteriaQuery, orders);
//		if (criteriaQuery.getOrderList().isEmpty()) {
//			if (OrderEntity.class.isAssignableFrom(entityClass)) {
//				criteriaQuery.orderBy(criteriaBuilder.asc(root.get(OrderEntity.ORDER_PROPERTY_NAME)));
//			} else {
//				criteriaQuery.orderBy(criteriaBuilder.desc(root.get(OrderEntity.CREATE_DATE_PROPERTY_NAME)));
//			}
//		}
		TypedQuery<T> query = entityManager.createQuery(criteriaQuery).setFlushMode(FlushModeType.COMMIT);
		if (first != null) {
			query.setFirstResult(first);
		}
		if (count != null) {
			query.setMaxResults(count);
		}
		return query.getResultList();
	}

	protected Page<T> findPage(CriteriaQuery<T> criteriaQuery, Pageable pageable) {
		Assert.notNull(criteriaQuery);
		Assert.notNull(criteriaQuery.getSelection());
		Assert.notEmpty(criteriaQuery.getRoots());

		if (pageable == null) {
			pageable = new Pageable();
		}
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		Root<T> root = getRoot(criteriaQuery);
		addRestrictions(criteriaQuery, pageable);
		addOrders(criteriaQuery, pageable);
		if (criteriaQuery.getOrderList().isEmpty()) {
			if (OrderEntity.class.isAssignableFrom(entityClass)) {
				criteriaQuery.orderBy(criteriaBuilder.asc(root.get(OrderEntity.ORDER_PROPERTY_NAME)));
			} else {
				criteriaQuery.orderBy(criteriaBuilder.desc(root.get(BaseEntity.CREATE_DATE_PROPERTY_NAME)));
			}
		}
		long total = count(criteriaQuery, null);
		int totalPages = (int) Math.ceil((double) total / (double) pageable.getPageSize());
		if (totalPages < pageable.getPageNumber()) {
			return new Page<T>(new ArrayList<T>(), 0, pageable);
//			pageable.setPageNumber(totalPages);
		}
		TypedQuery<T> query = entityManager.createQuery(criteriaQuery).setFlushMode(FlushModeType.COMMIT);
		query.setFirstResult((pageable.getPageNumber() - 1) * pageable.getPageSize());
		query.setMaxResults(pageable.getPageSize());
		return new Page<T>(query.getResultList(), total, pageable);
	}

	protected Long count(CriteriaQuery<T> criteriaQuery, List<Filter> filters) {
		Assert.notNull(criteriaQuery);
		Assert.notNull(criteriaQuery.getSelection());
		Assert.notEmpty(criteriaQuery.getRoots());

		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		addRestrictions(criteriaQuery, filters);

		CriteriaQuery<Long> countCriteriaQuery = criteriaBuilder.createQuery(Long.class);
		for (Root<?> root : criteriaQuery.getRoots()) {
			Root<?> dest = countCriteriaQuery.from(root.getJavaType());
			dest.alias(getAlias(root));
			copyJoins(root, dest);
		}

		Root<?> countRoot = getRoot(countCriteriaQuery, criteriaQuery.getResultType());
		countCriteriaQuery.select(criteriaBuilder.count(countRoot));

		if (criteriaQuery.getGroupList() != null) {
			countCriteriaQuery.groupBy(criteriaQuery.getGroupList());
		}
		if (criteriaQuery.getGroupRestriction() != null) {
			countCriteriaQuery.having(criteriaQuery.getGroupRestriction());
		}
		if (criteriaQuery.getRestriction() != null) {
			countCriteriaQuery.where(criteriaQuery.getRestriction());
		}
		return entityManager.createQuery(countCriteriaQuery).setFlushMode(FlushModeType.COMMIT).getSingleResult();
	}

	private synchronized String getAlias(Selection<?> selection) {
		if (selection != null) {
			String alias = selection.getAlias();
			if (alias == null) {
				if (aliasCount >= 1000) {
					aliasCount = 0;
				}
				alias = "agentGeneratedAlias" + aliasCount++;
				selection.alias(alias);
			}
			return alias;
		}
		return null;
	}

	private Root<T> getRoot(CriteriaQuery<T> criteriaQuery) {
		if (criteriaQuery != null) {
			return getRoot(criteriaQuery, criteriaQuery.getResultType());
		}
		return null;
	}

	private Root<T> getRoot(CriteriaQuery<?> criteriaQuery, Class<T> clazz) {
		if (criteriaQuery != null && criteriaQuery.getRoots() != null && clazz != null) {
			for (Root<?> root : criteriaQuery.getRoots()) {
				if (clazz.equals(root.getJavaType())) {
					return (Root<T>) root.as(clazz);
				}
			}
		}
		return null;
	}

	private void copyJoins(From<?, ?> from, From<?, ?> to) {
		for (Join<?, ?> join : from.getJoins()) {
			Join<?, ?> toJoin = to.join(join.getAttribute().getName(), join.getJoinType());
			toJoin.alias(getAlias(join));
			copyJoins(join, toJoin);
		}
		for (Fetch<?, ?> fetch : from.getFetches()) {
			Fetch<?, ?> toFetch = to.fetch(fetch.getAttribute().getName());
			copyFetches(fetch, toFetch);
		}
	}

	private void copyFetches(Fetch<?, ?> from, Fetch<?, ?> to) {
		for (Fetch<?, ?> fetch : from.getFetches()) {
			Fetch<?, ?> toFetch = to.fetch(fetch.getAttribute().getName());
			copyFetches(fetch, toFetch);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void addRestrictions(CriteriaQuery<T> criteriaQuery, List<Filter> filters) {
		if (criteriaQuery == null || filters == null || filters.isEmpty()) {
			return;
		}
		Root<T> root = getRoot(criteriaQuery);
		if (root == null) {
			return;
		}
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		Predicate restrictions = criteriaQuery.getRestriction() != null ? criteriaQuery.getRestriction() : criteriaBuilder.conjunction();
		for (Filter filter : filters) {
			if (filter == null || StringUtils.isEmpty(filter.getProperty())) {
				continue;
			}
			if (filter.getOperator() == Operator.eq && filter.getValue() != null) {
				if (filter.getIgnoreCase() != null && filter.getIgnoreCase() && filter.getValue() instanceof String) {
					restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(criteriaBuilder.lower(root.<String> get(filter.getProperty())), ((String) filter.getValue()).toLowerCase()));
				} else {
					restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get(filter.getProperty()), filter.getValue()));
				}
			} else if (filter.getOperator() == Operator.ne && filter.getValue() != null) {
				if (filter.getIgnoreCase() != null && filter.getIgnoreCase() && filter.getValue() instanceof String) {
					restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.notEqual(criteriaBuilder.lower(root.<String> get(filter.getProperty())), ((String) filter.getValue()).toLowerCase()));
				} else {
					restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.notEqual(root.get(filter.getProperty()), filter.getValue()));
				}
			} else if (filter.getOperator() == Operator.gt && filter.getValue() != null) {
				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.gt(root.<Number> get(filter.getProperty()), (Number) filter.getValue()));
			} else if (filter.getOperator() == Operator.lt && filter.getValue() != null) {
				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.lt(root.<Number> get(filter.getProperty()), (Number) filter.getValue()));
			} else if (filter.getOperator() == Operator.ge && filter.getValue() != null) {
				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.ge(root.<Number> get(filter.getProperty()), (Number) filter.getValue()));
			} else if (filter.getOperator() == Operator.le && filter.getValue() != null) {
				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.le(root.<Number> get(filter.getProperty()), (Number) filter.getValue()));
			} else if (filter.getOperator() == Operator.like && filter.getValue() != null && filter.getValue() instanceof String) {
				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.like(root.<String> get(filter.getProperty()), (String) filter.getValue()));
			} else if (filter.getOperator() == Operator.in && filter.getValue() != null) {
				if(filter.getValue().getClass().getName().equals("java.util.ArrayList")||filter.getValue().getClass().getName().equals("java.util.Arrays$ArrayList")){
				     List list = (List)filter.getValue();
				     if(list!=null&&list.size()>0&&list.get(0).getClass().getName().equals("java.lang.Long")){
				         restrictions = criteriaBuilder.and(restrictions, root.get(filter.getProperty()).in((List<Long>)filter.getValue()));
				     }else{
						restrictions = criteriaBuilder.and(restrictions, root.get(filter.getProperty()).in(filter.getValue()));
					 }
				}
			} else if (filter.getOperator() == Operator.isNull) {
				restrictions = criteriaBuilder.and(restrictions, root.get(filter.getProperty()).isNull());
			} else if (filter.getOperator() == Operator.isNotNull) {
				restrictions = criteriaBuilder.and(restrictions, root.get(filter.getProperty()).isNotNull());
			} else if (filter.getOperator() == Operator.between && filter.getValue() != null && filter.getValue1() != null){
				restrictions=criteriaBuilder.and(restrictions, criteriaBuilder.between(root.<String> get(filter.getProperty()),(String)filter.getValue(),(String)filter.getValue1()));
			}else if(filter.getOperator() == Operator.between_two && filter.getValue() != null && filter.getValue1() != null){
				SimpleDateFormat sDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				
				String value = String.valueOf(filter.getValue());
				String value1 = String.valueOf(filter.getValue1());
				if(value!=null&&value.length()>=10&&value1!=null&&value1.length()>=10){
					if(value.length()==10){
						value = value+" 00:00:00";
					}
					if(value1.length()==10){
						value1 = value1+" 23:59:59";
					}
					try {
						restrictions=criteriaBuilder.and(restrictions, criteriaBuilder.between(root.<Date> get(filter.getProperty()),sDateFormat.parse(value),sDateFormat.parse(value1)));
					} catch (ParseException e) {
						e.printStackTrace();
					}

				}
			}
		}
		criteriaQuery.where(restrictions);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void addRestrictions(CriteriaQuery<T> criteriaQuery, Pageable pageable) {
		if (criteriaQuery == null || pageable == null) {
			return;
		}
		Root<T> root = getRoot(criteriaQuery);
		if (root == null) {
			return;
		}
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		Predicate restrictions = criteriaQuery.getRestriction() != null ? criteriaQuery.getRestriction() : criteriaBuilder.conjunction();
		if (StringUtils.isNotEmpty(pageable.getSearchProperty()) && StringUtils.isNotEmpty(pageable.getSearchValue())) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.like(root.<String> get(pageable.getSearchProperty()), "%" + pageable.getSearchValue() + "%"));
		}
		if (pageable.getFilters() != null) {
			for (Filter filter : pageable.getFilters()) {
				if (filter == null || StringUtils.isEmpty(filter.getProperty())) {
					continue;
				}
				if (filter.getOperator() == Operator.eq && filter.getValue() != null) {
					if (filter.getIgnoreCase() != null && filter.getIgnoreCase() && filter.getValue() instanceof String) {
						restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(criteriaBuilder.lower(root.<String> get(filter.getProperty())), ((String) filter.getValue()).toLowerCase()));
					} else {
						restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get(filter.getProperty()), filter.getValue()));
					}
				} else if (filter.getOperator() == Operator.ne && filter.getValue() != null) {
					if (filter.getIgnoreCase() != null && filter.getIgnoreCase() && filter.getValue() instanceof String) {
						restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.notEqual(criteriaBuilder.lower(root.<String> get(filter.getProperty())), ((String) filter.getValue()).toLowerCase()));
					} else {
						restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.notEqual(root.get(filter.getProperty()), filter.getValue()));
					}
				} else if (filter.getOperator() == Operator.gt && filter.getValue() != null) {
					restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.gt(root.<Number> get(filter.getProperty()), (Number) filter.getValue()));
				} else if (filter.getOperator() == Operator.lt && filter.getValue() != null) {
					restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.lt(root.<Number> get(filter.getProperty()), (Number) filter.getValue()));
				} else if (filter.getOperator() == Operator.ge && filter.getValue() != null) {
					restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.ge(root.<Number> get(filter.getProperty()), (Number) filter.getValue()));
				} else if (filter.getOperator() == Operator.le && filter.getValue() != null) {
					restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.le(root.<Number> get(filter.getProperty()), (Number) filter.getValue()));
				} else if (filter.getOperator() == Operator.like && filter.getValue() != null && filter.getValue() instanceof String) {
					restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.like(root.<String> get(filter.getProperty()), (String) filter.getValue()));
				} else if (filter.getOperator() == Operator.in && filter.getValue() != null) {
					if(filter.getValue().getClass().getName().equals("java.util.ArrayList")||filter.getValue().getClass().getName().equals("java.util.Arrays$ArrayList")){
					     List list = (List)filter.getValue();
					     if(list!=null&&list.size()>0&&list.get(0).getClass().getName().equals("java.lang.Long")){
					         restrictions = criteriaBuilder.and(restrictions, root.get(filter.getProperty()).in((List<Long>)filter.getValue()));
					     }else{
							restrictions = criteriaBuilder.and(restrictions, root.get(filter.getProperty()).in(filter.getValue()));
						 }
					}
//					restrictions = criteriaBuilder.and(restrictions, root.get(filter.getProperty()).in(filter.getValue()));
				} else if (filter.getOperator() == Operator.isNull) {
					restrictions = criteriaBuilder.and(restrictions, root.get(filter.getProperty()).isNull());
				} else if (filter.getOperator() == Operator.isNotNull) {
					restrictions = criteriaBuilder.and(restrictions, root.get(filter.getProperty()).isNotNull());
				} else if (filter.getOperator() == Operator.between && filter.getValue() != null && filter.getValue1() != null){
					restrictions=criteriaBuilder.and(restrictions, criteriaBuilder.between(root.<String> get(filter.getProperty()),(String)filter.getValue(),(String) filter.getValue1()));
				}else if(filter.getOperator() == Operator.between_two && filter.getValue() != null && filter.getValue1() != null){
					
					/**
					 * @author king
					 * @Date 2019-08-28
					 * 1.只有开始时间,默认结束时间为当前时间
					 * 2.只有结束时间,默认开始时间为0000-00-00 00:00:00
					 * 3.字符串长度为10,说明传过来的值是年月日，默认加上时分秒
					 * 4.字符串长度为19,说明穿过来的值是年月日时分秒，不需要做任何处理
					 */
					try {
						SimpleDateFormat sDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						String startTime = String.valueOf(filter.getValue());
						String endTime = String.valueOf(filter.getValue1());

						if (!"".equals(startTime) && startTime != null || "".equals(endTime) || endTime == null) {
							if(startTime.length()==10){
								startTime = startTime+" 00:00:00";
								restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.between(root.<Date> get(filter.getProperty()), sDateFormat.parse(startTime),new Date()));
							}
							if(startTime.length()==19){
								restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.between(root.<Date> get(filter.getProperty()), sDateFormat.parse(startTime),new Date()));
							}
						}

						if (!"".equals(endTime) && endTime != null || "".equals(startTime) || startTime == null) {
							if(endTime.length()==10){
								startTime = "0000-00-00 00:00:00";
								endTime = endTime+" 23:59:59";
								restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.between(root.<Date> get(filter.getProperty()), sDateFormat.parse(startTime),new Date()));
							}
							if (endTime.length()==19) {
								restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.between(root.<Date> get(filter.getProperty()), sDateFormat.parse(startTime),new Date()));
							}
						}
						
						if(!"".equals(endTime) && endTime != null && !"".equals(startTime) && startTime != null){
							if(startTime.length()==10 && endTime.length()==10){
								startTime = startTime+" 00:00:00";
								endTime = endTime+" 23:59:59";
								restrictions=criteriaBuilder.and(restrictions, criteriaBuilder.between(root.<Date> get(filter.getProperty()),sDateFormat.parse(startTime),sDateFormat.parse(endTime)));
							}
							if(startTime.length()==19 && endTime.length()==19){
								restrictions=criteriaBuilder.and(restrictions, criteriaBuilder.between(root.<Date> get(filter.getProperty()),sDateFormat.parse(startTime),sDateFormat.parse(endTime)));

							}
						}
					} catch (Exception e) {
						// TODO: 时间查询异常
						e.printStackTrace();
					}
					
				}
			}
		}
		criteriaQuery.where(restrictions);
	}

	private void addOrders(CriteriaQuery<T> criteriaQuery, List<Order> orders) {
		if (criteriaQuery == null || orders == null || orders.isEmpty()) {
			return;
		}
		Root<T> root = getRoot(criteriaQuery);
		if (root == null) {
			return;
		}
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		List<javax.persistence.criteria.Order> orderList = new ArrayList<javax.persistence.criteria.Order>();
		if (!criteriaQuery.getOrderList().isEmpty()) {
			orderList.addAll(criteriaQuery.getOrderList());
		}
		for (Order order : orders) {
			if (order.getDirection() == Direction.asc) {
				orderList.add(criteriaBuilder.asc(root.get(order.getProperty())));
			} else if (order.getDirection() == Direction.desc) {
				orderList.add(criteriaBuilder.desc(root.get(order.getProperty())));
			}
		}
		criteriaQuery.orderBy(orderList);
	}

	private void addOrders(CriteriaQuery<T> criteriaQuery, Pageable pageable) {
		if (criteriaQuery == null || pageable == null) {
			return;
		}
		Root<T> root = getRoot(criteriaQuery);
		if (root == null) {
			return;
		}
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		List<javax.persistence.criteria.Order> orderList = new ArrayList<javax.persistence.criteria.Order>();
		if (!criteriaQuery.getOrderList().isEmpty()) {
			orderList.addAll(criteriaQuery.getOrderList());
		}
		if (StringUtils.isNotEmpty(pageable.getOrderField()) && pageable.getOrderDirection() != null) {
			if (pageable.getOrderDirection() == Direction.asc) {
				orderList.add(criteriaBuilder.asc(root.get(pageable.getOrderField())));
			} else if (pageable.getOrderDirection() == Direction.desc) {
				orderList.add(criteriaBuilder.desc(root.get(pageable.getOrderField())));
			}
		}
		if (pageable.getOrders() != null) {
			for (Order order : pageable.getOrders()) {
				if (order.getDirection() == Direction.asc) {
					orderList.add(criteriaBuilder.asc(root.get(order.getProperty())));
				} else if (order.getDirection() == Direction.desc) {
					orderList.add(criteriaBuilder.desc(root.get(order.getProperty())));
				}
			}
		}
		criteriaQuery.orderBy(orderList);
	}

}