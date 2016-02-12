package com.slurp.helper.checks.passive.cleartextAuthent;

/**
 * Created by Marc Lebrun @ XMCO.
 */

import burp.IParameter;
import burp.IRequestInfo;
import burp.IResponseInfo;
import com.slurp.helper.AbstractSlurpHelperCheck;
import com.slurp.helper.AbstractSlurpHelperPassiveCheck;
import com.slurp.helper.SlurpHelperAnalyzedRequestResponse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SlurpHelperPassiveCheckCleartextAuthent extends AbstractSlurpHelperPassiveCheck {
    private List<String> listParams;
    private List<String> listHeaders;
    private ArrayList<String> infoList = null;

    SlurpHelperPassiveCheckCleartextAuthent(int id, SlurpHelperAnalyzedRequestResponse analyzed, AbstractSlurpHelperCheck parent) {
        super(id, analyzed, parent);
        this.listParams = Arrays.asList(
                "password",
                "pass",
                "passw",
                "motdepasse"
        );
        this.listHeaders = Arrays.asList(
                "Authorization"
        );
    }

    private void addInfo(String ip) {
        if (this.infoList == null) {
            this.infoList = new ArrayList<String>();
        }
        this.infoList.add(ip);
    }

    @Override
    public void doDataCheck(List<String> data) {
        doCheck();
    }

    private int checkParams(IRequestInfo reqInfo) {
        boolean found = false;
        List<IParameter> params = reqInfo.getParameters();

        for (IParameter param : params) {
            if (this.listParams.contains(param.getName().toLowerCase())) {
                this.addInfo(param.getName());
                found = true;
            }
        }
        if (found)
            return 1;
        else
            return 0;
    }

    private int checkCode(IResponseInfo resInfo) {
        if (resInfo.getStatusCode() == 401) {
            // todo: récupérer le realm ?
            return 1;
        }
        return 0;
    }

    private int checkHeaders (IRequestInfo reqInfo) {
        boolean first = true;
        List<String> headers = reqInfo.getHeaders();

        for (String header : headers) {
            if (first) {
                first = false;
                continue; // we skip the HTTP response code line
            }

            String[] parts = header.split(":");
            if (this.listHeaders.contains(parts[0])) {
                this.addInfo(header);
                return 1;
            }
        }
        return 0;
    }

    @Override
    public void doCheck() {
        int authentFound = 0;
        if (this.getOriginalRequestResponse().getHttpService().getProtocol().toLowerCase().equals("https"))
            return;

        IResponseInfo resInfo = this.getAnalyzedReqRes().getResInfo();
        IRequestInfo reqInfo = this.getAnalyzedReqRes().getReqInfo();

        authentFound += checkCode(resInfo);
        authentFound += checkHeaders(reqInfo);
        authentFound += checkParams(reqInfo);

        if (authentFound > 0) {
            this.markAsPositive();
        }
    }

    @Override
    public List<String> getData() {
        return infoList;
    }
}
