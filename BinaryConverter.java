import java.util.Scanner;

public class BinaryConverter {
    private static final Scanner scan;
    private static final String PROMPT;
    private static final String OUTPUT;
    private static final String SELECTION;
    private static final String RESET;

    static {
        scan = new Scanner(System.in);
        PROMPT = "\u001b[96m";
        OUTPUT = "\u001b[92m";
        SELECTION = "\u001b[93m";
        RESET = "\u001b[48m\u001b[38m";
    }

    public static void main(String[] args) {
        boolean flag = false;

        System.out.println("Запущено приложение \"Бинарный конвертер\"");
        while (!flag) {
            String input = startDialog();
            input = input.replaceAll(",", ".");
            String types = "";

            if ( input.startsWith("e") ) {
                flag = true;
                System.out.println(SELECTION+"Завершение сеанса...");
            }
            else {
                try {
                    if (input.equals(Double.toString(Double.parseDouble(input)))) {
                        try {
                            types+='d';
                            if (input.equals(Float.toString(Float.parseFloat(input))))
                                types += 'f';
                        }
                        catch (NumberFormatException ignored) {}
                    }
                    if (!input.contains(".") || Double.parseDouble(input) == (long) Double.parseDouble(input)) {
                        if (input.contains("."))
                            input = Long.toString((long) Double.parseDouble(input));
                        try {
                            if (input.equals(Long.toString(Long.parseLong(input))))
                                types += 'l';
                            if (input.equals(Integer.toString(Integer.parseInt(input))))
                                types += 'i';
                            if (input.equals(Short.toString(Short.parseShort(input))))
                                types += 's';
                            if (input.equals(Byte.toString(Byte.parseByte(input))))
                                types += 'b';
                        }
                        catch (NumberFormatException ignored) {}
                    }
                }
                catch (NumberFormatException ignored) {}

                if (types.isEmpty())
                    System.err.println("Ошибка!\nЧисло не распознано, повторите попытку. Возможно, оно слишком большое," +
                            " или же не является числом вовсе");
                else
                    System.out.println(OUTPUT+typeDialog(types, input));
            }
        }
    }

    public static String startDialog() {
        System.out.println(RESET+"Для выхода из программы введите английскую букву "+SELECTION+"e"+RESET+PROMPT+
                "\nВведите число, которое вы хотите преобразовать в двоичный код:");
        return scan.nextLine().toLowerCase().strip();
    }

    public static String typeDialog(String types, String input) {
        String insert = "";
        if (types.contains("l")) {
            insert += "\nЦелочисленные типы данных:\nLong  - "+SELECTION+"От -9 223 372 036 854 775 808 до 9 223 372 036 854 775 808"+OUTPUT;
            if (types.contains("i")) {
                insert += "\nInt   - "+SELECTION+"От -2 147 483 648 до 2 147 483 647"+OUTPUT;
                if (types.contains("s")) {
                    insert += "\nShort - "+SELECTION+"От -32 768 до 32 767"+OUTPUT;
                    if (types.contains("b")) {
                        insert += "\nByte  - "+SELECTION+"От -128 до 127"+OUTPUT;
                    }
                }
            }
            insert += SELECTION+" <-- РЕКОМЕНДОВАНО"+OUTPUT;
        }
        if (types.contains("d")) {
            insert += "\nВещественночисленные типы данных:\nDouble - "+SELECTION+"1,7E +/- 308 (15 знаков)"+OUTPUT;
            if (types.contains("f")) {
                insert += "\nFloat  - "+SELECTION+"3,4E +/- 38 (7 знаков)";
            }
            insert += SELECTION+" <-- РЕКОМЕНДОВАНО";
        }
        System.out.println(PROMPT+"""
                Введите тип данных, в котором будет выведено число (Подсказка - можно ввести лишь первую букву типа)"""+
                OUTPUT+insert+PROMPT+
                """
                \nЕсли не уверены в выборе, используйте рекомендованные типы""");

        char inputT = scan.nextLine().toLowerCase().charAt(0);
        if (types.contains(""+inputT))
            return toBinary(inputT, input);
        else {
            System.err.print("Ошибка!\nТип данных не распознан, повторите попытку");
            return "";
        }
    }

