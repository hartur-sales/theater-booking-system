package hmd.teatroABC.util;

import hmd.teatroABC.model.entities.Area;

public class AreaUtil {
    public static Area getAreaPorIdentificador(char identificador, int segundoNumero) {
        Area area = null;
        switch (identificador) {
            case 'A' -> area = Area.PLATEIA_A;
            case 'B' -> area = Area.PLATEIA_B;
            case 'F' -> {
                if (segundoNumero >= 1 && segundoNumero <= 6) {
                    area = switch (segundoNumero) {
                        case 1 -> Area.FRISA1;
                        case 2 -> Area.FRISA2;
                        case 3 -> Area.FRISA3;
                        case 4 -> Area.FRISA4;
                        case 5 -> Area.FRISA5;
                        case 6 -> Area.FRISA6;
                        default -> null;
                    };
                }
            }
            case 'C' -> {
                if (segundoNumero >= 1 && segundoNumero <= 5) {
                    area = switch (segundoNumero) {
                        case 1 -> Area.CAMAROTE1;
                        case 2 -> Area.CAMAROTE2;
                        case 3 -> Area.CAMAROTE3;
                        case 4 -> Area.CAMAROTE4;
                        case 5 -> Area.CAMAROTE5;
                        default -> null;
                    };
                }
            }
            case 'N' -> area = Area.BALCAO_NOBRE;
            default -> throw new IllegalStateException("Unexpected value: " + identificador);
        }
        return area;
    }

    public static double getPrecoPorIdentificador(char identificador) {
        return switch (identificador) {
            case 'A' -> Area.PLATEIA_A.getPreco();
            case 'B' -> Area.PLATEIA_B.getPreco();
            case 'F' -> Area.FRISA1.getPreco();
            case 'C' -> Area.CAMAROTE1.getPreco();
            case 'N' -> Area.BALCAO_NOBRE.getPreco();
            default -> -1;
        };
    }
}
