package org.frontline.certInstaller;

import org.frontline.certInstaller.certOperation.KeyStoreManager;
import org.frontline.certInstaller.mschainOperation.CertManager;
import org.frontline.certInstaller.utils.CertGenerationException;
import org.frontline.certInstaller.utils.InvalidCSRParamException;

import java.io.File;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.cert.CertificateException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class UpdateKeyStore {
    public static void main(String[] args) {
        /*from args
        oldKeyFile,newKeyFile,keystoreName,aliasName,password
        * */

//
//        String oldKeyfile = "ms-catalogue";
//        String newKeyFile = "ms-catalogue-new2";
//        String keystoreName = "ms-catalogue.jks";
//        String aliasName = "ms-catalogue.lk";
//        String password = "pass123";
//
//        Map<String, String> csrParamMap = new HashMap<String, String>();
//        csrParamMap.put("commonName", "ms-catalogue.lk");
//        csrParamMap.put("organizationUnit", "MSChain");
//        csrParamMap.put("organizationName", "Frontline.org");
//        csrParamMap.put("localityName", "Colombo");
//        csrParamMap.put("stateName", "West");
//        csrParamMap.put("country", "SL");
//        try {
//            installNewCert("src/main/resources", oldKeyfile, newKeyFile, csrParamMap,
//                    keystoreName, aliasName, password);
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }

    }


    public static void installNewCert(String resourceFilePath, String oldKeyFile, String newKeyFile,
                                      Map<String, String> csrParams, String keyStoreName, String aliasName,
                                      String password) throws IOException, CertificateException, NoSuchAlgorithmException,
            ExecutionException, CertGenerationException, InterruptedException, InvalidCSRParamException, SignatureException, InvalidKeySpecException, InvalidKeyException, KeyStoreException {
        /*updateRevokedCert(String resourceFilePath, String oldKeyFile, String newKeyFile,
                                  Map<String, String> csrParams)*/

        CertManager certManager = new CertManager();
        certManager.updateRevokedCert(resourceFilePath, oldKeyFile, newKeyFile, csrParams);

        /*String keystorePath, KeyPair keyPair, String aliasName, String password,
                                      Certificate certificate*/
        String KeyStorePath = resourceFilePath + File.separator + keyStoreName;
        KeyStoreManager.updateKeyStore(KeyStorePath, certManager.getKeyPair(), aliasName, password, certManager.getCertificate());
    }
}
