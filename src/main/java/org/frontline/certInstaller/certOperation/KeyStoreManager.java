package org.frontline.certInstaller.certOperation;

import java.io.*;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.util.logging.Logger;


public class KeyStoreManager {

    final static Logger logger = Logger.getLogger(KeyStoreManager.class.toString());

    public static void installCert(String resourcePath, KeyPair keyPair, String aliasName, String password,
                                   Certificate certificate, String keyStoreName) throws KeyStoreException, IOException, CertificateException, NoSuchAlgorithmException {
        logger.info("Installing certificate to the key store");
        String keyStoreFilePath = resourcePath + File.separator + keyStoreName;
        KeyStore ks = KeyStore.getInstance("JKS");
        ks.load(null, null);

        Certificate[] certificates = new Certificate[1];
        certificates[0] = certificate;
        ks.setKeyEntry(aliasName, keyPair.getPrivate(), password.toCharArray(), certificates);

        ks.store(new FileOutputStream(keyStoreFilePath), password.toCharArray());
        logger.info("Certificate successfully installed.");


    }

    public static void updateKeyStore(String keystorePath, KeyPair keyPair, String aliasName, String password,
                                      Certificate certificate) throws KeyStoreException, IOException, CertificateException, NoSuchAlgorithmException {
        KeyStore keyStore = KeyStore.getInstance("JKS");
        InputStream is = new FileInputStream(new File(keystorePath));
        keyStore.load(is, password.toCharArray());
        Certificate[] certificates = keyStore.getCertificateChain(aliasName);
        Certificate[] certificatesWithNew = new Certificate[certificates.length + 1];
        for (int i = 0; i < certificates.length; i++) {
            certificatesWithNew[i] = certificates[i];
        }
        certificatesWithNew[certificates.length]=certificate;
        certificates[0] = certificate;
        keyStore.setKeyEntry(aliasName, keyPair.getPrivate(), password.toCharArray(), certificatesWithNew);
        new File(keystorePath).delete();
        keyStore.store(new FileOutputStream(keystorePath), password.toCharArray());

    }

}
