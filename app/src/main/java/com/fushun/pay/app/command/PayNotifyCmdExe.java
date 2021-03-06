package com.fushun.pay.app.command;

import com.alibaba.cola.command.Command;
import com.alibaba.cola.command.CommandExecutorI;
import com.alibaba.cola.dto.SingleResponse;
import com.alibaba.cola.extension.ExtensionExecutor;
import com.alibaba.cola.logger.Logger;
import com.alibaba.cola.logger.LoggerFactory;
import com.fushun.framework.util.util.JsonUtil;
import com.fushun.pay.app.convertor.extensionpoint.PayNotifyConvertorExtPt;
import com.fushun.pay.app.dto.PayNotifyCmd;
import com.fushun.pay.app.dto.clientobject.PayNotifyCO;
import com.fushun.pay.app.dto.domainevent.AnalysisNotifyExceptionEvent;
import com.fushun.pay.app.thirdparty.extensionpoint.PayNotifyThirdPartyExtPt;
import com.fushun.pay.app.validator.extensionpoint.PayNotifyValidatorExtPt;
import com.fushun.pay.domain.pay.entity.PayE;
import com.fushun.pay.infrastructure.common.util.DomainEventPublisher;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author wangfushun
 * @version 1.0
 * @description
 * @creation 2019年01月21日23时08分
 */
@Command
public class PayNotifyCmdExe implements CommandExecutorI<SingleResponse, PayNotifyCmd> {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ExtensionExecutor extensionExecutor;

    @Autowired
    private DomainEventPublisher domainEventPublisher;

    @Override
    public SingleResponse<String> execute(PayNotifyCmd cmd) {
        PayNotifyCO payNotifyCO = null;
        boolean analysisNotify = true;
        try {
            //解析 通知信息
            payNotifyCO = extensionExecutor.execute(PayNotifyThirdPartyExtPt.class, cmd.getContext(), thirdparty -> thirdparty.created(cmd.getPayNotifyCO()));
        } catch (Exception e) {
            analysisNotify = false;
            logger.error("analysis notify fail, paramMap:[{}]", JsonUtil.toJson(cmd.getPayNotifyCO().getParamMap()), e);

            AnalysisNotifyExceptionEvent analysisNotifyExceptionEvent = new AnalysisNotifyExceptionEvent();
            analysisNotifyExceptionEvent.setOutTradeNo(payNotifyCO.getOutTradeNo());
            domainEventPublisher.publish(analysisNotifyExceptionEvent);
        }

        try {
            //1, validation
            extensionExecutor.executeVoid(PayNotifyValidatorExtPt.class, cmd.getContext(), validator -> validator.validate(cmd));

            //2, invoke domain service or directly operate domain to do business logic process
            PayE payE = extensionExecutor.execute(PayNotifyConvertorExtPt.class, cmd.getContext(), convertor -> convertor.clientToEntity(cmd.getPayNotifyCO(), cmd.getContext()));
            payE.payNotify();
        } catch (Exception e) {
            analysisNotify = false;
            logger.error("pay notify fail, paramMap:[{}]", JsonUtil.toJson(cmd.getPayNotifyCO()), e);
        }

        if (analysisNotify == false) {
            return SingleResponse.of(cmd.getPayNotifyCO().getNotifyReturnDTO().getFail());
        }
        //3, response
        return SingleResponse.of(payNotifyCO.getNotifyReturnDTO().getSuccess());

    }

}
