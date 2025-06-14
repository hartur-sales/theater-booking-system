package hmd.teatroABC.util;

public class NumeroUtil {
    public static String removerDigitosNaoNumericos(String entrada) {
        return entrada == null ? "" : entrada.replaceAll("\\D", "");
    }

    /**
     * Aplica máscara de telefone brasileiro a uma string, que será usada num TextField, para o
     * formato (11) 91234-5678
     */
    public static String aplicarMascaraTelefone(String telefone) {
        String digits = removerDigitosNaoNumericos(telefone);
        if (digits.length() > 11) {
            digits = digits.substring(0, 11);
        }

        StringBuilder formatted = new StringBuilder();
        int len = digits.length();

        if (len == 0) {
            return "";
        } else if (len <= 2) {
            formatted.append("(").append(digits);
        } else {
            formatted.append("(")
                    .append(digits, 0, 2)
                    .append(") ");

            int numLen = len - 2;

            if (numLen <= 5) {
                formatted.append(digits.substring(2));
            } else {
                formatted.append(digits, 2, 7)
                        .append("-")
                        .append(digits.substring(7));
            }
        }

        return formatted.toString();
    }

    /**
     * Aplica máscara de CEP brasileiro a uma string, para o formato 12345-678.
     */
    public static String aplicarMascaraCep(String cep) {
        String digits = removerDigitosNaoNumericos(cep);

        if (digits.length() > 8) {
            digits = digits.substring(0, 8);
        }

        StringBuilder formatted = new StringBuilder();
        for (int i = 0; i < digits.length(); i++) {
            if (i == 5) {
                formatted.append('-');
            }
            formatted.append(digits.charAt(i));
        }

        return formatted.toString();
    }
}
