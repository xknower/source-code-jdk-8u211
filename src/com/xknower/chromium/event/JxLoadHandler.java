package com.xknower.chromium.event;

import com.teamdev.jxbrowser.chromium.CertificateErrorParams;
import com.teamdev.jxbrowser.chromium.DefaultLoadHandler;
import com.teamdev.jxbrowser.chromium.LoadHandler;
import com.teamdev.jxbrowser.chromium.LoadParams;

/**
 *  @author xknower
 */
public class JxLoadHandler extends DefaultLoadHandler implements LoadHandler {

    @Override
    public boolean onLoad(LoadParams params) {
        return false;
    }

    @Override
    public boolean canNavigateOnBackspace() {
        return true;
    }

    @Override
    public boolean onCertificateError(CertificateErrorParams params) {
        return false;
    }
}
