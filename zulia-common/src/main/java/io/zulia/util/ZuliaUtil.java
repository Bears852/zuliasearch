package io.zulia.util;

import org.bson.BsonBinaryReader;
import org.bson.BsonBinaryWriter;
import org.bson.Document;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.DocumentCodec;
import org.bson.codecs.EncoderContext;
import org.bson.io.BasicOutputBuffer;

import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.Objects;
import java.util.function.Consumer;

public class ZuliaUtil {

	private static final DocumentCodec documentCodec = new DocumentCodec();

	public static void handleLists(Object o, Consumer<? super Object> action) {
		if (o instanceof Collection) {
			Collection<?> c = (Collection<?>) o;
			c.stream().filter(Objects::nonNull).forEach(obj -> {
				if (obj instanceof Collection) {
					handleLists(obj, action);
				}
				else {
					action.accept(obj);
				}
			});
		}
		else if (o instanceof Object[]) {
			Object[] arr = (Object[]) o;
			for (Object obj : arr) {
				if (obj != null) {
					action.accept(action);
				}
			}
		}
		else {
			if (o != null) {
				action.accept(o);
			}
		}
	}

	public static byte[] mongoDocumentToByteArray(Document mongoDocument) {
		BasicOutputBuffer outputBuffer = new BasicOutputBuffer();
		BsonBinaryWriter writer = new BsonBinaryWriter(outputBuffer);
		new DocumentCodec().encode(writer, mongoDocument, EncoderContext.builder().isEncodingCollectibleDocument(true).build());
		return outputBuffer.toByteArray();
	}

	public static Document byteArrayToMongoDocument(byte[] byteArray) {
		BsonBinaryReader bsonReader = new BsonBinaryReader(ByteBuffer.wrap(byteArray));

		return documentCodec.decode(bsonReader, DecoderContext.builder().build());
	}
}
