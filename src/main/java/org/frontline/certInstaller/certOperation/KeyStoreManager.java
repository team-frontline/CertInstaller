package org.frontline.certInstaller.certOperation;


import org.frontline.certInstaller.mschainOperation.CertManager;
import org.frontline.certInstaller.utils.CertGenerationException;
import org.frontline.certInstaller.utils.InvalidCSRParamException;

import java.io.*;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;


public class KeyStoreManager {


    public static void createKeyStore(String resourcePath, KeyPair keyPair, String aliasName, String password, Certificate certificate, String keyStoreName)
            throws IOException, KeyStoreException {
        Certificate[] certificates = new Certificate[1];
        certificates[0] = certificate;
        FileOutputStream fos = new FileOutputStream(resourcePath + File.separator + keyStoreName);
        KeyStore keyStore = KeyStore.getInstance("JKS");
        keyStore.setKeyEntry(aliasName, keyPair.getPrivate(), password.toCharArray(), certificates);
        //   keyStore.store(fos, password.toCharArray());
        System.out.println("create key store");
        System.out.println("--------------");
        fos.close();

    }


    public static void installCert(String resourcePath, KeyPair keyPair, String aliasName, String password,
                                   Certificate certificate, String keyStoreName) throws KeyStoreException, IOException, CertificateException, NoSuchAlgorithmException {

        String keyStoreFilePath = resourcePath + File.separator + keyStoreName;
        KeyStore ks = KeyStore.getInstance("JKS");
        ks.load(null, null);

        Certificate[] certificates = new Certificate[1];
        certificates[0] = certificate;
        ks.setKeyEntry(aliasName, keyPair.getPrivate(), password.toCharArray(), certificates);

        ks.store(new FileOutputStream(keyStoreName), password.toCharArray());
    }

}
