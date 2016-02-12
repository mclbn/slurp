package com.slurp.helper.checks.passive.reflectedParams;

/**
 * Created by Marc Lebrun @ XMCO.
 */

import burp.IParameter;
import burp.IRequestInfo;
import com.slurp.helper.*;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

public class SlurpHelperPassiveCheckReflectedParams extends AbstractSlurpHelperPassiveCheck {
    private ArrayList<String> reflectedParamList = null;

    public SlurpHelperPassiveCheckReflectedParams(int id, SlurpHelperAnalyzedRequestResponse analyzed, AbstractSlurpHelperCheck parent) {
        super(id, analyzed, parent);
    }

    @Override
    public void doDataCheck(List<String> data) {

    }

    private void addReflectedParam(String ip) {
        if (this.reflectedParamList == null) {
            this.reflectedParamList = new ArrayList<String>();
        }
        this.reflectedParamList.add(ip);
    }

    @Override
    public void doCheck() {
        int reflectedParams = 0;
        IRequestInfo reqInfo = this.getAnalyzedReqRes().getReqInfo();
        String resString = this.getAnalyzedReqRes().getResAsString();
        List<IParameter> paramList = reqInfo.getParameters();

        for (IParameter param : paramList) {
            String decodedParam = URLDecoder.decode(param.getValue());
            if (decodedParam.length() > 0
                    && resString.contains(decodedParam)) {
                this.addReflectedParam(decodedParam);
                reflectedParams++;
                // xss checks (passive and active)
            }
        }

        if (reflectedParams > 0) {
            ArrayList<String> data = new ArrayList<String>();
            for (String entry : this.reflectedParamList) {
                data.add(entry);
            }

            //doing subchecks here
            SlurpHelperCheckFactory factory = SlurpHelperCheckFactory.getInstance();
            SlurpHelper helper = SlurpHelper.getInstance();

            AbstractSlurpHelperPassiveCheck checkParam = (AbstractSlurpHelperPassiveCheck) factory.newCheck("PASSIVE_REFLECTEDXSS", this.getAnalyzedReqRes(), this, null, this);

            if (checkParam != null) {
                checkParam.doDataCheck(data);
                checkParam.doSanityCheck();
            }

            this.markAsPositive();
        }
    }

    @Override
    public List<String> getData() {
        return this.reflectedParamList;
    }
}
