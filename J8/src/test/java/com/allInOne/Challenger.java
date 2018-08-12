package com.allInOne;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.joining;

public class Challenger {

    Trader imran = new Trader("Imran", "Krakow");
    Trader ivan = new Trader("Ivan", "Kiev");
    Trader asotsky = new Trader("Asotsky", "US ");
    Trader dimaRapper = new Trader("Dima Ushakov", "KieV");
    Trader dimaChe = new Trader("Dima Chernaev", "Krakow");
    Trader alena = new Trader("alena", "kiev");
    Trader matt = new Trader("Matt", "us");
    Trader kryven = new Trader("Kryvenkov", "kiev");
    Trader denis = new Trader("Denis", "krakow");
    Trader robert = new Trader("Robert", "serbia");
    Trader mariia = new Trader("Mariia", "Kiev");

    List<Transaction> transactions = Arrays.asList(
            new Transaction(imran, 2017, 990),
            new Transaction(ivan, 2017, 650),
            new Transaction(asotsky, 2018, 300),
            new Transaction(dimaRapper, 2017, 350),
            new Transaction(dimaChe, 2017, 500),
            new Transaction(alena, 2018, 600),
            new Transaction(kryven, 2017, 590),
            new Transaction(denis, 2018, 575),
            new Transaction(matt, 2017, 800),
            new Transaction(robert, 2018, 515),
            new Transaction(mariia, 2017, 550)
    );

    public static void main(String[] args) {
        Challenger ch = new Challenger();
        getAllTraders(ch);
        tradersInKrakow(ch);
        getUniqueCities(ch);
        transactionYearBasis(ch);
        printAllTransactionFromCity(ch, "KieV");
        System.out.println(isCityPresent(ch, "Krakow"));
        System.out.println(transactionWithSmallestValue(ch));
        System.out.println(highestValueOfAllTransaction(ch, "us"));
    }

    public static void getAllTraders(Challenger ch) {
        /**return the list of all traders name sorted alphabetically */
        List<String> traderUniqueNSortedName = ch.transactions.stream().
                map(Transaction -> Transaction.getTrader().getName().toUpperCase()).
                distinct().sorted().collect(Collectors.toList());

        traderUniqueNSortedName.forEach(System.out::println);
        /** But the above is not the answer as its asked us to return the name of all the traders */
        String realAnswer = ch.transactions.stream().
                map(Transaction -> Transaction.getTrader().getName().toUpperCase()).
                distinct().sorted().
                collect(joining());

        System.out.println("realAnswer = " + realAnswer);

        /**what if need to modify the output */

        String outputModification = String.valueOf(ch.transactions.stream().
                map(Transaction -> Transaction.getTrader().getName().toUpperCase()).
                distinct().sorted().
                reduce((n1, n2) -> n1 + " " + n2));

        System.out.println("outputModification = " + outputModification);
    }

    public static void tradersInKrakow(Challenger ch) {
        /**Find all traders from krakow and sort them by name*/

        List<Trader> tradersFromKrakow = ch.transactions.stream().
                map(Transaction::getTrader).
                filter(allTrader -> allTrader.getCity().trim().equalsIgnoreCase("Krakow")).
                distinct().
                collect(Collectors.toList());

        tradersFromKrakow.forEach(System.out::println);
    }

    public static void getUniqueCities(Challenger ch) {
        /**what are all unique cities where traders works*/
        List<String> cities =
                ch.transactions.stream().
                        map(transaction -> transaction.getTrader().getCity().toUpperCase().trim()).
                        distinct().collect(Collectors.toList());

        cities.forEach(System.out::println);
    }

    private static void transactionYearBasis(Challenger ch) {
        /**Find all transaction in the year 2017 and sort them by value << small to high >>  */

        List<Transaction> challange1 =
                ch.transactions.stream().
                        filter(transaction -> transaction.getYear() == 2017).
                        sorted(comparing(Transaction::getValue)).
                        collect(Collectors.toList());

        challange1.forEach(System.out::print);
    }

    private static boolean isCityPresent(Challenger ch, String city) {
        /**Are any trader from US*/
        return ch.transactions.stream().
                anyMatch(transaction -> transaction.getTrader().getCity().equalsIgnoreCase(city));
    }

    private static void printAllTransactionFromCity(Challenger ch, String city) {
        /**get all value of the transaction from the city*/
        ch.transactions.stream().
                filter(transaction -> transaction.getTrader().getCity().equalsIgnoreCase(city))
                .map(transaction -> transaction.getValue())
                .forEach(System.out::println);
    }

    private static int highestValueOfAllTransaction(Challenger ch, String city) {
        return ch.transactions.stream().
                filter(transaction -> transaction.getTrader().getCity().equalsIgnoreCase(city)).
                map(transaction -> transaction.getValue()).
                reduce(Integer::min).orElse(-1);
    }

    private static Optional<Transaction> transactionWithSmallestValue(Challenger ch) {
        return ch.transactions.stream().
                reduce((t1, t2) ->
                        t1.getValue() < t2.getValue() ? t1 : t2);
    }


}
