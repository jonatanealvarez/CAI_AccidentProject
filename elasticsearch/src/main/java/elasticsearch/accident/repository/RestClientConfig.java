package elasticsearch.accident.repository;

import org.apache.http.HttpHost;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.convert.ElasticsearchCustomConversions;
import org.springframework.expression.ParseException;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Arrays;
import java.util.Date;

@Configuration
public class RestClientConfig {

    @Bean
    public RestHighLevelClient elasticsearchClient() {


        final ClientConfiguration clientConfiguration = ClientConfiguration.builder()
                .connectedTo("localhost:9200").withConnectTimeout(Duration.ofSeconds(200))
                .withSocketTimeout(Duration.ofSeconds(200))
                .build();

        return RestClients.create(clientConfiguration).rest();



/*        RestClientBuilder builder = RestClient.builder(
                new HttpHost("localhost", 9200, "http"));

        return builder;

 */


    }

    @Bean
    public ElasticsearchRestTemplate elasticsearchTemplate() { return new ElasticsearchRestTemplate(elasticsearchClient()); }


}






