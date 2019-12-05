package org.frontline.certInstaller.mschainOperation;

import org.frontline.certInstaller.certOperation.CertHandler;
import org.frontline.certInstaller.utils.FileOperator;
import org.frontline.certInstaller.utils.InvalidCSRParamException;
import org.frontline.certInstaller.utils.KeyOperator;

import java.io.File;
import java.io.IOException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;

public class CertManager {
    final static Logger logger = Logger.getLogger(CertManager.class.toString());

    private CaRequester caRequester;

    public CertManager() {
        this.caRequester = new CaRequester();
    }

    public void initiateCertificate(String resourceFilePath, String fileName, Map<String, String> csrParams)
            throws IOException, InvalidCSRParamException, InvalidKeySpecException, NoSuchAlgorithmException, ExecutionException, InterruptedException {
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
            String res = caRequester.getNewCertificateAsync(csr);

        } else {
            logger.warning("Invalid number of CSR params. Provide CSR params as an Map of [ String commonName, " +
                    "String organizationUnit, String organizationName, String localityName, String stateName, String country ]");
            throw new InvalidCSRParamException("Insufficient CSR params");
        }


    }
}
