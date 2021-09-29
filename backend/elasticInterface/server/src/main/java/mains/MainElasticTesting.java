package mains;

import com.entonomachia.server.ElasticRequestHandler;
import com.entonomachia.unmarshalling.DocumentUnmarshalled;
import com.entonomachia.unmarshalling.JsonHandler;

public class MainElasticTesting {

	public static void main(String[] args) {
		try {
			String responseBody = ElasticRequestHandler.GetRequest("/primarydirect/",
					"{\n"
					+ "  \"query\": {\n"
					+ "    \"bool\": {\n"
					+ "      \"must\": [\n"
					+ "        { \"match\": { \"labelError\": 1.0 } }\n"
					+ "      ],\n"
					+ "      \"must_not\": [\n"
					+ "        { \"match\": { \"ids\": 10 } }\n"
					+ "      ]\n"
					+ "    }\n"
					+ "  }\n"
					+ "}");
			
			//System.out.println(responseBody);
			
			DocumentUnmarshalled document = JsonHandler.jsonToDocument(responseBody);
			
			System.out.println(document.getHits().getHits().get(0).getSource());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Exception in main");
			e.printStackTrace();
		}
        
		
	}

}
