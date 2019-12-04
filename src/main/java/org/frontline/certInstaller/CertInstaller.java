package org.frontline.certInstaller;

import org.frontline.certInstaller.certOperation.CertHandler;
import org.frontline.certInstaller.utils.InvalidCSRParamException;
import org.frontline.certInstaller.utils.KeyOperator;
import org.frontline.certInstaller.utils.FileOperator;

import java.io.*;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.util.Map;
import java.util.logging.Logger;

public class CertInstaller {
    final static Logger logger = Logger.getLogger(CertInstaller.class.toString());

    public static void main(String[] args) throws IOException, InvalidCSRParamException, InvalidKeySpecException, NoSuchAlgorithmException {
//        Map<String, String> csrParamMap = new HashMap<String, String>();
//        csrParamMap.put("commonName", "frontline.catalogue");
//        csrParamMap.put("organizationUnit", "MSChain");
//        csrParamMap.put("organizationName", "Frontline.org");
//        csrParamMap.put("localityName", "Colombo");
//        csrParamMap.put("stateName", "West");
//        csrParamMap.put("country", "SL");
//        InitiateCertificate("src/main/resources","test123",csrParamMap);
//        KeyPair keyPair = CertHandler.generateKeyPair("RSA", 2048);
//        byte[] csr = CertHandler.generateCSR("SHA256WithRSA", keyPair, "frontline.catalogue",
//                "MSchain", "Frontline.org", "Colombo", "West", "SL");
//
//        String privateKeyString = null;
//        if (keyPair != null) {
//            privateKeyString = KeyOperator.getKeyString(keyPair);
//        }
//        String csrString = new String(csr);
//        System.out.println(privateKeyString);
//        System.out.println(csrString);
//        FileOperator.writeFile(privateKeyString, "catalogue.key");
//        FileOperator.writeFile(csrString, "catalogue.csr");
//
//        try {
//            KeyPair keyPair1 = KeyOperator.getKeyPairFromKeyFile("catalogue.key");
//
//            String certString = "-----BEGIN CERTIFICATE-----\n" +
//                    "MIIDPTCCAiUCBgFuu3ULlzANBgkqhkiG9w0BAQsFADAmMRYwFAYDVQQDDA1hZG1p\n" +
//                    "bi5jYTEuY29tMQwwCgYDVQQKDANDQTEwHhcNMTkxMTI5MDgzNzIwWhcNMjExMTI5\n" +
//                    "MDgzNzIwWjCBnTELMAkGA1UEBhMCSU4xEjAQBgNVBAgMCU5ldyBEZWxoaTESMBAG\n" +
//                    "A1UEBwwJTmV3IERlbGhpMRowGAYDVQQKDBFBc2hva2EgVW5pdmVyc2l0eTEOMAwG\n" +
//                    "A1UECwwFQWRtaW4xFjAUBgNVBAMMDWFzaG9rYS5lZHUuaW4xIjAgBgkqhkiG9w0B\n" +
//                    "CQEWE2FkbWluQGFzaG9rYS5lZHUuaW4wggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAw\n" +
//                    "ggEKAoIBAQC4V2psuu2ypgAu4XeOTgHhyc08+zID+9BdavmSQECV+eCzIHQB4j0Y\n" +
//                    "5AI8SVurFQpBXS0M6Exy5EkXCC5hkIDuatLL8CS/anWOZzezf8An2R9Sl9u/pWXX\n" +
//                    "WbIsGj3b/80XkDDcjv3rBd3fAlLwBxb27AS5s4htE8/kjXxumP0gSd6ukFBc0gf/\n" +
//                    "rfg4uVDr0o3PVdH7fTgfL4gahll5S6ebWJOBcAlE9Uob3UAMBxAGkQYr53L2Y6HQ\n" +
//                    "uwGBl5fezNy3AXa1mHapnhmAFL2h6Wx5tJAXFGDGccDN6UnX56yVFP6yXoRAAGJZ\n" +
//                    "UCEpgmRprMYb3gp7KfDmc2jkf7iXu8glAgMBAAEwDQYJKoZIhvcNAQELBQADggEB\n" +
//                    "ACQ939kWYSG2bNk4lb1kAXoLI3XN1Ofps9CPAeIKWmYt2vaY0CpUoZIdgxyM5wzK\n" +
//                    "Znl2Tshu0hC2dRvHl8QLM879m9CB6wLjsvHJ+ssccEmSi4K+9Eu0nsWWl8g5exNx\n" +
//                    "nhvJ8lbtbZDKcTsaOwSCW+YzsFcNtgOI22EYjKAhgVVjGwtXbFu5lAXpAKQn18Di\n" +
//                    "P06EsYVUTFbhtYhoXl5e0IpWLMBun/P0O+9kmHZwKj+0nGESXQltKGJUoNvVf1Yp\n" +
//                    "lBbz7Abrh7ptSJyq30B3hMZm5knb1wAbHkhuKrJFuh9I1FROjO7ouGxzZODldMEc\n" +
//                    "vPNlX2oVYDtbcTxW+hYanl0=\n" +
//                    "-----END CERTIFICATE-----";
//
//            byte[] signatureReceived = CertHandler.signCert(keyPair1.getPrivate(), certString);
//
//            Signature signature = Signature.getInstance("SHA256withRSA");
//            signature.initVerify(keyPair.getPublic());
//
//            byte[] certificateBytes = KeyOperator.readCertificate(certString).getEncoded();
//
//            signature.update(certificateBytes);
//            boolean isTrue = signature.verify(signatureReceived);
//
//            System.out.println(isTrue);
//            System.out.println(keyPair.equals(keyPair));
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    public static void InitiateCertificate(String resourceFilePath, String fileName, Map<String,String> csrParams)
            throws IOException, InvalidCSRParamException, InvalidKeySpecException, NoSuchAlgorithmException {
        String keyFileName = fileName + ".key";
        String csrFileName = fileName + ".csr";
        String keyFilePath = resourceFilePath + File.separator + keyFileName;
        String csrFilePath = resourceFilePath + File.separator + csrFileName;
        File kf = new File(keyFilePath);
        File cf = new File(csrFilePath);
        KeyPair keyPair = null;
        //Generate Key pair if not exists in the resource folder
        if (!kf.exists()) {
            logger.info("Key file doesn't exist in the resource folder");
            keyPair = CertHandler.generateKeyPair("RSA", 2048);
            String privateKeyString = KeyOperator.getKeyString(keyPair);
            FileOperator.writeFile(privateKeyString, keyFilePath);
            logger.info("Key file created");
        } else {
            logger.info("Key file exists in the resource folder");
            keyPair = KeyOperator.getKeyPairFromKeyFile(keyFilePath);
            logger.info("KeyPair loaded from the existing file");
        }

        if (csrParams.size() == 6) {
            byte[] csr = CertHandler.generateCSR("SHA256WithRSA", keyPair, csrParams.get("commonName"),
                    csrParams.get("organizationUnit"), csrParams.get("organizationName"), csrParams.get("localityName"),
                    csrParams.get("stateName"), csrParams.get("country"));

            String csrString = new String(csr);
            //logger.info(csrString);
            logger.info("CSR is generated");
            FileOperator.writeFile(csrString, csrFilePath);

        } else {
            logger.warning("Invalid number of CSR params. Provide CSR params as an Map of [ String commonName, " +
                    "String organizationUnit, String organizationName, String localityName, String stateName, String country ]");
            throw new InvalidCSRParamException("Insufficient CSR params");
        }


    }
}