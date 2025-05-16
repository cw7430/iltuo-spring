package kr.co.iltuo.service.auth.util;

public class AuthConvertUtil {

    public static String convertPermission(String userPermissionsCode) {
        return "AR002".equals(userPermissionsCode) ? "ADMIN" : "USER";
    }

    public static String convertAuthMethodToString(String authMethodCode) {
        return "AM002".equals(authMethodCode) ? "SOCIAL" : "NATIVE";
    }

    public static String convertAuthMethodToCode(String authMethod) {
        return "SOCIAL".equals(authMethod) ? "AM002" : "AM001";
    }

}
