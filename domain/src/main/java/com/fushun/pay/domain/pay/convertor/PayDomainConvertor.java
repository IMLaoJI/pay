package com.fushun.pay.domain.pay.convertor;

import com.alibaba.cola.convertor.ConvertorI;
import com.alibaba.cola.logger.Logger;
import com.alibaba.cola.logger.LoggerFactory;
import com.fushun.pay.app.dto.enumeration.ERecordPayNotityStatus;
import com.fushun.pay.app.dto.enumeration.ERecordPayStatus;
import com.fushun.pay.domain.pay.entity.PayE;
import com.fushun.pay.infrastructure.pay.tunnel.database.dataobject.RecordPayDO;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * @author wangfushun
 * @version 1.0
 * @description
 * @creation 2019年01月20日18时29分
 */
@Component
public class PayDomainConvertor implements ConvertorI {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public RecordPayDO entityToData(PayE payE) {
        RecordPayDO recordPayDO = new RecordPayDO();
        entityToData(payE, recordPayDO);
        return recordPayDO;
    }

    public RecordPayDO entityToData(PayE payE, RecordPayDO recordPayDO) {
        String outTradeNo = payE.getPayFrom().getPreStr() + payE.getTradeNo();
        recordPayDO.setOutTradeNo(outTradeNo);
        recordPayDO.setOrderPayNo(payE.getTradeNo());
        recordPayDO.setPayMoney(payE.getPayMoney());
        recordPayDO.setPayWay(payE.getPayWay().getCode());
        recordPayDO.setRefundAmount(BigDecimal.ZERO);
        recordPayDO.setPayFrom(payE.getPayFrom().getCode());
        recordPayDO.setStatus(ERecordPayStatus.created.getCode());
        recordPayDO.setNotityStatus(ERecordPayNotityStatus.no.getCode());
        return recordPayDO;
    }
}
