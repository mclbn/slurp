package com.slurp.helper.checks.active.dirFuzz.backups;

/**
 * Created by Marc Lebrun @ XMCO.
 */

import burp.IHttpRequestResponse;
import com.slurp.SlurpUtils;
import com.slurp.helper.AbstractSlurpHelperActiveCheck;
import com.slurp.helper.AbstractSlurpHelperCheck;
import com.slurp.helper.SlurpHelperAnalyzedRequestResponse;
import com.slurp.helper.checks.active.dirFuzz.SlurpHelperCheckDirFuzz;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SlurpHelperCheckDirFuzzBackups extends AbstractSlurpHelperActiveCheck {
    private List<String> suffixList;
    private List<String> prefixList;
    private List<String> archiveExtList;
    private ArrayList<String> foundList = null;
    private int issuedRequests = 0;
    private boolean lightMode = true;

    public SlurpHelperCheckDirFuzzBackups(int id, SlurpHelperAnalyzedRequestResponse analyzed, AbstractSlurpHelperCheck parent) {
        super(id, analyzed, parent);
        this.suffixList = Arrays.asList(
                ".backup",
                ".bck",
                ".old",
                ".save",
                ".bak",
                ".sav",
                "~",
                ".copy",
                ".old",
                ".orig",
                ".tmp",
                ".txt",
                ".back"
        );
        this.prefixList = Arrays.asList(
                "Copy_(1)_of_",
                "Copy_(2)_of_",
                "Copy%20of%20",
                "Copy_of_",
                "Copy_",
                "Copy%20",
                "_",
                "%20"
        );
        this.archiveExtList = Arrays.asList(
                "0",
                "000",
                "7z",
                "a00",
                "a01",
                "a02",
                "ace",
                "ain",
                "alz",
                "apz",
                "ar",
                "arc",
                "arh",
                "ari",
                "arj",
                "ark",
                "axx",
                "b64",
                "ba",
                "bh",
                "boo",
                "bz",
                "bz2",
                "bzip",
                "bzip2",
                "c00",
                "c01",
                "c02",
                "car",
                "cb7",
                "cbr",
                "cbt",
                "cbz",
                "cp9",
                "cpgz",
                "cpt",
                "dar",
                "dd",
                "deb",
                "dgc",
                "dist",
                "ecs",
                "efw",
                "epi",
                "f",
                "fdp",
                "gca",
                "gz",
                "gzi",
                "gzip",
                "ha",
                "hbc",
                "hbc2",
                "hbe",
                "hki",
                "hki1",
                "hki2",
                "hki3",
                "hpk",
                "hyp",
                "ice",
                "ipg",
                "ipk",
                "ish",
                "j",
                "jar.pack",
                "jgz",
                "jic",
                "kgb",
                "lbr",
                "lemon",
                "lha",
                "lnx",
                "lqr",
                "lz",
                "lzh",
                "lzm",
                "lzma",
                "lzo",
                "lzx",
                "md",
                "mint",
                "mou",
                "mpkg",
                "mzp",
                "oar",
                "p7m",
                "packgz",
                "package",
                "pae",
                "pak",
                "paq6",
                "paq7",
                "paq8",
                "par",
                "par2",
                "pbi",
                "pcv",
                "pea",
                "pet",
                "pf",
                "pim",
                "pit",
                "piz",
                "pkg",
                "pup",
                "puz",
                "pwa",
                "qda",
                "r0",
                "r00",
                "r01",
                "r02",
                "r03",
                "r1",
                "r2",
                "r30",
                "rar",
                "rev",
                "rk",
                "rnc",
                "rp9",
                "rpm",
                "rte",
                "rz",
                "rzs",
                "s00",
                "s01",
                "s02",
                "s7z",
                "sar",
                "sdc",
                "sdn",
                "sea",
                "sen",
                "sfs",
                "sfx",
                "sh",
                "shar",
                "shk",
                "shr",
                "sit",
                "sitx",
                "spt",
                "sqx",
                "sqz",
                "tar",
                "targz",
                "tarxz",
                "taz",
                "tbz",
                "tbz2",
                "tg",
                "tgz",
                "tlz",
                "tlzma",
                "txz",
                "tz",
                "uc2",
                "uha",
                "vem",
                "vsi",
                "wad",
                "war",
                "wot",
                "xef",
                "xez",
                "xmcdz",
                "xpi",
                "xx",
                "xz",
                "y",
                "yz",
                "z",
                "z01",
                "z02",
                "z03",
                "z04",
                "zap",
                "zfsendtotarget",
                "zip",
                "zipx",
                "zix",
                "zoo",
                "zpi",
                "zz"
        );
    }

    private void addFound(String uri) {
        if (this.foundList == null) {
            this.foundList = new ArrayList<String>();
        }
        this.foundList.add(uri);
    }

    private void doFuzzReq(String urn) {
        SlurpUtils utils = SlurpUtils.getInstance();
        IHttpRequestResponse req = SlurpHelperCheckDirFuzz.doDirFuzzReq(this, urn);
        if (req == null)
            return;
        issuedRequests++;
        int code = utils.getCodeFromResponse(req.getResponse());

        if (code == 200) {
            addFound(utils.getUriFromRequest(req));
            this.markAsPositive();
        }
    }

    private String insertPrefix(String urn, String prefix) {
        int endIndex = urn.lastIndexOf("/");
        String urnNoEndingSlash = urn.substring(0, endIndex);
        int startIndex = urnNoEndingSlash.lastIndexOf("/");
        String baseUrn = urn.substring(0, startIndex);
        String endUrn = urn.substring(startIndex + 1, endIndex);
        String newUrn = baseUrn + "/" + prefix + endUrn + "/";
        return newUrn;
    }

    private void doFuzzPrefix(String urn) {
        if (urn.length() == 1)
            return; // we skip /

        for (String prefix : prefixList) {
            String newUrn = insertPrefix(urn, prefix);
            doFuzzReq(newUrn); // we keep the trailing slash
        }
    }

    private void doFuzzSuffix(String urn) {
        if (urn.length() == 1)
            return; // we skip /

        String newUrn;
        if (urn.endsWith("/")) {
            newUrn = urn.substring(0, urn.length() - 1);
        } else
            newUrn = urn;

        for (String suffix : suffixList) {
            doFuzzReq(newUrn + suffix); // we dont want the trailing slash here
        }
    }

    private void doFuzzArchive(String urn) {
        if (urn.length() == 1)
            return; // we skip /

        String newUrn;
        if (urn.endsWith("/")) {
            newUrn = urn.substring(0, urn.length() - 1);
        } else
            newUrn = urn;

        for (String suffix : archiveExtList) {
            doFuzzReq(newUrn + "." + suffix); // we dont want the trailing slash here
        }
    }

    public void lightMode() {
        this.lightMode = true;
    }

    public void fullMode() {
        this.lightMode = false;
    }

    @Override
    public void doDataCheck(List<String> data) {
        for (String urn : data) {
            doFuzzPrefix(urn);
            doFuzzSuffix(urn);
            if (this.lightMode == false)
                doFuzzArchive(urn);
        }

        if (issuedRequests == 0)
            this.markForDeletetion();

        if (getStatus().equals("PENDING"))
            this.markAsNegative();
    }

    @Override
    public void doCheck() {

    }

    @Override
    public List<String> getData() {
        return foundList;
    }
}
