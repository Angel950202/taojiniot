package com.taojin.iot.service.kanban;

/**
 * @program: server
 * @author: king
 * @description: 看板计算公式
 * FileName: KanBanFormula
 * Date:     2019-08-06 0006 09:32:43
 **/
public class KanBanFormula {

    /**
     * 生产进度
     * 生产进度计算公式（产线OK数/产线计划生产数）
     * @param okNum
     * @param planNum
     * @return
     */
    public Double productionProgress(Double okNum,Double planNum){
    	Double progress = 0.0;
    	 if (planNum != 0.0) {
    		 progress =  okNum / planNum;
    		 return progress * 100;
 		}
        return progress;
    }

    /**
     * 良品率
     * 良品率计算公式（产线OK数/产线实际生产总数）
     * @param okNum
     * @param actualNum
     * @return
     */
    public Double goodProducts(Double okNum,Double actualNum){
    	Double goodProducts = 0.0;
        if (actualNum != 0.0){
        	 goodProducts = okNum / actualNum;
            return goodProducts * 100;
        }
        return goodProducts;
    }

    /**
     * 生产线TRS(稼动率)
     * TRS计算公式（生产线实际生产总数*CT/（实际生产时间））
     * @param ct                ct值
     * @param trsActualNum      实际生产总数
     * @param actualTime        实际生产时间
     * @return
     */
    public Double trs(Double ct,Double trsActualNum,Double actualTime){
    	Double trs = 0.0;
        if (actualTime != 0.0){
            trs = (trsActualNum * ct) / (actualTime)*100;
            return trs;
        }
        return trs;
    }

    /**
     * 设备稼动率
     * 设备稼动率=设备运行时间/（目前时间-生产型号开始时间)
     * @param runTime
     * @param currentTime
     * @param modelStartTime
     * @return
     */
    public Double deviceTrs(Double runTime,Double currentTime,Double modelStartTime){
        if (runTime != null && currentTime != null && modelStartTime != null){
            Double deviceTrs = runTime / (currentTime - modelStartTime);
            return deviceTrs;
        }
        return 0.0;
    }
}
 