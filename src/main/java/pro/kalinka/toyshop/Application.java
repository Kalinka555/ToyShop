package pro.kalinka.toyshop;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class Application {
    final private static String ProductRegister = "C:\\Users\\Alina\\IdeaProjects\\ToyShop\\src\\main\\resources\\ProductRegister(Toys).txt";
    final private static Charset CHARSET = StandardCharsets.UTF_8;
    final private static Path PATH = Path.of(ProductRegister);
    final private static Scanner CUSTOMER = new Scanner(System.in);

    final private static String COMMAND_QUESTION = "?";
    final private static String COMMAND_SLASH_QUESTION = "/?";
    final private static String COMMAND_MINUS_QUESTION = "-?";
    final private static String COMMAND_MINUS_H = "-h";
    final private static String COMMAND_MINUS_HELP = "--help";
    final private static String COMMAND_LIST_PRODUCT = "list-products";
    final private static String COMMAND_ORDER = "create-order";

    final private static String WELCOME_TEXT = "Welcome to our online shop\n";
    final private static String USAGE_TEXT = "Usage:\n" +
            "  java -jar ToyShop.jar [command] [options]\n" +
            "  Commands:\n" +
            "    list-products            Shows all available products in the shop\n" +
            "    ?, /?, -?, -h, --help    Shows help and usage information\n" +
            "    create-order             Allows you to create an order with the following parameters: ID-number, quantity, delivery address ";
    final private static String ORDERING_ID_NUMBER_TEXT = "Enter the ID number of the product:\n";
    final private static String ORDERING_QUANTITY_TEXT = "Enter the quantity of the product:\n";
    final private static String ORDERING_DELIVERY_ADDRESS = "Enter the delivery address:\n";
    final private static String ORDER_NUMBER_TEXT = "Your order number is: %d"; // + ORDER_NUMBER;
    final private static int ORDER_NUMBER = 0; //Create random method!!!

    public static void main(String[] args) throws IOException {

        final String allProductList = Files.readString(PATH, CHARSET);

        if (args.length == 0) {
            System.out.println(USAGE_TEXT);
        } else {
            String arg = args[0];
            if (arg.equals(COMMAND_LIST_PRODUCT)) {
                System.out.println(allProductList);
            } else if (arg.equals(COMMAND_QUESTION) || arg.equals(COMMAND_SLASH_QUESTION) || arg.equals(COMMAND_MINUS_QUESTION)
                    || arg.equals(COMMAND_MINUS_HELP) || arg.equals(COMMAND_MINUS_H)) {
                System.out.println(USAGE_TEXT);
            } else if (arg.equals(COMMAND_ORDER)) {
                System.out.println(ORDERING_ID_NUMBER_TEXT);
                CUSTOMER.nextInt();
                // if(){ //написать условие, чтобы цифра была от 1 до 999
                //}
                System.out.println(ORDERING_QUANTITY_TEXT);
                CUSTOMER.nextInt();
                // if(){ //написать условие, чтобы цифра была от 1 до 999
                //}
                System.out.println(ORDERING_DELIVERY_ADDRESS);
                CUSTOMER.nextInt();
                // if(){ //написать условие, чтобы в адресе были индекс из 6 цифр, город, улица, номер дома, номер квартиры
                //}
                System.out.println();

            } else System.out.println(WELCOME_TEXT);
        }
    }
}
//Пользователь ToyShop должен иметь возможность внеcти информацию о желаемой покупке.
//
//Для этого надо предусмотреть команду "create-order" со следующими опциями:
//
//артикул (ID) товара
//количество
//адрес доставки
//Программа должна сгенерировать случайный номер заказа от 1 до 9999.
//Информацию о созданном заказе программа должна сохранить в виде файла в текущей папке (cd), название файла формата order-{order_id}.txt, где {order_id} - это сгенерированный номер заказа.
//В ответ на команду программа должна его подтвердить и поблагодарить за заказ.

//Не забудь обновить help новой командой

//Да, и ещё программа должна показать пользователю номер заказа, конечно же.