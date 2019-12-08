package org.frontline.certInstaller;

import org.frontline.certInstaller.certOperation.KeyStoreManager;
import org.frontline.certInstaller.mschainOperation.CertManager;
import org.frontline.certInstaller.utils.CAResponse;
import org.junit.Test;

import java.io.IOException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

public class CAResponseTest {

    @Test
    public void getCaResponseTest() throws CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException {
        String cerString = "{\n" +
                "\"operation\":\"Adding Certificate\",\n" +
                "\"result\":{\"status\":\"OK\"},\n" +
                "\"certificate\":" +
                "\"-----BEGIN CERTIFICATE-----\\nMIIDCTCCAfECBgFu1SWkoTANBgkqhkiG9w0BAQsFADAmMRYwFAYDVQQDDA1hZG1p\\nbi5jYTEuY29tMQwwCgYDVQQKDANDQTEwHhcNMTkxMjA0MDgyMDQ0WhcNMjExMjA0\\nMDgyMDQ0WjBqMQswCQYDVQQGEwJTTDENMAsGA1UECBMEV2VzdDEQMA4GA1UEBxMH\\nQ29sb21ibzEWMBQGA1UEChMNRnJvbnRsaW5lLm9yZzEQMA4GA1UECxMHTVNDaGFp\\nbjEQMA4GA1UEAxMHbXMyLm9yZzCCASIwDQYJKoZIhvcNAQEBBQADggEPADCCAQoC\\nggEBALK/mSnGJKKKeuU/fpmUgdPyV8VqTB5ZrEs20Qdb5VUSSu0m+AMdxFwMaMr2\\nsJM0aea1wTwCe2oheAzBCOe0hOpAV+Zyaq4PQapRQdnrKCSUGWnHgHr4t07okBNF\\nNIB5fdXMJeKbeOyOO8vv4GQDbQhWX/Ul0x3vEc1ClpFs1iarYpRhhwd0HFfd/5OP\\nUbesLM+qUo4JurfmYk2XLwaKKwfkWOASDHo/4DPWqJaaFlo9CEbkwtHRgefbrjHZ\\n5OA++LkfQEtGFcgwG22Lq73cEyzoaxB4fnXaaHIH6dnrdxsN5e4JRCNHA9W1dlZm\\njCNnzy6I3pLULvo8Id91j1miNFsCAwEAATANBgkqhkiG9w0BAQsFAAOCAQEAjQ01\\nVHYetk+MVlXjcotZhzLGGUJ+3/0v75xd8xSG68s9YR5xdvtCS8P4XjbW4saaVhVL\\njSMj4ivXLMrwZhlZVerinIZU0zBb/x+T/f/RrbKZiQrrPcz8Heq0EuaSnbzcBnJD\\nAtk16Rryb+FBJKCyyLd4p4KG03iqEODd4GQhABMp3lbwcXdiXsV4bHFbstpU/Gcu\\n9vgU09CHVjr5NvG7vGxj2eZHyEofrS0vNZ/KsGqr3+9F4Z5FT35R8BOk3zrom+AH\\n4bfvdxZfH18+1919lsLT+MOEOdW0M8WFbbSe/XWrGerQxQy/9z3vCAeroTmo2If7\\n86OK1YUUAWfwcQGnRg==\\n-----END CERTIFICATE-----\\n\"\n" +
                "}\n";

        CAResponse.getCAResponseObject(cerString);
        CertManager certManager = new CertManager();
//        KeyStoreManager.createKeyStore("src/main/resources/keystore.jks",certManager.getKeyPair(),"store123"
//                ,"pass123",certManager.getCertificate(),"");

    }
}
