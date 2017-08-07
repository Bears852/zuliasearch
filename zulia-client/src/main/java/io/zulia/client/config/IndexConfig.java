package io.zulia.client.config;

import io.zulia.fields.FieldConfigBuilder;

import java.util.TreeMap;

import static io.zulia.message.ZuliaBase.Similarity;
import static io.zulia.message.ZuliaIndex.AnalyzerSettings;
import static io.zulia.message.ZuliaIndex.FieldConfig;
import static io.zulia.message.ZuliaIndex.IndexSettings;

public class IndexConfig {

	private String defaultSearchField;
	private Double requestFactor;
	private Integer minSegmentRequest;
	private Integer numberOfSegments;
	private String indexName;
	private Integer idleTimeWithoutCommit;
	private Integer segmentCommitInterval;
	private Double segmentTolerance;
	private Integer segmentQueryCacheSize;
	private Integer segmentQueryCacheMaxAmount;
	private Boolean storeDocumentInMongo;
	private Boolean storeDocumentInIndex;
	private Boolean storeIndexOnDisk;

	private TreeMap<String, FieldConfig> fieldMap;
	private TreeMap<String, AnalyzerSettings> analyzerSettingsMap;

	public IndexConfig() {
		this(null);
	}

	public IndexConfig(String defaultSearchField) {
		this.defaultSearchField = defaultSearchField;
		this.fieldMap = new TreeMap<>();
		this.analyzerSettingsMap = new TreeMap<>();
	}

	public String getDefaultSearchField() {
		return defaultSearchField;
	}

	public IndexConfig setDefaultSearchField(String defaultSearchField) {
		this.defaultSearchField = defaultSearchField;
		return this;
	}

	public double getRequestFactor() {
		return requestFactor;
	}

	public IndexConfig setRequestFactor(double requestFactor) {
		this.requestFactor = requestFactor;
		return this;
	}

	public int getMinSegmentRequest() {
		return minSegmentRequest;
	}

	public IndexConfig setMinSegmentRequest(int minSegmentRequest) {
		this.minSegmentRequest = minSegmentRequest;
		return this;
	}

	public int getNumberOfSegments() {
		return numberOfSegments;
	}

	public IndexConfig setNumberOfSegments(int numberOfSegments) {
		this.numberOfSegments = numberOfSegments;
		return this;
	}

	public String getIndexName() {
		return indexName;
	}

	public IndexConfig setIndexName(String indexName) {
		this.indexName = indexName;
		return this;
	}

	public int getIdleTimeWithoutCommit() {
		return idleTimeWithoutCommit;
	}

	public IndexConfig setIdleTimeWithoutCommit(int idleTimeWithoutCommit) {
		this.idleTimeWithoutCommit = idleTimeWithoutCommit;
		return this;
	}

	public int getSegmentCommitInterval() {
		return segmentCommitInterval;
	}

	public IndexConfig setSegmentCommitInterval(int segmentCommitInterval) {
		this.segmentCommitInterval = segmentCommitInterval;
		return this;
	}

	public double getSegmentTolerance() {
		return segmentTolerance;
	}

	public IndexConfig setSegmentTolerance(double segmentTolerance) {
		this.segmentTolerance = segmentTolerance;
		return this;
	}

	public Integer getSegmentQueryCacheSize() {
		return segmentQueryCacheSize;
	}

	public void setSegmentQueryCacheSize(Integer segmentQueryCacheSize) {
		this.segmentQueryCacheSize = segmentQueryCacheSize;
	}

	public Integer getSegmentQueryCacheMaxAmount() {
		return segmentQueryCacheMaxAmount;
	}

	public void setSegmentQueryCacheMaxAmount(Integer segmentQueryCacheMaxAmount) {
		this.segmentQueryCacheMaxAmount = segmentQueryCacheMaxAmount;
	}

	public void addFieldConfig(FieldConfigBuilder FieldConfigBuilder) {
		addFieldConfig(FieldConfigBuilder.build());
	}

