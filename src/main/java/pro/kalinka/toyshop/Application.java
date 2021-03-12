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

    final private static String COMMAND_LIST_PRODUCT = "list-products";
    final private static String COMMAND_QUESTION = "?";
    final private static String COMMAND_SLASH_QUESTION = "/?";
    final private static String COMMAND_MINUS_QUESTION = "-?";
    final private static String COMMAND_MINUS_H = "-h";
    final private static String COMMAND_MINUS_HELP = "--help";
    final private static String COMMAND_CREATE_ORDER = "create-order";
    final private static String COMMAND_CHANGE_ORDER_COUNT = "change-order-count";
    final private static String COMMAND_COMMIT_ORDER = "commit-order";

    final private static String ProductRegister = "C:\\Users\\Alina\\IdeaProjects\\ToyShop\\src\\main\\resources\\ProductRegister(Toys).txt";
    final private static Path PATH_OF_LIST_PRODUCTS = Path.of(ProductRegister);
    final private static Charset UNICODE = StandardCharsets.UTF_8;

    final private static String USAGE_TEXT = "Usage:\n" +
            " java -jar ToyShop.jar [command] [options]\n" +
            " Commands:\n" +
            "  list-products ________________________________ Shows all available products in the shop\n" +
            "  ?, /?, -?, -h, --help_________________________ Shows help and usage information\n" +
            "  create-order <id> <count> <deliveryAddress>___ Allows to create an order with parameters: ID-number, count, delivery address\n" +
            "  change-order-count <order-number> <newCount>__ Allows to change the count of products\n" +
            "  commit-order <order-number> __________________ Confirms the order, after this command, changes in the order are not possible.\n";

    final private static String COMMIT_TEXT = "  Your order № %d has been confirmed and submitted for processing.\n" +
            "  Our manager will contact you to clarify the delivery details.\n";
    final private static String ORDER_CHANGE_TEXT = "  Your order № %d was changed.\n" +
            "  Our manager will contact you to confirm the order. \n";
    final private static String ORDER_CHANGE_PARAMETERS_NOT_FILLED = "One or both of following parameters: <order-number> <newCount>, were not filled in. Please try again.\n";
    final private static String ORDER_COMMIT_PARAMETER_NOT_FILLED = "The parameter <order-number> was not filled in. Please try again.\n";
    final private static String ORDER_FILE_TEXT = " Order number - %d\n ID-number -  %d\n Count - %d\n Delivery address -  %s\n";
    final private static String PARAMETERS_NOT_FILLED = "One or more of following parameters: <id> <count> <delivery address>, were not filled in. Please try again.\n";
    final private static String THANKS_TEXT = "  Your order number is № %d.\n" +
            "  Our manager will contact you to confirm the order. \n" +
            "  Thank you and we look forward to seeing you again in our store.\n";
    final private static String WELCOME_TEXT = "Welcome to our online shop";
    final private static String WRONG_ID_NUMBER_TEXT = "The ID-number must include only numbers from 1000 to 9999, please try again.\n";
    final private static String WRONG_COUNT_NUMBER_TEXT = "The count must include only numbers from 1 to 99, please try again.\n";
    final private static String WRONG_ID_NUMBER_FORMAT = "The ID-number must include only numbers, please try again.\n";
    final private static String WRONG_COUNT_NUMBER_FORMAT = "The count must include only numbers, please try again.\n";
    final private static String WRONG_ORDER_NUMBER_TEXT = "The order-number must include only numbers from 1 to 9999, please try again.\n";
    final private static String WRONG_ORDER_NUMBER_FORMAT = "The order-number must include only numbers, please try again.\n";

    public static void main(String... args) {

        if (args.length == 0) {
            String usageTextResult = usageText();
            System.out.println(usageTextResult);
        } else {
            String command = args[0];
            switch (command) {
                case COMMAND_LIST_PRODUCT:
                    String listProductResult = listProducts();
                    System.out.println(listProductResult);
                    break;

                case COMMAND_QUESTION:
                case COMMAND_SLASH_QUESTION:
                case COMMAND_MINUS_QUESTION:
                case COMMAND_MINUS_HELP:
                case COMMAND_MINUS_H:
                    String usageTextResult = usageText();
                    System.out.println(usageTextResult);
                    break;

                case COMMAND_CREATE_ORDER:
                    String orderPlacingResult = orderPlacing(args);
                    System.out.println(orderPlacingResult);
                    break;

                case COMMAND_CHANGE_ORDER_COUNT:
                    String orderChangeResult = orderChange(args);
                    System.out.println(orderChangeResult);
                    break;

                case COMMAND_COMMIT_ORDER:
                    String orderCommitResult = orderCommit(args);
                    System.out.println(orderCommitResult);
                    break;

                default:
                    String otherCasesResult = otherCases();
                    System.out.println(otherCasesResult);
                    break;
            }
        }
    }

    /**
     * метод предоставляет информацию по использованию команд (usage information)
     *
     * @return информацию по использованию команд
     */
    public static String usageText() {
        return USAGE_TEXT;
    }

    /**
     * метод предоставляет перечень всей продукции с описанием и ценами при запросе "list-products" от клиента
     *
     * @return возвращает перечень всей продукции
     */
    public static String listProducts() {

        try {
            final String allProductsList = Files.readString(PATH_OF_LIST_PRODUCTS, UNICODE);
            System.out.println(allProductsList);
            return allProductsList;
        } catch (IOException e) {
            return "Cannot get product register :((";
        }
    }

    /**
     * Метод проверяет,чтобы в строке присутствовали только цифры
     *
     * @param input
     * @return true или false
     */
    private static boolean isNumeric(String input) {
        if (input == null) return true;
        return input.matches("\\d+");
    }

    /**
     * метод для оформления заказа, предусматривает исключение пустых параметров и буквенного ввода в параметры <id> и <count>;
     * учитывает диапазон чисел для <id> и <count>;
     * выдает случайно сгенерированный номер заказа;
     * создает текстовый файл с именем "order-NUMBER.txt", где NUMBER - это сгенерированный ранее номер заказа, и
     * передает все внесенные клиентом параметры в данный текстовый файл;
     *
     * @param args <id> <count> <deliveryAddress>
     * @return текст с подтвердением заказа с указанием номера заказа и благодарностью;
     * @throws FileNotFoundException
     */
    public static String orderPlacing(String[] args) {
        if (args.length == 1 || args.length == 2 || args.length == 3) {
            return PARAMETERS_NOT_FILLED;
        }
        String IDNumberParameter = args[1];
        String countParameter = args[2];
        String deliveryAddress = args[3];
        if (!isNumeric(IDNumberParameter)) {
            return WRONG_ID_NUMBER_FORMAT;
        }
        if (!isNumeric(countParameter)) {
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
                orderFile.close();
            } catch (FileNotFoundException e) {
                return "Cannot save the order :(";
            }
            return String.format(THANKS_TEXT, ORDER_NUMBER);
        }
    }

    /**
     * метод вносит изменения в файл заказа в части количества товара
     * предусматривает исключение пустых параметров и буквенного ввода, учитывает диапазон чисел в параметрах;
     *
     * @param args <order-number> <newCount>
     * @return текст с подтвердением измененного заказа с указанием номера заказа.
     */

    public static String orderChange(String[] args) {
        if (args.length == 1 || args.length == 2) {
            return ORDER_CHANGE_PARAMETERS_NOT_FILLED;
        }
        String orderParameter = args[1];
        String newCountParameter = args[2];
        if (!isNumeric(orderParameter)) {
            return WRONG_ORDER_NUMBER_FORMAT;
        }
        if (!isNumeric(orderParameter)) {
            return WRONG_ORDER_NUMBER_FORMAT;
        }
        int orderNumber = Integer.parseInt(orderParameter);
        int newCount = Integer.parseInt(newCountParameter);

        if (newCount < 0 || newCount > 99) {
            return WRONG_COUNT_NUMBER_TEXT;
        }
        if (orderNumber < 1 || orderNumber > 10000) {
            return WRONG_ORDER_NUMBER_TEXT;
        } else {
            final String OLD_ORDER_FILE_NAME = "order-" + orderNumber + ".txt";
            String ORDER_FILE_NAME = OLD_ORDER_FILE_NAME;
//            String search = "Count";
//            String replace =  newCount;
//          try { PrintWriter orderFile = new PrintWriter(ORDER_FILE_NAME);
//                orderFile.printf(ORDER_FILE_TEXT, ORDER_NUMBER, IDNumber, count, deliveryAddress);
//                orderFile.close();
//                orderFile.close();
//        } catch (FileNotFoundException e) {
//            return "Cannot save the order :(";
        }
        return String.format(ORDER_CHANGE_TEXT, orderNumber);
    }

    /**
     * метод окончательно подтверждает заказ,
     * предусматривает исключение пустого и буквенного ввода, учитывает диапазон чисел в параметре;
     *
     * @param args <order-number>
     * @return текст с окончательным подтвердением заказа с указанием номера заказа.
     */
    public static String orderCommit(String[] args) {
        if (args.length == 1) {
            return ORDER_COMMIT_PARAMETER_NOT_FILLED;
        }
        String orderParameter = args[1];
        if (!isNumeric(orderParameter)) {
            return WRONG_ORDER_NUMBER_FORMAT;
        }
        int orderNumber = Integer.parseInt(orderParameter);
        if (orderNumber < 1 || orderNumber > 10000) {
            return WRONG_ORDER_NUMBER_TEXT;
        } else
            return String.format(COMMIT_TEXT, orderNumber);
    }

    /**
     * метод возвращает приветственное сообщение клиенту при вводе любых значений,
     * кроме предусмотренных в информации по использованию команд (usage information)
     *
     * @return приветственное сообщение
     */
    public static String otherCases() {
        return WELCOME_TEXT;
    }
}