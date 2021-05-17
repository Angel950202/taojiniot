package com.taojin.iot.service.quality.entiy;

import com.taojin.iot.base.comm.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import java.util.Collection;

/**
 * @program: ucskype-iot
 * @description: 质量标准
 * @author: LiHeYang
 * @create: 2019-07-13 14:05
 **/
@Entity
@Table(name = "iot_quality_standard")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "iot_quality_standard_sequence")
public class QualityStandard extends BaseEntity {
    /**
     * 检验标准代码
     */
    private String inspectionStandardCode;

    /**
     * 检验标准
     */
    private String inspectionStandardName;

    /**
     * 检验方式
     */
    private String inspectionType;
    

    private String creatTime;
    
    private String creatorName;

    public QualityStandard() {
    }

    public String getInspectionStandardCode() {
        return this.inspectionStandardCode;
    }

    public String getInspectionStandardName() {
        return this.inspectionStandardName;
    }

    public String getInspectionType() {
        return this.inspectionType;
    }

    public String getCreatorName() {
        return this.creatorName;
    }

    public void setInspectionStandardCode(String inspectionStandardCode) {
        this.inspectionStandardCode = inspectionStandardCode;
    }

    public void setInspectionStandardName(String inspectionStandardName) {
        this.inspectionStandardName = inspectionStandardName;
    }

    public void setInspectionType(String inspectionType) {
        this.inspectionType = inspectionType;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof QualityStandard)) return false;
        final QualityStandard other = (QualityStandard) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$inspectionStandardCode = this.getInspectionStandardCode();
        final Object other$inspectionStandardCode = other.getInspectionStandardCode();
        if (this$inspectionStandardCode == null ? other$inspectionStandardCode != null : !this$inspectionStandardCode.equals(other$inspectionStandardCode))
            return false;
        final Object this$inspectionStandardName = this.getInspectionStandardName();
        final Object other$inspectionStandardName = other.getInspectionStandardName();
        if (this$inspectionStandardName == null ? other$inspectionStandardName != null : !this$inspectionStandardName.equals(other$inspectionStandardName))
            return false;
        final Object this$inspectionType = this.getInspectionType();
        final Object other$inspectionType = other.getInspectionType();
        if (this$inspectionType == null ? other$inspectionType != null : !this$inspectionType.equals(other$inspectionType))
            return false;
        final Object this$creatorName = this.getCreatorName();
        final Object other$creatorName = other.getCreatorName();
        if (this$creatorName == null ? other$creatorName != null : !this$creatorName.equals(other$creatorName))
            return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof QualityStandard;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $inspectionStandardCode = this.getInspectionStandardCode();
        result = result * PRIME + ($inspectionStandardCode == null ? 43 : $inspectionStandardCode.hashCode());
        final Object $inspectionStandardName = this.getInspectionStandardName();
        result = result * PRIME + ($inspectionStandardName == null ? 43 : $inspectionStandardName.hashCode());
        final Object $inspectionMethodName = this.getInspectionType();
        result = result * PRIME + ($inspectionMethodName == null ? 43 : $inspectionMethodName.hashCode());
        final Object $creatorName = this.getCreatorName();
        result = result * PRIME + ($creatorName == null ? 43 : $creatorName.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "QualityStandard(inspectionStandardCode=" + this.getInspectionStandardCode() + ", inspectionStandardName=" + this.getInspectionStandardName() + ", inspectionType=" + this.getInspectionType() + ", creatorName=" + this.getCreatorName() + ")";
    }
    
   private Collection<QualityStandardDetail> qualityStandardDetail;
    
    @OneToMany(mappedBy = "qualityStandard")
    public Collection<QualityStandardDetail> getQualityStandardDetail() {
        return qualityStandardDetail;
    }

    public void setQualityStandardDetail(Collection<QualityStandardDetail> qualityStandardDetail) {
        this.qualityStandardDetail = qualityStandardDetail;
    }
    
    @Column(name="creat_time",columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP",insertable = false,updatable = false)
    @Generated(GenerationTime.INSERT)
	public String getCreatTime() {
		return creatTime;
	}

	public void setCreatTime(String creatTime) {
		this.creatTime = creatTime;
	}
}
