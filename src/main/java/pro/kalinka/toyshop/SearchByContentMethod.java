package pro.kalinka.toyshop;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;

    /**
     * программа получает название файла с реестром и часть текста (например, номер артикула), после чего выводит на экран все продукты, содержащие данный контекст
     */
    public class SearchByContentMethod {

        public void ProductSearchByContent(Path registerPath, String partOfText) throws IOException {
            final String PRODUCT_DELIMITER = "#";
            final String CHARSET = "cp1251";
            final Charset registerCharset = Charset.forName(CHARSET);

            Path pathOfRegister = Path.of(String.valueOf(registerPath));
            String register = Files.readString(pathOfRegister, registerCharset);
            String[] productArray = register.split(PRODUCT_DELIMITER);
            for (int i = 0; i < productArray.length; i = i + 1) {
                String product = productArray[i];
                boolean productMatched = product.contains(partOfText);
                if (productMatched) {
                    System.out.println(product);
                }
            }
        }

        public static void main(String[] args) throws IOException {
           SearchByContentMethod product = new SearchByContentMethod();
            product.ProductSearchByContent(Path.of("C:\\Users\\Alina\\Desktop\\ToyShopReferences\\ProductRegister(Toys).txt"), "ID 1001");
            if (args[0] == "list-products") {
                System.out.println("Here is a list of all avaliable products in the shop: ");
            } else if (args[0] == "/?" || args[0] == "-?" || args[0] == "-h" || args[0] == "--help") {
                System.out.println("Help and usage information");
            }

        }
        }


