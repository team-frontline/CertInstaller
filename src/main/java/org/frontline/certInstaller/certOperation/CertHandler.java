package org.frontline.certInstaller.certOperation;

import org.frontline.certInstaller.utils.KeyOperator;
import sun.security.pkcs10.PKCS10;
import sun.security.x509.X500Name;

import java.io.*;
import java.security.*;
import java.security.cert.CertificateException;;
import java.security.cert.X509Certificate;



public class CertHandler {

    public static KeyPair generateKeyPair(String alg, int keySize) {
        try {
            KeyPairGenerator keyPairGenerator = null;
            keyPairGenerator = KeyPairGenerator.getInstance(alg);

            keyPairGenerator.initialize(keySize);

            return keyPairGenerator.generateKeyPair();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public static byte[] generateCSR(String sigAlg, KeyPair keyPair, String commonName, String organizationUnit,
                                     String organizationName, String localityName, String stateName,
                                     String country) {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outStream);

        try {
            /*  public X500Name(String commonName,
                String organizationUnit,
                String organizationName,
                String localityName,
                String stateName,
                String country)*/
            X500Name x500Name = new X500Name(commonName, organizationUnit, organizationName, localityName, stateName, country);

            Signature sig = Signature.getInstance(sigAlg);

            sig.initSign(keyPair.getPrivate());

            PKCS10 pkcs10 = new PKCS10(keyPair.getPublic());
            pkcs10.encodeAndSign(x500Name, sig);                   // For Java 7 and Java 8
            pkcs10.print(printStream);

            byte[] csrBytes = outStream.toByteArray();

            return csrBytes;
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (null != outStream) {
                try {
                    outStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (null != printStream) {
                printStream.close();
            }
        }

        return new byte[0];
    }

    public static byte[] signCert(PrivateKey privateKey, String certString) throws NoSuchAlgorithmException,
            InvalidKeyException, IOException, SignatureException, CertificateException {
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initSign(privateKey);
        X509Certificate certificate = KeyOperator.readCertificate(certString);
        byte[] certificateBytes = certificate.getEncoded();
        signature.update(certificateBytes);
        return signature.sign();
    }
}

