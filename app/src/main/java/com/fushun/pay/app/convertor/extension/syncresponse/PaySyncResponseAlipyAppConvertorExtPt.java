package com.fushun.pay.app.convertor.extension.syncresponse;

import com.alibaba.cola.context.Context;
import com.alibaba.cola.extension.Extension;
import com.fushun.pay.app.convertor.PaySyncResponseConvertor;
import com.fushun.pay.app.convertor.extensionpoint.PaySyncResponseConvertorExtPt;
import com.fushun.pay.app.dto.clientobject.syncresponse.PaySyncResponseAlipayAppCO;
import com.fushun.pay.domain.pay.entity.PayE;
import com.fushun.pay.infrastructure.common.BizCode;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author wangfushun
 * @version 1.0
 * @description
 * @creation 2019年01月24日01时04分
 */
@Extension(bizCode = BizCode.CREATEPAY_ALIPAY_APP)
public class PaySyncResponseAlipyAppConvertorExtPt implements PaySyncResponseConvertorExtPt<PaySyncResponseAlipayAppCO> {

    @Autowired
    private PaySyncResponseConvertor payConvertor;

    @Override
    public PayE clientToEntity(PaySyncResponseAlipayAppCO paySyncResponseAlipayAppCO, Context context) {

        PayE payE = payConvertor.clientToEntity(paySyncResponseAlipayAppCO, context);
        payE.setPayMoney(paySyncResponseAlipayAppCO.getPayMoney());
        payE.setPayNo(paySyncResponseAlipayAppCO.getPayNo());
        payE.setOutTradeNo(paySyncResponseAlipayAppCO.getOutTradeNo());
        payE.setReceiveWay(paySyncResponseAlipayAppCO.getReceiveWay());
        payE.setPayWay(paySyncResponseAlipayAppCO.getEPayWay());
        payE.setStatus(paySyncResponseAlipayAppCO.getStatus());
        return payE;
    }
}
