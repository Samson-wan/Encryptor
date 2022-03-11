public class Main {
    public static void main(String[] args) {
        Encryptor message1 = new Encryptor(5, 3);
        System.out.println(message1.superEncryptMessage("Meet at noon"));

        Encryptor dMessage1 = new Encryptor(4, 2);
        System.out.println(dMessage1.decryptMessage("Hlo el,thsi i sderpecytd AAAAAAA"));
    }
}
