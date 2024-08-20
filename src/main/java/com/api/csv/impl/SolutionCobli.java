package com.api.csv.impl;

import java.util.ArrayList;
import java.util.List;

public record SolutionCobli() {
    public static void main(String[] args) {

        String[] addresses = {"Cobli", "192.168.0.1", "2001:0db8:0a0b:12f0:0000:0000:0000:0001"};

        List<String> results = new ArrayList<>();

        for (String address : addresses) {
            results.add(validateIPAddress(address));
        }

        System.out.println(results); // Saída esperada: ['Neither', 'IPv4', 'IPv6']
    }

    public static String validateIPAddress(String ipAddress) {
        // Verificar se é IPv4
        if (isValidIPv4(ipAddress)) {
            return "IPv4";
        }
        // Verificar se é IPv6
        if (isValidIPv6(ipAddress)) {
            return "IPv6";
        }
        // Caso contrário, retornar "Neither"
        return "Neither";
    }

    private static boolean isValidIPv4(final String ipAddress) {

        String[] groups = ipAddress.split("\\.");

        // Verificar se tem 4 grupos
        if (groups.length != 4) {
            return false;
        }

        for (String group : groups) {

            // Verificar se cada grupo tem 3 caracteres ou menos
            if (group.isEmpty() || group.length() > 3) {
                return false;
            }

            // Verificar se não começa com 0, exceto se for 0
            if (group.charAt(0) == '0' && group.length() > 1 && Integer.parseInt(group) > 7) {
                return false;
            }

            // Verificar se cada grupo é um inteiro entre 0 e 255
            try {
                int value = Integer.parseInt(group);
                if (value < 0 || value > 255) {
                    return false;
                }
            } catch (NumberFormatException e) {
                e.getStackTrace();
            }
        }

        return true;
    }

    private static boolean isValidIPv6(String ipAddress) {

        String[] groups = ipAddress.split(":");

        // Verificar se tem 8 grupos ou menos
        if (groups.length > 8) {
            return false;
        }

        boolean hasDoubleColon = ipAddress.indexOf("::") != -1;

        // Se tiver "::", garantir que seja apenas uma ocorrência
        if (hasDoubleColon && ipAddress.indexOf("::", ipAddress.indexOf("::") + 2) != -1) {
            return false;
        }

        for (String group : groups) {
            // Verificar se cada grupo tem 4 caracteres ou menos
            if (group.length() == 0 || group.length() > 4) {
                return false;
            }

            // Verificar se cada grupo contém apenas dígitos hexadecimais
            for (int i = 0; i < group.length(); i++) {
                char ch = group.charAt(i);
                if (!((ch >= '0' && ch <= '9') || (ch >= 'a' && ch <= 'f') || (ch >= 'A' && ch <= 'F'))) {
                    return false;
                }
            }
        }

        // Se houver "::", garantir que haja apenas um conjunto de "::"
        if (hasDoubleColon && groups.length > 1) {
            return true;
        }

        return groups.length == 8;
    }
}