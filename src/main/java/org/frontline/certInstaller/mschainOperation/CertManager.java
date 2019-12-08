package org.frontline.certInstaller.mschainOperation;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.bouncycastle.util.encoders.Hex;
import org.frontline.certInstaller.certOperation.CertHandler;
import org.frontline.certInstaller.config.Config;
import org.frontline.certInstaller.utils.*;

import java.io.File;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.security.spec.InvalidKeySpecException;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;

public class CertManager {
    final static Logger logger = Logger.getLogger(CertManager.class.toString());

    private CaRequester caRequester;

    private KeyPair keyPair;
    private Certificate certificate;

    public CertManager() {
        this.caRequester = new CaRequester();
        this.keyPair = null;
        this.certificate = null;
    }

    public KeyPair getKeyPair() {
        return keyPair;
    }

    public void setKeyPair(KeyPair keyPair) {
        this.keyPair = keyPair;
    }

    public Certificate getCertificate() {
        return certificate;
    }

    public void setCertificate(Certificate certificate) {
        this.certificate = certificate;
    }

    public void initiateCertificate(String resourceFilePath, String fileName, Map<String, String> csrParams)
            throws IOException, InvalidCSRParamException, InvalidKeySpecException, NoSuchAlgorithmException,
            ExecutionException, InterruptedException, CertificateException, CertGenerationException {
        String keyFileName = fileName + ".key";
        String csrFileName = fileName + ".csr";
        String certFile = fileName + ".pem";
        String keyFilePath = resourceFilePath + File.separator + keyFileName;
        String csrFilePath = resourceFilePath + File.separator + csrFileName;
        String certFilePath = resourceFilePath + File.separator + certFile;
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
        this.setKeyPair(keyPair);

        if (csrParams.size() == 6) {
            byte[] csr = CertHandler.generateCSR("SHA256WithRSA", keyPair, csrParams.get("commonName"),
                    csrParams.get("organizationUnit"), csrParams.get("organizationName"), csrParams.get("localityName"),
                    csrParams.get("stateName"), csrParams.get("country"));

            String csrString = new String(csr);
            //logger.info(csrString);
            logger.info("CSR is generated");
            FileOperator.writeFile(csrString, csrFilePath);
            String res = caRequester.getNewCertificateAsync(csr, Config.NEW_CERT_URL);

            CAResponse caResponse = CAResponse.getCAResponseObject(res);

            if (caResponse.getResult().getStatus().equals(Config.OK)) {
                Certificate cert = KeyOperator.readCertificate(caResponse.getCertificate());
                FileOperator.writeFile(caResponse.getCertificate(),certFilePath );
                this.setCertificate(cert);
            } else {
                logger.warning("Either you have requested a certificate for the same key or " +
                        "certificate already exists in MSChain");
                throw new CertGenerationException("Certificate Generation Failed.");
            }

        } else {
            logger.warning("Invalid number of CSR params. Provide CSR params as an Map of [ String commonName, " +
                    "String organizationUnit, String organizationName, String localityName, String stateName, String country ]");
            throw new InvalidCSRParamException("Insufficient CSR params");
        }


    }

    public void updateRevokedCert(String resourceFilePath, String oldKeyFile, String newKeyFile,
                                  Map<String, String> csrParams) throws IOException, InvalidKeySpecException,
            NoSuchAlgorithmException, ExecutionException, InterruptedException, CertificateException,
            CertGenerationException, InvalidCSRParamException, SignatureException, InvalidKeyException {
        String oldKeyFileName = oldKeyFile + ".key";
        String newKeyFileName = newKeyFile + ".key";
        String newCsrFileName = newKeyFile + ".csr";
        String newCertFileName = newKeyFile + ".pem";
        String oldKeyFilePath = resourceFilePath + File.separator + oldKeyFileName;
        String newKeyFilePath = resourceFilePath + File.separator + newKeyFileName;
        String newCsrFilePath = resourceFilePath + File.separator + newCsrFileName;
        String newCertFilePath = resourceFilePath + File.separator + newCertFileName;
        File newKf = new File(newKeyFilePath);
        KeyPair keyPair = null;
        KeyPair oldKeyPair = null;
        try {
            oldKeyPair = KeyOperator.getKeyPairFromKeyFile(oldKeyFilePath);
        } catch (NullPointerException e) {
            logger.warning("Old key pair does not exist");
            logger.warning(e.getMessage());
        }
        //============================Generate Key pair if not exists in the resource folder
        String proposedCertString = null;
        if (!newKf.exists()) {
            logger.info("Key file doesn't exist in the resource folder");
            keyPair = CertHandler.generateKeyPair("RSA", 2048);
            String privateKeyString = KeyOperator.getKeyString(keyPair);
            FileOperator.writeFile(privateKeyString, newKeyFilePath);
            logger.info("Key file created");
        } else {
            logger.info("Key file exists in the resource folder");
            keyPair = KeyOperator.getKeyPairFromKeyFile(newKeyFilePath);
            logger.info("KeyPair loaded from the existing file");
        }
        this.setKeyPair(keyPair);

        if (csrParams.size() == 6) {
            byte[] csr = CertHandler.generateCSR("SHA256WithRSA", keyPair, csrParams.get("commonName"),
                    csrParams.get("organizationUnit"), csrParams.get("organizationName"), csrParams.get("localityName"),
                    csrParams.get("stateName"), csrParams.get("country"));

            String csrString = new String(csr);
            logger.info("CSR is generated");
            FileOperator.writeFile(csrString, newCsrFilePath);
            String res = caRequester.getNewCertificateAsync(csr, Config.NEW_PROPOSED_CERT_URL);

            ObjectMapper mapper = new ObjectMapper();
            JsonNode proposedCertJson = mapper.readTree(res);
            proposedCertString = proposedCertJson.get("certificate").asText();
            X509Certificate cert = KeyOperator.readCertificate(proposedCertString);
            this.setCertificate(cert);

        } else {
            logger.warning("Invalid number of CSR params. Provide CSR params as an Map of [ String commonName, " +
                    "String organizationUnit, String organizationName, String localityName, String stateName, String country ]");
            throw new InvalidCSRParamException("Insufficient CSR params");
        }

        //========================================================
        byte[] signature = CertHandler.signCert(oldKeyPair.getPrivate(), proposedCertString);
        String signatureString = new String(Hex.encode(signature));

        String responseString = caRequester.updateCertAsync(proposedCertString, signatureString);
        CAResponse caResponse = CAResponse.getCAResponseObject(responseString);

        if (caResponse.getResult().getStatus().equals(Config.OK)) {
            X509Certificate cert = KeyOperator.readCertificate(proposedCertString);
            FileOperator.writeFile(proposedCertString, newCertFilePath);
            this.setCertificate(cert);
        } else {
            logger.warning("Certificate Generation Failed.");
            throw new CertGenerationException("Certificate Generation Failed.");
        }
    }
}