    public static String toBinary(char mode, String input) {
        StringBuilder output = new StringBuilder();
        int i = 0, size = 0, exponent = 0;
        char fillerChar = '0';
        StringBuilder outputN = new StringBuilder();
        StringBuilder outputD = new StringBuilder();

        switch (mode) {
            case 'l' -> {
                size = Long.SIZE;
                long l = Long.parseLong(input);
                if (l < 0)
                    fillerChar = '1';
                l = Math.abs(l);
                for (i = 1; l >= 1; i++) {
                    output.append(l % 2);
                    l /= 2;
                    if (i % Byte.SIZE == 0) {
                        output.append(" ");
                    }
                }
            }
            case 'i' -> {
                size = Integer.SIZE;
                int x = Integer.parseInt(input);
                if (x < 0)
                    fillerChar = '1';
                x = Math.abs(x);
                for (i = 1; x >= 1; i++) {
                    output.append(x % 2);
                    x /= 2;
                    if (i % Byte.SIZE == 0) {
                        output.append(" ");
                    }
                }
            }
            case 's' -> {
                size = Short.SIZE;
                long s = Short.parseShort(input);
                if (s < 0)
                    fillerChar = '1';
                s = Math.abs(s);
                for (i = 1; s >= 1; i++) {
                    output.append(s % 2);
                    s /= 2;
                    if (i % Byte.SIZE == 0) {
                        output.append(" ");
                    }
                }
            }
            case 'b' -> {
                size = Byte.SIZE;
                long b = Byte.parseByte(input);
                if (b < 0)
                    fillerChar = '1';
                b = Math.abs(b);
                for (i = 1; b >= 1; i++) {
                    output.append(b % 2);
                    b /= 2;
                }
            }
            case 'd' -> {
                double d = Double.parseDouble(input);
                if (d < 0)
                    fillerChar = '1';
                d = Math.abs(d);
                int dN = (int) d;
                double dD = d - dN;
                while (dN >= 1) {
                    outputN.append(dN % 2);
                    dN /= 2;
                }
                exponent = 1023 + outputN.length() - 1;
                int index = outputN.lastIndexOf("1");
                if (index != -1)
                    outputN = new StringBuilder(outputN.substring(0, index));
                do {
                    dD += dD;
                    if (dD >= 1) {
                        outputD.append('1');
                        dD -= 1;
                    } else {
                        outputD.append('0');
                    }
                } while (dD > 0 && outputD.length() + outputN.length() - 2 < 52);
                while (outputD.length() + outputN.length() < 52)
                    outputD.append("0");
            }
            case 'f' -> {
                float f = Float.parseFloat(input);
                if (f < 0)
                    fillerChar = '1';
                f = Math.abs(f);
                //long dD = (long) ( (d%(int)d)*( Math.pow(10, Double.toString(d).indexOf(".")+1) ) );
                int fN = (int) f;
                float fD = f - fN;
                while (fN >= 1) {
                    outputN.append(fN % 2);
                    fN /= 2;
                }
                exponent = 127 + outputN.length() - 1;
                int index = outputN.lastIndexOf("1");
                if (index != -1)
                    outputN = new StringBuilder(outputN.substring(0, index));
                do {
                    fD += fD;
                    if (fD >= 1) {
                        outputD.append('1');
                        fD -= 1;
                    } else {
                        outputD.append('0');
                    }
                } while (fD > 0 && outputD.length() + outputN.length() - 2 < 21);
                while (outputD.length() + outputN.length() < 23)
                    outputD.append("0");
            }
        }
        switch (mode) {
            case 'd', 'f' -> {
                StringBuilder bD = new StringBuilder(outputD.toString());
                bD.reverse();
                output.append(bD).append(outputN).append(" ");
                while (exponent >= 1) {
                    output.append(exponent % 2);
                    exponent /= 2;
                }
                output.append(" ").append(fillerChar);
            }
            case 'l', 'i', 's', 'b' -> {
                if (fillerChar == '1') {
                    output = new StringBuilder(output.toString().replaceAll("0", "2"));
                    output = new StringBuilder(output.toString().replaceAll("1", "0"));
                    output = new StringBuilder(output.toString().replaceAll("2", "1"));
                }
                for (int j = 0; j++ < size + 1 - i; ) {
                    output.append(fillerChar);

                    if ((j + i - 1) % Byte.SIZE == 0)
                        output.append(" ");
                }
            }
        }

        StringBuilder outputf = new StringBuilder(output.toString());
        outputf.reverse();
        return input+" -> "+SELECTION+outputf;
    }
}
