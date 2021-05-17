package com.taojin.iot.service.task.entity;


import com.taojin.iot.base.comm.entity.BaseEntity;




import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;



@Entity
@Table(name = "iot_work_order")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "iot_work_order_sequence")
public class WorkOrder extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4642689644693568095L;

	/**
     * 生产任务单编号
     */
    private String orderNumber;
    
    /**
     * 生产型号
     */
    private String productionModel;

    /**
     * 计划开工时间
     */

    private String startTime;
    
    /**
     * 计划完成时间
     */
   // @Column(columnDefinition="timestamp",insertable=false,updatable=false)
    private String endTime;
    
    /**
     * 完成所用时间 /s
     */  
    private long finishTime;
    /**
     * 生产线
     */
    private String lineName;
    
    /**
     * 生产线
     */
    private String lineNumber;
    
    /**
     * 生产数量
     */
    private int count;


    private String status;

    private String creatorName;

    private String creatTime;

    private String updateTime;

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getProductionModel() {
		return productionModel;
	}

	public void setProductionModel(String productionModel) {
		this.productionModel = productionModel;
	}
//    @Column(name="start_time",columnDefinition="TIMESTAMP",insertable = false,updatable = false)
//    @Generated(GenerationTime.INSERT)
	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
//    @Column(name="end_time",columnDefinition="TIMESTAMP",insertable = false,updatable = false)
//    @Generated(GenerationTime.INSERT)
	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getLineName() {
		return lineName;
	}

	public void setLineName(String lineName) {
		this.lineName = lineName;
	}

	public String getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(String lineNumber) {
		this.lineNumber = lineNumber;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}
  /*  @Column(name="creat_time",columnDefinition="TIMESTAMP",insertable = false,updatable = false)
    @Generated(GenerationTime.INSERT)*/
	public String getCreatTime() {
		return creatTime;
	}

	public void setCreatTime(String creatTime) {
		this.creatTime = creatTime;
	}

	public long getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(long finishTime) {
		this.finishTime = finishTime;
	}
  /*@Column(name="update_time",columnDefinition="TIMESTAMP",insertable = false,updatable = false)
    @Generated(GenerationTime.INSERT)*/
	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
    
   
}
