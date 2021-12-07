package com.example.dtp.enums;

public enum PunishmentClass {
    WAITING,
    INNOCENT,
    PENALTY,
    LICENSE_DEPRIVATION,
    ARRESTING;
    
    public static PunishmentClass convert (String punishmentRequest) {
        String punishment = punishmentRequest.toUpperCase();
        for (PunishmentClass punishmentClass : PunishmentClass.values()) {
            if (punishmentClass.toString().equals(punishment)) {
                return punishmentClass;
            }
        }
        return null;
    }
}
