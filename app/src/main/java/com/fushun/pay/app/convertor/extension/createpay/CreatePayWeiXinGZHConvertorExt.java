package com.fushun.pay.app.convertor.extension.createpay;

import com.alibaba.cola.context.Context;
import com.alibaba.cola.extension.Extension;
import com.fushun.pay.app.convertor.CreatePayConvertor;
import com.fushun.pay.app.convertor.extensionpoint.CreatePayConvertorExtPt;
import com.fushun.pay.app.dto.clientobject.createpay.CreatePayWeiXinGZHCO;
import com.fushun.pay.domain.pay.entity.PayE;
import com.fushun.pay.infrastructure.common.BizCode;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author wangfushun
 * @version 1.0
 * @creation 2019年01月18日23时38分
 */
@Extension(bizCode = BizCode.CREATEPAY_WEIXIN_GZH)
public class CreatePayWeiXinGZHConvertorExt implements CreatePayConvertorExtPt<CreatePayWeiXinGZHCO> {

    @Autowired
    private CreatePayConvertor payConvertor;

    @Override
    public PayE clientToEntity(CreatePayWeiXinGZHCO payCO, Context context) {
        PayE payE = payConvertor.clientToEntity(payCO, context);
        return payE;
    }
}
