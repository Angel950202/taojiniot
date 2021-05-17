package com.taojin.iot.service.charts.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.taojin.iot.base.comm.entity.BaseEntity;

/**
 * Entity-图表研
 * Echarts图表种类整合
 * iot物联网平台
 * ============================================================================
 * 版权所有 2013-2016 无锡淘金网络科技有限公司，并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得UcSkype商业授权之前，您不能将本软件应用于商业用途，否则UcSkype将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.ucskype.com/
 * ----------------------------------------------------------------------------
 * 上午10:16:00
 * author 王杰
 * ============================================================================
 */
@Entity
@Table(name = "iot_charts_type")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "iot_charts_type_sequence")
public class ChartsType extends BaseEntity{

	private static final long serialVersionUID = 6576718120985571674L;
	//echarts图表种类
	public enum EchartType{
		line,//拆线图
		gauge,//仪表盘
		pie,//饼图
		heatmap,//热力图
		parallel,//平等坐标
		funnel//漏斗
	}
	
	/**名称*/
	private String name;
	/**种类*/
	private EchartType echartType; 
	/**类别*/
	private String echartTypeParams;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Column(nullable = false)
	public EchartType getEchartType() {
		return echartType;
	}
	public void setEchartType(EchartType echartType) {
		this.echartType = echartType;
	}
	public String getEchartTypeParams() {
		return echartTypeParams;
	}
	public void setEchartTypeParams(String echartTypeParams) {
		this.echartTypeParams = echartTypeParams;
	}
	
}
