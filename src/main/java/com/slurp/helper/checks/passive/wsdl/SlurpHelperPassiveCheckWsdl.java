package com.slurp.helper.checks.passive.wsdl;

/**
 * Created by Marc Lebrun @ XMCO.
 */

import com.slurp.SlurpUtils;
import com.slurp.helper.AbstractSlurpHelperCheck;
import com.slurp.helper.AbstractSlurpHelperPassiveCheck;
import com.slurp.helper.SlurpHelperAnalyzedRequestResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SlurpHelperPassiveCheckWsdl extends AbstractSlurpHelperPassiveCheck {
    private String data = null;

    SlurpHelperPassiveCheckWsdl(int id, SlurpHelperAnalyzedRequestResponse analyzed, AbstractSlurpHelperCheck parent) {
        super(id, analyzed, parent);
    }

    @Override
    public void doDataCheck(List<String> data) {
        doCheck();
    }

    @Override
    public void doCheck() {
        SlurpUtils utils = SlurpUtils.getInstance();

        int code = utils.getCodeFromResponse(this.getOriginalRequestResponse().getResponse());

        if (code == 200) {
            String url = this.getAnalyzedReqRes().getUri().toString();
            Pattern pattern;
            Matcher m;

            pattern = Pattern.compile(".*(\\?wsdl)", Pattern.CASE_INSENSITIVE);
            m = pattern.matcher(url);
            if (m.find()) {
                data = SlurpUtils.getResponseBody(this.getOriginalRequestResponse());

                if (getAnalyzedReqRes().getResAsString().toLowerCase().contains("wsdl")) {
                    this.markAsPositive();
                } else
                    this.markAsUncertain();
            } else {
                this.markAsNegative();
            }
        } else {
            this.markAsNegative();
        }
    }

    @Override
    public List<String> getData() {
        if (data != null) {
            ArrayList<String> dataList = new ArrayList<String>();
            dataList.add(data);

            return dataList;
        } else
            return null;
    }
}
