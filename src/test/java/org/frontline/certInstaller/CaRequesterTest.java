package org.frontline.certInstaller;

import org.frontline.certInstaller.mschainOperation.CaRequester;
import org.frontline.certInstaller.mschainOperation.CertManager;
import org.frontline.certInstaller.utils.InvalidCSRParamException;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class CaRequesterTest {



    @BeforeClass
    public static void setUp(){
        CaRequester caRequester = new CaRequester();
    }

    @Test
    public void getNewCertificateTest() throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidCSRParamException, IOException, ExecutionException, InterruptedException {
//        CertManager certManager = new CertManager();
//        Map<String, String> csrParamMap = new HashMap<String, String>();
//        csrParamMap.put("commonName", "frontline.catalogue");
//        csrParamMap.put("organizationUnit", "MSChain");
//        csrParamMap.put("organizationName", "Frontline.org");
//        csrParamMap.put("localityName", "Colombo");
//        csrParamMap.put("stateName", "West");
//        csrParamMap.put("country", "SL");
//        certManager.initiateCertificate("src/main/resources","test.com",csrParamMap);
    }
}
