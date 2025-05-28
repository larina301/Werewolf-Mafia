import java.io.*;
import java.net.*;


class GptClient {
    public static String Request(String prompt) {
        try {
            URL url = new URL("https://api.openai.com/v1/responses");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            httpURLConnection.setRequestProperty("Authorization", "Bearer " + System.getenv("OPENAI_API_KEY"));

            String json = """
            {
                "model": "gpt-4o-mini",
                "temperature": 1,
                "max_output_tokens": 100,
                "top_p": 1,
                "store": true,
                "age": 30,
                "text": {
                    "format": {
                        "type": "text"
                    }
                },
                "input": [
                    {
                        "role": "user",
                        "content": [
                            {
                                "type": "input_text",
                                "text": """ + prompt + """
                            }
                        ]
                    }
                ]
            }
            """;

            httpURLConnection.setDoOutput(true);
            try (OutputStream os = httpURLConnection.getOutputStream()) {
                byte[] input = json.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int responseCode = httpURLConnection.getResponseCode();
            System.out.println("Response Code: " + responseCode);


            BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            System.out.println(response.toString());
            return response.toString();

        } catch (MalformedURLException e) {
            System.err.println("Malformed URL: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("IO Error: " + e.getMessage());
        }
        return "";
    }
}