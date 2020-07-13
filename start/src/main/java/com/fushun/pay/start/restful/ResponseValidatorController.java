package com.fushun.pay.start.restful;

import com.alibaba.cola.dto.SingleResponse;
import com.alibaba.cola.extension.BizScenario;
import com.fushun.framework.util.response.ApiResult;
import com.fushun.framework.web.annotations.NoApiResult;
import com.fushun.pay.client.api.PayServiceI;
import com.fushun.pay.client.dto.cmd.syncresponse.PaySyncResponseWeiXinGZHCmd;
import com.fushun.pay.dto.clientobject.syncresponse.PaySyncResponseWeixinGZHValidatorDTO;
import com.fushun.pay.infrastructure.common.BizCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pay/valid")
public class ResponseValidatorController {

    @Autowired
    private PayServiceI payServiceI;

    /**
     * 公众号 支付同步返回校验
     * {"responseStr":"微信返回参数","orderPayNo":"订单支付单号"}
     * @param paySyncResponseWeixinGZHValidatorDTO
     */
    @RequestMapping("/sync/gzh")
    @NoApiResult
    public ApiResult<String> payGZHResponseValidator(PaySyncResponseWeixinGZHValidatorDTO paySyncResponseWeixinGZHValidatorDTO){
        PaySyncResponseWeiXinGZHCmd paySyncResponseWeiXinGZHCmd=new PaySyncResponseWeiXinGZHCmd();
        BizScenario bizScenario = BizScenario.valueOf(BizCode.payBizId, BizCode.payUseCase, BizCode.PAY_SCENARIO_WEIXIN_GZH);
        paySyncResponseWeiXinGZHCmd.setBizScenario(bizScenario);
        paySyncResponseWeiXinGZHCmd.setPaySyncResponseWeixinGZHValidatorDTO(paySyncResponseWeixinGZHValidatorDTO);
        SingleResponse<String> singleResponse= payServiceI.payResponseValidator(paySyncResponseWeiXinGZHCmd);
        if(!singleResponse.isSuccess()){
            return ApiResult.ofFail(singleResponse.getErrCode(),singleResponse.getErrMessage());
        }
        return ApiResult.of(singleResponse.getData());
    }

}
