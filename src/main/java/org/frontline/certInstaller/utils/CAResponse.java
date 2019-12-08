package org.frontline.certInstaller.utils;

import com.google.gson.Gson;


public class CAResponse {
    public class Result{
        String status;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
    private String operation;
    private Result result;
    private String certificate;

    public String getOperation() {
        return operation;
    }

    public Result getResult() {
        return result;
    }

    public String getCertificate() {
        return certificate;
    }


    public static CAResponse getCAResponseObject(String jsonString){
        Gson gson = new Gson();
        CAResponse caResponse = gson.fromJson(jsonString, CAResponse.class);
//        System.out.println(caResponse.getCertificate());
//        System.out.println(caResponse.getResult().getStatus());
        return caResponse;
    }
}
