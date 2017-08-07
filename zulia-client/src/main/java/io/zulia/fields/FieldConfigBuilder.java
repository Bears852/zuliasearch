package io.zulia.fields;

import java.util.ArrayList;
import java.util.List;

import static io.zulia.message.ZuliaIndex.FacetAs;
import static io.zulia.message.ZuliaIndex.FieldConfig;
import static io.zulia.message.ZuliaIndex.IndexAs;
import static io.zulia.message.ZuliaIndex.ProjectAs;
import static io.zulia.message.ZuliaIndex.SortAs;
import static io.zulia.message.ZuliaIndex.Superbit;

public class FieldConfigBuilder {
	private final FieldConfig.FieldType fieldType;
	private String storedFieldName;
	private List<IndexAs> indexAsList;
	private List<FacetAs> facetAsList;
	private List<SortAs> sortAsList;
	private List<ProjectAs> projectAsList;

	public FieldConfigBuilder(String storedFieldName, FieldConfig.FieldType fieldType) {
		this.storedFieldName = storedFieldName;
		this.fieldType = fieldType;
		this.indexAsList = new ArrayList<>();
		this.facetAsList = new ArrayList<>();
		this.sortAsList = new ArrayList<>();
		this.projectAsList = new ArrayList<>();
	}

	public static FieldConfigBuilder create(String storedFieldName, FieldConfig.FieldType fieldType) {
		return new FieldConfigBuilder(storedFieldName, fieldType);
	}

	public FieldConfigBuilder index() {
		return indexAs(null, storedFieldName);
	}

	public FieldConfigBuilder indexAs(String analyzerName) {
		return indexAs(analyzerName, storedFieldName);
	}

	public FieldConfigBuilder indexAs(String analyzerName, String indexedFieldName) {

		IndexAs.Builder builder = IndexAs.newBuilder();
		builder.setIndexFieldName(indexedFieldName);
		if (analyzerName != null) {
			builder.setAnalyzerName(analyzerName);
		}
		return indexAs(builder.build());
	}

	public FieldConfigBuilder indexAs(IndexAs indexAs) {
		this.indexAsList.add(indexAs);
		return this;
	}

	public FieldConfigBuilder facet() {
		return facetAs(null, storedFieldName);
	}

	public FieldConfigBuilder facetAs(String facetName) {
		return facetAs(null, facetName);
	}

	public FieldConfigBuilder facetAs(FacetAs.DateHandling dateHandling) {
		return facetAs(dateHandling, storedFieldName);
	}

	public FieldConfigBuilder facetAs(FacetAs.DateHandling dateHandling, String facetName) {
		FacetAs.Builder builder = FacetAs.newBuilder().setFacetName(facetName);
		if (dateHandling != null) {
			builder.setDateHandling(dateHandling);
		}
		return facetAs(builder.build());
	}

	public FieldConfigBuilder facetAs(FacetAs facetAs) {
		this.facetAsList.add(facetAs);
		return this;
	}

	public FieldConfigBuilder sort() {
		return sortAs(null, storedFieldName);
	}

	public FieldConfigBuilder sortAs(SortAs.StringHandling stringHandling) {
		return sortAs(stringHandling, storedFieldName);
	}

	public FieldConfigBuilder sortAs(SortAs.StringHandling stringHandling, String sortFieldName) {
		SortAs.Builder builder = SortAs.newBuilder().setSortFieldName(sortFieldName);
		if (stringHandling != null) {
			builder.setStringHandling(stringHandling);
		}
		return sortAs(builder.build());
	}

	public FieldConfigBuilder sortAs(SortAs sortAs) {
		this.sortAsList.add(sortAs);
		return this;
	}

	public FieldConfigBuilder projectAsSuperBit(String field, int inputDim) {
		Superbit superbit = Superbit.newBuilder().setInputDim(inputDim).build();
		ProjectAs projectAs = ProjectAs.newBuilder().setField(field).setSuperbit(superbit).build();
		return projectAs(projectAs);
	}

	public FieldConfigBuilder projectAsSuperBit(String field, int inputDim, int batches) {
		Superbit superbit = Superbit.newBuilder().setInputDim(inputDim).setBatches(batches).build();
		ProjectAs projectAs = ProjectAs.newBuilder().setField(field).setSuperbit(superbit).build();
		return projectAs(projectAs);
	}

	public FieldConfigBuilder projectAsSuperBit(String field, int inputDim, int batches, int seed) {
		Superbit superbit = Superbit.newBuilder().setInputDim(inputDim).setBatches(batches).setSeed(seed).build();
		ProjectAs projectAs = ProjectAs.newBuilder().setField(field).setSuperbit(superbit).build();
		return projectAs(projectAs);
	}

	public FieldConfigBuilder projectAsSuperBit(String field, Superbit superbit) {
		ProjectAs projectAs = ProjectAs.newBuilder().setField(field).setSuperbit(superbit).build();
		return projectAs(projectAs);
	}

	public FieldConfigBuilder projectAs(ProjectAs projectAs) {
		this.projectAsList.add(projectAs);
		return this;
	}

	public FieldConfig build() {
		FieldConfig.Builder fcBuilder = FieldConfig.newBuilder();
		fcBuilder.setStoredFieldName(storedFieldName);
		fcBuilder.setFieldType(fieldType);
		fcBuilder.addAllIndexAs(indexAsList);
		fcBuilder.addAllFacetAs(facetAsList);
		fcBuilder.addAllSortAs(sortAsList);
		fcBuilder.addAllProjectAs(projectAsList);

		return fcBuilder.build();
	}
}
