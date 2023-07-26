package org.FAPI.formular;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) {

        try {
            Document doc = Jsoup.connect("https://www.cnb.cz/cs/financni-trhy/devizovy-trh/kurzy-devizoveho-trhu/kurzy-devizoveho-trhu/").get();

            Element table = doc.selectFirst("table");

            Elements rows = table.select("tbody tr");

            Map<String, Double> exchangeRates = new HashMap<>();

            for (Element row : rows) {
                String code = row.selectFirst("td:nth-child(4)").text();
                String rateStr = row.selectFirst("td:nth-child(5)").text();

                double rate = Double.parseDouble(rateStr.replace(",", "."));

                exchangeRates.put(code, rate);
            }

            exchangeRates.compute("PHP", (code, rate) -> rate / 100);
            exchangeRates.compute("INR", (code, rate) -> rate / 100);
            exchangeRates.compute("ISK", (code, rate) -> rate / 100);
            exchangeRates.compute("JPY", (code, rate) -> rate / 100);
            exchangeRates.compute("KRW", (code, rate) -> rate / 100);
            exchangeRates.compute("HUF", (code, rate) -> rate / 100);
            exchangeRates.compute("THB", (code, rate) -> rate / 100);
            exchangeRates.compute("IDR", (code, rate) -> rate / 1000);

            for (Map.Entry<String, Double> entry : exchangeRates.entrySet()) {
                String currency = entry.getKey();
                double rate = entry.getValue();
                System.out.println(currency + " Exchange Rate: " + rate);
            }
/*
            double usdRate = exchangeRates.get("USD");
            System.out.println("To buy 1 USD  you need " + usdRate + "CZK");

            double thbRate = exchangeRates.get("THB");
            System.out.println("To buy 1 THB  you need " + thbRate + "CZK");
*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}