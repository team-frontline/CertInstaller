package org.frontline.certInstaller;

import org.frontline.certInstaller.certOperation.KeyStoreManager;
import org.frontline.certInstaller.mschainOperation.CertManager;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class CertManagerTest {

    @Test
    public void testCertManage(){
        Map<String, String> csrParamMap = new HashMap<String, String>();
        csrParamMap.put("commonName", "ms223.org");
        csrParamMap.put("organizationUnit", "MSChain");
        csrParamMap.put("organizationName", "Frontline.org");
        csrParamMap.put("localityName", "Colombo");
        csrParamMap.put("stateName", "West");
        csrParamMap.put("country", "SL");
        CertManager certManager = new CertManager();
        try {
//            certManager.initiateCertificate("src/main/resources","ms223",csrParamMap);
//            KeyStoreManager.createKeyStore("src/main/resources",certManager.getKeyPair(),"store123"
//                    ,"pass123",certManager.getCertificate(),"piuyghu.jks");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
