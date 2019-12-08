package org.frontline.certInstaller.utils;

import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.asn1.pkcs.RSAPrivateKey;
import org.bouncycastle.asn1.pkcs.RSAPublicKey;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;
import org.bouncycastle.util.io.pem.PemWriter;

import java.io.*;
import java.security.*;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.*;

public class KeyOperator {

    public static String getKeyString(KeyPair keyPair) throws IOException {

        byte [] skBytes = keyPair.getPrivate().getEncoded();
        PrivateKeyInfo privateKeyInfo = PrivateKeyInfo.getInstance(skBytes);
        ASN1Encodable encodable = privateKeyInfo.parsePrivateKey();
        ASN1Primitive primitive = encodable.toASN1Primitive();

        PemObject pemObject = new PemObject("RSA PRIVATE KEY", primitive.getEncoded());
        StringWriter stringWriter = new StringWriter();
        PemWriter pemWriter = new PemWriter(stringWriter);
        pemWriter.writeObject(pemObject);
        pemWriter.close();
        return stringWriter.toString();
    }

    public static X509Certificate readCertificate(String certString) throws IOException, CertificateException {
        PemObject pemObject;
        PemReader pemReader = new PemReader(new StringReader(certString));
        pemObject = pemReader.readPemObject();
        CertificateFactory certificateFactory = CertificateFactory.getInstance("X509");
        ByteArrayInputStream in = new ByteArrayInputStream(pemObject.getContent());
        X509Certificate result = (X509Certificate) certificateFactory.generateCertificate(in);
        return result;
    }

    public static KeyPair getKeyPairFromKeyFile(String filePath) throws IOException, NoSuchAlgorithmException,
            InvalidKeySpecException {

        PemReader pemReader = new PemReader(new FileReader(filePath));
        PemObject pemObject = pemReader.readPemObject();
        byte[] content = pemObject.getContent();

        PrivateKey privateKey = null;
        PublicKey publicKey;

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        if (pemObject.getType().equals("RSA PRIVATE KEY")) {
            ASN1Sequence sequence = ASN1Sequence.getInstance(content);
            RSAPrivateKey key = RSAPrivateKey.getInstance(sequence);

            RSAPublicKeySpec publicKeySpec = new RSAPublicKeySpec(key.getModulus(), key.getPublicExponent());
            RSAPrivateCrtKeySpec privateCrtKeySpec = new RSAPrivateCrtKeySpec(
                    key.getModulus(),
                    key.getPublicExponent(),
                    key.getPrivateExponent(),
                    key.getPrime1(),
                    key.getPrime2(),
                    key.getExponent1(),
                    key.getExponent2(),
                    key.getCoefficient()
            );
            publicKey = keyFactory.generatePublic(publicKeySpec);
            privateKey = keyFactory.generatePrivate(privateCrtKeySpec);
        } else if (pemObject.getType().equals("PUBLIC KEY")) {
            KeySpec keySpec = new X509EncodedKeySpec(content);
            publicKey = keyFactory.generatePublic(keySpec);
        } else if (pemObject.getType().equals("RSA PUBLIC KEY")) {
            ASN1Sequence sequence = ASN1Sequence.getInstance(content);
            RSAPublicKey key = RSAPublicKey.getInstance(sequence);
            RSAPublicKeySpec publicKeySpec = new RSAPublicKeySpec(key.getModulus(), key.getPublicExponent());
            publicKey = keyFactory.generatePublic(publicKeySpec);
        } else {
            throw new IllegalArgumentException(pemObject.getType() + "is not supported");
        }
        KeyPair keyPair = new KeyPair(publicKey, privateKey);

        return keyPair;

    }

    public static String stringFromCert(X509Certificate certificate) throws IOException, CertificateEncodingException {
        StringWriter certStringWriter = new StringWriter();
        PemWriter pemWriter = new PemWriter(certStringWriter);
        pemWriter.writeObject(new PemObject("CERTIFICATE", certificate.getEncoded()));
        pemWriter.close();
        String certString = certStringWriter.toString();
        return certString;
    }

}
