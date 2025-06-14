package hmd.teatroABC.util;

public class CpfUtil {
    /**
     * Adiciona a máscara de CPF no formato "XXX.XXX.XXX-XX" a uma string, para uso em TextField.
     */
    public static String adicionarMascaraTextField(String entrada) {
        if (entrada == null) return "";

        String digits = removerMascaraCpf(entrada);
        if (digits.length() > 11) {
            digits = digits.substring(0, 11);
        }

        StringBuilder formatted = new StringBuilder();
        for (int i = 0; i < digits.length(); i++) {
            if (i == 3 || i == 6) formatted.append('.');
            if (i == 9) formatted.append('-');
            formatted.append(digits.charAt(i));
        }

        return formatted.toString();
    }

    public static String removerMascaraCpf(String cpf) {
        return NumeroUtil.removerDigitosNaoNumericos(cpf);
    }

    /**
     * Adiciona a máscara de CPF no formato "XXX.XXX.XXX-XX" a uma string.
     * <p>
     *     É usado para exibir o CPF em strings, como na tela dos ingressos.
     * </p>
     */
    public static String adicionarMascaraCpf(String cpf) {
        return cpf.replaceAll("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4");
    }

    public static boolean validarCPF(long cpf) {
        String cpfString = String.format("%011d", cpf);
        if (cpf == 0 || cpf % 11111111111L == 0) {
            return false;
            // retorna false se o cpf digitado foi 000 ou se todos seus dígitos forem iguais (22222222222, etc)
        }

        int[] cpfArray = new int[11];
        for (int i = 0; i < 11; i++) {
            cpfArray[i] = Integer.parseInt(String.valueOf(cpfString.charAt(i)));
            // passa os dígitos da string cpf para cada posiçao do array
        }

        int soma = 0;
        int peso = 10;
        for (int i = 0; i < 9; i++) {
            soma += cpfArray[i] * peso;
            peso--;
        }
        int primeiroDigito = (soma % 11 < 2) ? 0 : 11 - (soma % 11);
        // se o resto da divisão de soma por 11 for menor que 2, primeiroDigito recebe 0. caso contrario, primeiroDigito recebe 11 - soma % 11

        soma = 0;
        peso = 11;
        for (int i = 0; i < 10; i++) {
            soma += cpfArray[i] * peso;
            peso--;
        }
        int segundoDigito = (soma % 11 < 2) ? 0 : 11 - (soma % 11);
        // se o resto da divisão de soma por 11 for menor que 2, segundoDigito recebe 0. caso contrario, segundoDigito recebe 11 - soma % 11

        return cpfArray[9] == primeiroDigito && cpfArray[10] == segundoDigito;
        // verifica se as variaveis primeiroDigito e segundoDigito sao iguais aos digitos verificadores do CPF, que estao na posicao 9 e 10 do array
    }
}
