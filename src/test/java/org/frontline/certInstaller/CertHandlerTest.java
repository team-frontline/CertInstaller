package org.frontline.certInstaller;

import org.bouncycastle.util.encoders.Hex;
import org.frontline.certInstaller.certOperation.CertHandler;
import org.frontline.certInstaller.utils.KeyOperator;
import org.junit.Test;

import java.security.KeyPair;

import static org.junit.Assert.assertEquals;

public class CertHandlerTest {

    @Test
    public void testSign(){
        try {
            String cert = "-----BEGIN CERTIFICATE-----\n" +
                    "MIIDDDCCAfQCBgFu4fUERzANBgkqhkiG9w0BAQsFADAmMRYwFAYDVQQDDA1hZG1p\n" +
                    "bi5jYTEuY29tMQwwCgYDVQQKDANDQTEwHhcNMTkxMjA2MjAwMjQxWhcNMjExMjA2\n" +
                    "MjAwMjQxWjBtMQswCQYDVQQGEwJTTDENMAsGA1UECBMEV2VzdDEQMA4GA1UEBxMH\n" +
                    "Q29sb21ibzEWMBQGA1UEChMNRnJvbnRsaW5lLm9yZzEQMA4GA1UECxMHTVNDaGFp\n" +
                    "bjETMBEGA1UEAxMKY2hhbWVlLm9yZzCCASIwDQYJKoZIhvcNAQEBBQADggEPADCC\n" +
                    "AQoCggEBANC2qCSMmS9zkTouz5rAfpSm/YQjTGigdzh7wTTDQSXOiufzZyb14e0E\n" +
                    "ZkaikMEoKpiAXK5oS14MxneYqIeTjT6YijItGr5kCgq9hgFO7aKfo351ABoIS0J5\n" +
                    "cbMcYQd7SmhNu3PHNyfV0t2fDvCjbEbu94+viA6y9B0RMXbDow68IZ3UUloUUlAL\n" +
                    "gKTRAKBWJtkDmy72vKqRodQv3k+19z9+6sc8BfL+BRcqYfSMz5TB+cn6lJ1VwDSA\n" +
                    "i/2e0jflN72hz3wzvdC+9/Up0YiI07P4UN0ndxBBzOZSBPs592bsAu44JKMTMLkA\n" +
                    "7N+1HzUi+O+V7Q6aATPTBQzMdMcsZocCAwEAATANBgkqhkiG9w0BAQsFAAOCAQEA\n" +
                    "XI3RD4N9SjSJUcFOftD2+UAiB+Z2DU6BudUWWuW6dMl+khkCKn//4ifLztg3jFIZ\n" +
                    "OyQRof7lAMOx3zy6Go6u/hj8gQ/7igsDOcsF6beik8TXiIMRxQt2wpjVqIvENMS8\n" +
                    "LhhKPG3X2yEItdfjitm/6PRUmUo2IjeIW5EFQu5COYSBPq5pVTJNsph3bjdPrWpG\n" +
                    "wRJaIFcau827yyhB8PTWbw3qDphMBldrKbDi5OV9utQEHc+Ea6FyOV2ab6hlkGQ4\n" +
                    "767FvOWfDNdZkE593AcR7jWOcb0VfLzpaRHdewFQUEi4f7tGZeTnKMeMXx+khTkI\n" +
                    "uvPQ8B4Tw7Uf2T2n/lsHWg==\n" +
                    "-----END CERTIFICATE-----";
            String t = "289832cbd5366dfe9429d65cc0f032665ae8826ce877589de1dd9f39dc756b94744fa448cec25f93236b7897a847d013f5658d8c6d783c76c18c86171d1c5fe5215545f51c09181e83fce7965678dba8df78024395557edddf14b0dc81140f246e4d343dd573cd06ab6ed1b04aeb4dbf4dfa6e1a589ad806221a0444674f3b0553d26a190cbb19f8f43576fdce3f108b7234ba62bcfe91e21070804cdb4bc99819f4cbf2b86e54d5982c212254fdcc18559acf84495372f17955793e574ce22be8a36cc4ed883cc5c4bb58d24daeaefee7affcfe92228bcd3c56cabd588d23f06c33e25d9dc6d3e72a749d814bfaa576dd1c8038f7cda3547715567f7cd93969";
            KeyPair keyPair = KeyOperator.getKeyPairFromKeyFile("src/main/resources/chamee.key");
            byte[] sig = CertHandler.signCert(keyPair.getPrivate(),cert);
            String signatureString = new String(Hex.encode(sig));
            signatureString.equals(t);
            assertEquals(t,signatureString);


        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
