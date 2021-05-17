package com.taojin.iot.service.quality.entiy;

import com.taojin.iot.base.comm.entity.BaseEntity;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

/**
 * @program: ucskype-iot
 * @description: 检验标准详情
 * @author: LiHeYang
 * @create: 2019-07-13 16:22
 **/
@Entity
@Table(name = "iot_quality_standard_Detail")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "iot_quality_standard_Detail_sequence")
public class QualityStandardDetail extends BaseEntity {

	/**
	 * 检验项目
	 */
	private String inspectionProject ;

	/**
	 * 检验指标
	 */
	private String indicators;
	
	
	private QualityStandard qualityStandard;

    public QualityStandardDetail() {
    }


    public String getInspectionProject() {
        return this.inspectionProject;
    }

    public String getIndicators() {
        return this.indicators;
    }

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "qualityStandard_id")
    public QualityStandard getQualityStandard() {
        return this.qualityStandard;
    }

    public void setInspectionProject(String inspectionProject) {
        this.inspectionProject = inspectionProject;
    }

    public void setIndicators(String indicators) {
        this.indicators = indicators;
    }

    public void setQualityStandard(QualityStandard qualityStandard) {
        this.qualityStandard = qualityStandard;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof QualityStandardDetail)) return false;
        final QualityStandardDetail other = (QualityStandardDetail) o;
        if (!other.canEqual((Object) this)) return false;
        
        final Object this$inspectionProject = this.getInspectionProject();
        final Object other$inspectionProject = other.getInspectionProject();
        if (this$inspectionProject == null ? other$inspectionProject != null : !this$inspectionProject.equals(other$inspectionProject)) return false;
       
        final Object this$indicators = this.getIndicators();
        final Object other$indicators = other.getIndicators();
        if (this$indicators == null ? other$indicators != null : !this$indicators.equals(other$indicators))
            return false;
        final Object this$qualityStandard = this.getQualityStandard();
        final Object other$qualityStandard = other.getQualityStandard();
        if (this$qualityStandard == null ? other$qualityStandard != null : !this$qualityStandard.equals(other$qualityStandard))
            return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof QualityStandardDetail;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $inspectionProject = this.getInspectionProject();
        result = result * PRIME + ($inspectionProject == null ? 43 : $inspectionProject.hashCode());
        final Object $indicators = this.getIndicators();
        result = result * PRIME + ($indicators == null ? 43 : $indicators.hashCode());
        final Object $qualityStandard = this.getQualityStandard();
        result = result * PRIME + ($qualityStandard == null ? 43 : $qualityStandard.hashCode());
        return result;
    }

    public String toString() {
        return "QualityStandardDetail(inspectionProject=" + this.getInspectionProject() + ", indicators=" + this.getIndicators() + ", qualityStandard=" + this.getQualityStandard() + ")";
    }
}
