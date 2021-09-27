package mains;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import com.entonomachia.server.QueryResult;

public class MainElasticTesting {

	public static void main(String[] args) {
		try {
			RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(
							new HttpHost("localhost", 9200, "http")));
			QueryResult result = new QueryResult();
			SearchSourceBuilder sourceBuilder = new SearchSourceBuilder(); 
			sourceBuilder.query(QueryBuilders.termQuery("user", "MACHINA")); 
			sourceBuilder.from(0); 
			sourceBuilder.size(5); //returns 5 hits max
			sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS)); 
			SearchRequest request = new SearchRequest("primarydirect");
			request.source(sourceBuilder);
		
			SearchResponse queryResult = client.search(request, RequestOptions.DEFAULT);
			SearchHit[] searchHits = queryResult.getHits().getHits();
	        for (SearchHit hit : searchHits) {
	            result.addDocument(hit);
	        }
	        System.out.println(result);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Exception in main");
			e.printStackTrace();
		}
        
		
	}

}
