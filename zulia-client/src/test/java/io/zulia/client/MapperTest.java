package io.zulia.client;

import io.zulia.fields.Mapper;
import org.bson.Document;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Set;

public class MapperTest {

	@Test
	public void testSimpleCase() throws Exception {

		Date d = new Date();
		TestObj1 testObj1 = new TestObj1("test", 14, Arrays.asList("1", "2"), Set.of(4, 6, 7), d);

		Mapper<TestObj1> mapper = new Mapper<>(TestObj1.class);

		Document doc = mapper.toDocument(testObj1);

		Assertions.assertEquals("test", doc.getString("field1"));
		Assertions.assertEquals(14, (int) doc.getInteger("field2"));
		Assertions.assertEquals(2, doc.getList("field3", String.class).size());
		Assertions.assertEquals(3, ((Collection<Integer>) doc.get("field4")).size());
		Assertions.assertEquals(doc.getDate("field5"), d);

		TestObj1 testObj1a = mapper.fromDocument(doc);
		Assertions.assertEquals(testObj1a.getField1(), testObj1.getField1());
		Assertions.assertEquals(testObj1a.getField2(), testObj1.getField2());
		Assertions.assertEquals(testObj1a.getField3(), testObj1.getField3());
		Assertions.assertEquals(testObj1a.getField4(), testObj1.getField4());
		Assertions.assertEquals(testObj1a.getField5(), testObj1.getField5());

		System.out.println(doc);

	}

}
