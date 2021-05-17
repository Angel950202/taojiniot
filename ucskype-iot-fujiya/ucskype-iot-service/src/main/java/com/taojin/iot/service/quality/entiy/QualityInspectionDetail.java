package com.taojin.iot.service.quality.entiy;

import static javax.persistence.FetchType.LAZY;

import com.taojin.iot.base.comm.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 质量检验单详情
 */
@Entity
@Table(name = "iot_quality_inspection_detail")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "iot_quality_inspection_detail_sequence")
public class QualityInspectionDetail extends BaseEntity {
	
	/**
	 * 质量检验单号
	 */
	private String receiptNumber;

	/**
	 * 生产任务编号
	 */
	private String orderNumber;

	/**
	 * 创建时间
	 */
	private String creatTime;
	/**
	 * 创建人
	 */
	private String creatorName;

	/**
	 * 计量单位
	 */
	private String unit;

	/**
	 * 物料代码
	 */
	private String materialCode;

	/**
	 * 物料名称
	 */
	private String materialName;

	/**
	 * 规格型号
	 */
	private String specification;

	/**
	 * 检验数量
	 */
	private Integer inspectionCount;
	/**
	 * 检验方式
	 */
	private String inspectionType;

	/**
	 * 检验标准
	 */
	private String inspectionStandardName;

	/**
	 * 合格品数量
	 */
	private Integer qualified;

	/**
	 * 不良品数量
	 */
	private Integer badCount;
	

	/**
	 * 不良部位
	 */

	private String badLocation;

	/**
	 * 不良描述
	 */
	private String badDescribe;
	
	
	private QualityInspection qualityInspection;

	/**
	 * 检验员
	 */
	private String inspector;

	public QualityInspectionDetail() {
	}

	public String getReceiptNumber() {
		return this.receiptNumber;
	}

	public String getOrderNumber() {
		return this.orderNumber;
	}
	@Column(name="creat_time",columnDefinition="TIMESTAMP",insertable = false,updatable = false)
	public String getCreatTime() {
		return this.creatTime;
	}

	public String getCreatorName() {
		return this.creatorName;
	}

	public String getUnit() {
		return this.unit;
	}

	public String getMaterialCode() {
		return this.materialCode;
	}

	public String getMaterialName() {
		return this.materialName;
	}

	public String getSpecification() {
		return this.specification;
	}

	public String getInspectionType() {
		return this.inspectionType;
	}

	public String getInspectionStandardName() {
		return this.inspectionStandardName;
	}

	public Integer getQualified() {
		return this.qualified;
	}

	public Integer getBadCount() {
		return this.badCount;
	}

	public String getBadLocation() {
		return this.badLocation;
	}

	public String getBadDescribe() {
		return this.badDescribe;
	}

	public String getInspector() {
		return this.inspector;
	}

	public void setReceiptNumber(String receiptNumber) {
		this.receiptNumber = receiptNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public void setCreatTime(String creatTime) {
		this.creatTime = creatTime;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public void setMaterialCode(String materialCode) {
		this.materialCode = materialCode;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}

	public void setSpecification(String specification) {
		this.specification = specification;
	}

	public void setInspectionType(String inspectionType) {
		this.inspectionType = inspectionType;
	}

	public void setInspectionStandardName(String inspectionStandardName) {
		this.inspectionStandardName = inspectionStandardName;
	}

	public void setQualified(Integer qualified) {
		this.qualified = qualified;
	}

	public void setBadCount(Integer badCount) {
		this.badCount = badCount;
	}

	public void setBadLocation(String badLocation) {
		this.badLocation = badLocation;
	}

	public void setBadDescribe(String badDescribe) {
		this.badDescribe = badDescribe;
	}

	public void setInspector(String inspector) {
		this.inspector = inspector;
	}

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "qualityInspection_id")
	public QualityInspection getQualityInspection() {
		return qualityInspection;
	}

	public void setQualityInspection(QualityInspection qualityInspection) {
		this.qualityInspection = qualityInspection;
	}

	public Integer getInspectionCount() {
		return inspectionCount;
	}

	public void setInspectionCount(Integer inspectionCount) {
		this.inspectionCount = inspectionCount;
	}
}
