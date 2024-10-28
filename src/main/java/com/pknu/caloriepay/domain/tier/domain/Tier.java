package com.pknu.caloriepay.domain.tier.domain;

public enum Tier {
    S, A, B, C, D;

    public static Tier calculateDailyTier(double kcal) {
        if (kcal >= 1000) {
            return S;
        } else if (kcal >= 500) {
            return A;
        } else if (kcal > 0) {
            return B;
        } else if (kcal >= -200) {
            return C;
        }
        return D;
    }
    public static Tier calculateMonthlyTier(double kcal){
//        
        if(kcal >= 29000){
            return S;
        } else if (kcal>=16000) {
            return A;
        }
        else if (kcal >= 0){
            return B;
        }
        else if (kcal >=-5000){
            return C;
        }
        else{
            return D;
        }
    }
}
