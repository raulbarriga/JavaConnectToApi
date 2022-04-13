import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

// it works!
// fetches data from api
// then gets the desired value from the json response
// (requires a lot of code to get the desired results in its desired data type)
public class ConnectToApi {
    public static int callingCode(String countryName) {
        int myNum = 0;

        try {
            URL url = new URL("https://jsonmock.hackerrank.com/api/countries?name=" + countryName);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            // check if connection is made
            int responseCode = conn.getResponseCode();

            // 200 success
            if (responseCode != 200) {
                throw new RuntimeException("HttpResponseCode: " + responseCode);
            }

            StringBuilder infoString = new StringBuilder();
            Scanner scanner2 = new Scanner(url.openStream());

            while (scanner2.hasNext()) {
                infoString.append(scanner2.nextLine());
            }
            // close the scanner
            scanner2.close();

            System.out.println("infoString: " + infoString);
            // convert strings to JSON
            JSONParser parse = new JSONParser();
            // dataArr is wrapped in an array
            JSONObject dataArr = (JSONObject) parse.parse(String.valueOf(infoString));
            // remove the array brackets
            JSONArray dataObj = (JSONArray) dataArr.get("data");

            // if no data in json data array response
            if (dataObj.isEmpty()) return 0;

            System.out.println("dataArr:\n "+ dataObj.get(0));
            JSONObject desiredResult = (JSONObject) dataObj.get(0); // the code is wrapped in an array
            System.out.println("callingCodes:\n "+ desiredResult.get("callingCodes"));
            // remove array brackets
            JSONArray answer = (JSONArray) desiredResult.get("callingCodes");
            System.out.println("final answer:\n "+ answer.get(0));

            myNum = Integer.parseInt(answer.get(0).toString()); // answer is finally an integer!
            System.out.println("myNum:\n "+ myNum);
            System.out.println(myNum + 1); // manipulate integer now with other numbers

        } catch(Exception e) {
            e.printStackTrace();
        }
        return myNum;
    }
    public static void main(String[] args) {
        // https://jsonmock.hackerrank.com/api/countries?name=<Country>
        // e.g. https://jsonmock.hackerrank.com/api/countries?name=Afghanistan

        Scanner scanner = new Scanner(System.in);
        System.out.println("Which country?");
        String country = scanner.nextLine();
        int code = callingCode(country);
        if (code == 0) {
            System.out.println("No calling code for that country.");
        }

//        System.out.println(code);
    }
}
