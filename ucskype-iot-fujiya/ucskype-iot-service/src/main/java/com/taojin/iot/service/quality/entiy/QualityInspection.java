package com.taojin.iot.service.quality.entiy;

import java.util.Collection;

import com.taojin.iot.base.comm.entity.BaseEntity;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

/**
 * 质量检验单
 */
@Entity
@Table(name = "iot_quality_inspection")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "iot_quality_inspection_sequence")
public class QualityInspection extends BaseEntity {
	
	/**
	 * 质量检验单号
	 */
	private String receiptNumber;

	/**
	 * 创建时间
	 */
	private String creatTime;
	/**
	 * 创建人
	 */
	private String creatorName;


	
	public QualityInspection() {
	}

	public String getReceiptNumber() {
		return this.receiptNumber;
	}

	@Column(columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP",insertable = false,updatable = false)
	@Generated(GenerationTime.INSERT)
	  public String getCreatTime() {
		return this.creatTime;
	}

	public String getCreatorName() {
		return this.creatorName;
	}

	
	public void setReceiptNumber(String receiptNumber) {
		this.receiptNumber = receiptNumber;
	}

	
	public void setCreatTime(String creatTime) {
		this.creatTime = creatTime;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	
	public boolean equals(final Object o) {
		if (o == this) return true;
		if (!(o instanceof QualityInspection)) return false;
		final QualityInspection other = (QualityInspection) o;
		if (!other.canEqual((Object) this)) return false;
		final Object this$receiptNumber = this.getReceiptNumber();
		final Object other$receiptNumber = other.getReceiptNumber();
		if (this$receiptNumber == null ? other$receiptNumber != null : !this$receiptNumber.equals(other$receiptNumber))
			return false;
		final Object this$creatTime = this.getCreatTime();
		final Object other$creatTime = other.getCreatTime();
		if (this$creatTime == null ? other$creatTime != null : !this$creatTime.equals(other$creatTime)) return false;
		final Object this$creatorName = this.getCreatorName();
		final Object other$creatorName = other.getCreatorName();
		if (this$creatorName == null ? other$creatorName != null : !this$creatorName.equals(other$creatorName))
			return false;
		return true;
	}

	protected boolean canEqual(final Object other) {
		return other instanceof QualityInspection;
	}

	public int hashCode() {
		final int PRIME = 59;
		int result = 1;
		final Object $receiptNumber = this.getReceiptNumber();
		result = result * PRIME + ($receiptNumber == null ? 43 : $receiptNumber.hashCode());
		final Object $creatTime = this.getCreatTime();
		result = result * PRIME + ($creatTime == null ? 43 : $creatTime.hashCode());
		final Object $creatorName = this.getCreatorName();
		result = result * PRIME + ($creatorName == null ? 43 : $creatorName.hashCode());
		return result;
	}
	
	 private Collection<QualityInspectionDetail> qualityInspectionDetail;
	
    @OneToMany(mappedBy = "qualityInspection")
    public Collection<QualityInspectionDetail> getQualityInspectionDetail() {
        return qualityInspectionDetail;
    }

    public void setQualityInspectionDetail(Collection<QualityInspectionDetail> qualityInspectionDetail) {
        this.qualityInspectionDetail = qualityInspectionDetail;
    }
	
}