	public void addFieldConfig(FieldConfig fieldConfig) {
		this.fieldMap.put(fieldConfig.getStoredFieldName(), fieldConfig);
	}

	public FieldConfig getFieldConfig(String fieldName) {
		return this.fieldMap.get(fieldName);
	}

	public void addAnalyzerSetting(String name, AnalyzerSettings.Tokenizer tokenizer, Iterable<AnalyzerSettings.Filter> filterList, Similarity similarity) {

		AnalyzerSettings.Builder analyzerSettings = AnalyzerSettings.newBuilder();
		analyzerSettings.setName(name);
		if (tokenizer != null) {
			analyzerSettings.setTokenizer(tokenizer);
		}
		if (filterList != null) {
			analyzerSettings.addAllFilter(filterList);
		}
		if (similarity != null) {
			analyzerSettings.setSimilarity(similarity);
		}

		addAnalyzerSetting(analyzerSettings.build());
	}

	public void addAnalyzerSetting(AnalyzerSettings analyzerSettings) {
		analyzerSettingsMap.put(analyzerSettings.getName(), analyzerSettings);
	}

	public TreeMap<String, FieldConfig> getFieldConfigMap() {
		return fieldMap;
	}

	public Boolean getStoreDocumentInMongo() {
		return storeDocumentInMongo;
	}

	public void setStoreDocumentInMongo(Boolean storeDocumentInMongo) {
		this.storeDocumentInMongo = storeDocumentInMongo;
	}

	public Boolean getStoreDocumentInIndex() {
		return storeDocumentInIndex;
	}

	public void setStoreDocumentInIndex(Boolean storeDocumentInIndex) {
		this.storeDocumentInIndex = storeDocumentInIndex;
	}

	public Boolean getStoreIndexOnDisk() {
		return storeIndexOnDisk;
	}

	public void setStoreIndexOnDisk(Boolean storeIndexOnDisk) {
		this.storeIndexOnDisk = storeIndexOnDisk;
	}

	public IndexSettings getIndexSettings() {
		IndexSettings.Builder isb = IndexSettings.newBuilder();
		if (defaultSearchField != null) {
			isb.setDefaultSearchField(defaultSearchField);
		}
		if (requestFactor != null) {
			isb.setRequestFactor(requestFactor);
		}
		if (minSegmentRequest != null) {
			isb.setMinSegmentRequest(minSegmentRequest);
		}

		if (segmentCommitInterval != null) {
			isb.setSegmentCommitInterval(segmentCommitInterval);
		}
		if (idleTimeWithoutCommit != null) {
			isb.setIdleTimeWithoutCommit(idleTimeWithoutCommit);
		}
		if (segmentTolerance != null) {
			isb.setSegmentTolerance(segmentTolerance);
		}

		for (String fieldName : fieldMap.keySet()) {
			FieldConfig fieldConfig = fieldMap.get(fieldName);
			isb.addFieldConfig(fieldConfig);
		}

		for (String analyzerName : analyzerSettingsMap.keySet()) {
			isb.addAnalyzerSettings(analyzerSettingsMap.get(analyzerName));
		}

		return isb.build();
	}

	public void configure(IndexSettings indexSettings) {
		this.defaultSearchField = indexSettings.getDefaultSearchField();
		this.requestFactor = indexSettings.getRequestFactor();
		this.minSegmentRequest = indexSettings.getMinSegmentRequest();
		this.segmentCommitInterval = indexSettings.getSegmentCommitInterval();
		this.idleTimeWithoutCommit = indexSettings.getIdleTimeWithoutCommit();
		this.segmentTolerance = indexSettings.getSegmentTolerance();
		this.fieldMap = new TreeMap<>();

		for (FieldConfig fc : indexSettings.getFieldConfigList()) {
			fieldMap.put(fc.getStoredFieldName(), fc);
		}

	}

	public static IndexConfig fromIndexSettings(IndexSettings indexSettings) {
		IndexConfig ic = new IndexConfig();
		ic.configure(indexSettings);
		return ic;
	}

}
