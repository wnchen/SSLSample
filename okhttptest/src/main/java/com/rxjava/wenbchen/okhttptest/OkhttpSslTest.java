package com.rxjava.wenbchen.okhttptest;

import java.io.IOException;

public class OkhttpSslTest {

    public static void main(String[] args) throws IOException {
        //testOkhttpCertificatePinning();
    }

    private static void testOkhttpCertificatePinning() throws IOException {
//        OkHttpClient client = new OkHttpClient.Builder()
//                .certificatePinner(new CertificatePinner.Builder()
//                    .add("publicobject.com", "sha256/afwiKY3RxoMmLkuRW1l7QsPZTJPwDS2pdDROQjXw8ig=")
//                    .build())
//                .build();
//
//        Request request = new Request.Builder()
//                .url("https://publicobject.com/robots.txt")
//                .build();
//        try (Response response = client.newCall(request).execute()) {
//            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
//            for (Certificate certificate : response.handshake().peerCertificates()) {
//                System.out.println(CertificatePinner.pin(certificate));
//            }
//        } catch (Exception e) {
//            System.out.println("exception thrown");
//        }
    }
}
