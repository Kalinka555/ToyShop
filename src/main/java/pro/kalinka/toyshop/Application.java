package pro.kalinka.toyshop;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Random;

public class Application {
    final private static String ProductRegister = "C:\\Users\\Alina\\IdeaProjects\\ToyShop\\src\\main\\resources\\ProductRegister(Toys).txt";
    final private static Charset UNICODE = StandardCharsets.UTF_8;
    final private static Path PATH_OF_LIST_PRODUCTS = Path.of(ProductRegister);

    final private static String COMMAND_LIST_PRODUCT = "list-products";
    final private static String COMMAND_QUESTION = "?";
    final private static String COMMAND_SLASH_QUESTION = "/?";
    final private static String COMMAND_MINUS_QUESTION = "-?";
    final private static String COMMAND_MINUS_H = "-h";
    final private static String COMMAND_MINUS_HELP = "--help";
    final private static String COMMAND_ORDER = "create-order";

    final private static String WELCOME_TEXT = "Welcome to our online shop";
    final private static String USAGE_TEXT = "Usage:\n" +
            " java -jar ToyShop.jar [command] [options]\n" +
            " Commands:\n" +
            "  list-products            Shows all available products in the shop\n" +
            "  ?, /?, -?, -h, --help    Shows help and usage information\n" +
            "  create-order <id> <count> <deliveryAddress>   Allows to create an order with following parameters: ID-number, count, delivery address.";
    final private static String THANKS_TEXT = "  Your order number is № %d.\n" +
            "  Our manager will contact you to confirm the order. \n" +
            "  Thank you and we look forward to seeing you again in our store.\n";
    final private static String WRONG_ID_NUMBER_TEXT = "The ID-number must include only numbers from 1000 to 9999, please try again.\n";
    final private static String WRONG_COUNT_NUMBER_TEXT = "The count must include only numbers from 1 to 99, please try again.\n";
    final private static String WRONG_ID_NUMBER_FORMAT = "The ID-number must include only numbers, please try again.\n";
    final private static String WRONG_COUNT_NUMBER_FORMAT = "The count must include only numbers, please try again.\n";
    final private static String PARAMETERS_NOT_FILLED = "One or more of following parameters: <id> <count> <delivery address>, were not filled in. Please try again.";
    final private static String ORDER_FILE_TEXT = " Order number - %d\n ID-number -  %d\n Count - %d\n Delivery address -  %s\n";

    public static void main(String... args) throws IOException {

        if (args.length == 0) {
            String usageTextResult = usageText(args);
            System.out.println(usageTextResult);
        } else {
            String arg1 = args[0];
            switch (arg1) {
                case COMMAND_LIST_PRODUCT:
                    String listProductResult = listProducts(args);
                    System.out.println(listProductResult);
                    break;

                case COMMAND_QUESTION:
                case COMMAND_SLASH_QUESTION:
                case COMMAND_MINUS_QUESTION:
                case COMMAND_MINUS_HELP:
                case COMMAND_MINUS_H:
                    String usageTextResult = usageText(arg1);
                    System.out.println(usageTextResult);
                    break;

                case COMMAND_ORDER:
                    String orderPlacingResult = orderPlacing(args);
                    System.out.println(orderPlacingResult);
                    break;
                    
                default:
                    String otherCasesResult = otherCases(args);
                    System.out.println(otherCasesResult);
                    break;
            }
        }
    }

    /**
     * метод возвращает информацию по использованию команд (usage information)
     *
     * @param args
     * @return
     */
    public static String usageText(String... args) {
        return USAGE_TEXT;
    }

    /**
     * метод возвращает перечень всей продукции с описанием и ценами при запросе "list-products" от клиента
     *
     * @param args
     * @return
     */
    public static String listProducts(String[] args) {

        try {
            final String allProductsList = Files.readString(PATH_OF_LIST_PRODUCTS, UNICODE);
            System.out.println(allProductsList);
            return allProductsList;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return USAGE_TEXT;
    }

    /**
     * Метод - для проверки, что в строке присутствую только цифры
     *
     * @param input
     * @return
     */
    private static boolean isNotNumeric(String input) {
        if (input == null) return true;
        return !input.matches("\\d+");
    }

    /**
     * метод для оформления заказа, предусматривает исключение пустых параметров и буквенного ввода в параметры "ID номер" и "количество";
     * учитывает диапазон чисел для ID-номера и количества товара;
     * выдает случайно сгенерированный номер заказа;
     * создает текстовый файл с именем "order-NUMBER.txt", где NUMBER - это сгенерированный ранее номер заказа, и
     * передает все внесенные клиентом параметры в данный текстовый файл;
     * направляет клиенту подтвердение заказ с указанием номера заказа и благодарит клиента.
     *
     * @param args
     * @return
     * @throws FileNotFoundException
     */
    public static String orderPlacing(String[] args) throws FileNotFoundException {
        if (args.length == 1 || args.length == 2 || args.length == 3) {
            try {
                throw new ArrayIndexOutOfBoundsException(PARAMETERS_NOT_FILLED);
            } catch (ArrayIndexOutOfBoundsException e) {
                return PARAMETERS_NOT_FILLED;
            }
        }
        String IDNumberParameter = args[1];
        String countParameter = args[2];
        String deliveryAddress = args[3];
        if (isNotNumeric(IDNumberParameter)) {
            return WRONG_ID_NUMBER_FORMAT;
        }
        if (isNotNumeric(countParameter)) {
            return WRONG_COUNT_NUMBER_FORMAT;
        }
        int IDNumber = Integer.parseInt(IDNumberParameter);
        int count = Integer.parseInt(countParameter);

        if (IDNumber < 1001 || IDNumber > 10000) {
            return WRONG_ID_NUMBER_TEXT;

        } else if (count < 0 || count > 99) {
            return WRONG_COUNT_NUMBER_TEXT;

        } else {
            final Random RANDOM = new Random();
            final int ORDER_NUMBER = RANDOM.nextInt(9998) + 1;
            final String ORDER_FILE_NAME = "order-" + ORDER_NUMBER + ".txt";
            try {
                PrintWriter orderFile = new PrintWriter(ORDER_FILE_NAME);
                orderFile.printf(ORDER_FILE_TEXT, ORDER_NUMBER, IDNumber, count, deliveryAddress);
                orderFile.close();
            } catch (FileNotFoundException e) {
                return "Cannot save the order :(";
            }
            return String.format(THANKS_TEXT, ORDER_NUMBER);
        }
    }

    /**
     * метод возвращает приветственное сообщение клиенту при вводе любых значений,
     * кроме предусмотренных в информации по использованию команд (usage information)
     *
     * @param args
     * @return
     */
    public static String otherCases(String[] args) {
        return WELCOME_TEXT;
    }
}