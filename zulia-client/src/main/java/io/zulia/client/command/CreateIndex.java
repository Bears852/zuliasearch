package io.zulia.client.command;

import org.lumongo.client.command.base.SimpleCommand;
import org.lumongo.client.config.IndexConfig;
import org.lumongo.client.pool.LumongoConnection;
import org.lumongo.client.result.CreateIndexResult;
import org.lumongo.cluster.message.ExternalServiceGrpc;
import org.lumongo.cluster.message.Lumongo.IndexCreateRequest;
import org.lumongo.cluster.message.Lumongo.IndexCreateResponse;

/**
 * Creates a new index with the name, number of segments, unique id field, and IndexSettings given.  Whether the index supports faceting
 * or not is also configurable.  However, only the IndexConfig cannot be changed after the index is created.  If index already exists an exception will be thrown.
 * See @CreateOrUpdateIndex
 *
 * @author mdavis
 *
 */
public class CreateIndex extends SimpleCommand<IndexCreateRequest, CreateIndexResult> {

	private String indexName;
	private Integer numberOfSegments;
	private IndexConfig indexConfig;

	public CreateIndex(String indexName, Integer numberOfSegments, IndexConfig indexConfig) {
		this.indexName = indexName;
		this.numberOfSegments = numberOfSegments;
		this.indexConfig = indexConfig;
	}

	@Override
	public IndexCreateRequest getRequest() {
		IndexCreateRequest.Builder indexCreateRequestBuilder = IndexCreateRequest.newBuilder();

		if (indexName != null) {
			indexCreateRequestBuilder.setIndexName(indexName);
		}

		if (numberOfSegments != null) {
			indexCreateRequestBuilder.setNumberOfSegments(numberOfSegments);
		}

		if (indexConfig != null) {
			indexCreateRequestBuilder.setIndexSettings(indexConfig.getIndexSettings());
		}

		return indexCreateRequestBuilder.build();
	}

	@Override
	public CreateIndexResult execute(LumongoConnection lumongoConnection) {
		ExternalServiceGrpc.ExternalServiceBlockingStub service = lumongoConnection.getService();
		IndexCreateResponse indexCreateResponse = service.createIndex(getRequest());

		return new CreateIndexResult(indexCreateResponse);
	}

}
