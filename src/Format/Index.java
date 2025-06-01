package Format;

public class Index {
    private  String indexName;

    public Index(String indexName) {
        this.indexName = indexName;
    }
    public String getIndexName() {
        return indexName;
    }

    @Override
    public String toString() {
        return "Index{" +
                "indexName='" + indexName + '\'' +
                '}';
    }
}
