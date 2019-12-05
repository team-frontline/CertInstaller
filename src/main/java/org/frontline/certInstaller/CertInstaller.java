package org.frontline.certInstaller;


import org.frontline.certInstaller.certOperation.KeyStoreManager;
import org.frontline.certInstaller.mschainOperation.CertManager;
import org.frontline.certInstaller.utils.CertGenerationException;
import org.frontline.certInstaller.utils.InvalidCSRParamException;

import java.io.IOException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class CertInstaller {
    public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidCSRParamException, IOException, ExecutionException, InterruptedException, CertificateException, KeyStoreException, CertGenerationException {
        Map<String, String> csrParamMap = new HashMap<String, String>();
        csrParamMap.put("commonName", "piumi.org");
        csrParamMap.put("organizationUnit", "MSChain");
        csrParamMap.put("organizationName", "Frontline.org");
        csrParamMap.put("localityName", "Colombo");
        csrParamMap.put("stateName", "West");
        csrParamMap.put("country", "SL");
        CertManager certManager = new CertManager();
        try {
            certManager.initiateCertificate("src/main/resources", "test_piumi", csrParamMap);
            KeyStoreManager.installCert("src/main/resources", certManager.getKeyPair(), "piumi.org"
                    , "pass123", certManager.getCertificate(),"mystore.jks");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}