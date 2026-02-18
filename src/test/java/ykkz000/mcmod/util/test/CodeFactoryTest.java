package ykkz000.mcmod.util.test;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Pair;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import io.netty.buffer.PooledByteBufAllocator;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import org.jspecify.annotations.NonNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ykkz000.mcmod.util.api.UtilsApi;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

public class CodeFactoryTest {
    record TestRecord(int a, byte b, boolean c, short d, long e, float f, double g, Integer h, Byte i, Boolean j,
                      Short k, Long l, Float m, Double n, int[] o, byte[] p) {
    }

    private static Codec<TestRecord> codec = null;
    private static StreamCodec<RegistryFriendlyByteBuf, TestRecord> streamCodec = null;

    @BeforeAll
    protected static void setup() {
        codec = UtilsApi.codecFactory().codec(TestRecord.class);
        streamCodec = UtilsApi.codecFactory().streamCodec(TestRecord.class);
    }

    @Test
    protected void testCodecEncode() {
        JsonObject jsonObject = new JsonObject();
        DataResult<JsonElement> result = codec.encode(new TestRecord(1, (byte) 2, true, (short) 3, 4L, 5F, 6.0, 7, (byte) 8, true, (short) 9, 10L, 11F, 12.0, new int[]{1, 2, 3}, new byte[]{4, 5, 6}), JsonOps.INSTANCE, jsonObject);
        LogUtils.getLogger().info(jsonObject.toString());
        Assertions.assertTrue(result.isSuccess());
        AtomicReference<JsonObject> newJsonObject = new AtomicReference<>();
        Assertions.assertDoesNotThrow(() -> newJsonObject.set(result.getOrThrow().getAsJsonObject()));
        Assertions.assertEquals(1, newJsonObject.get().get("a").getAsInt());
        Assertions.assertEquals((byte) 2, newJsonObject.get().get("b").getAsByte());
        Assertions.assertTrue(newJsonObject.get().get("c").getAsBoolean());
        Assertions.assertEquals((short) 3, newJsonObject.get().get("d").getAsShort());
        Assertions.assertEquals(4L, newJsonObject.get().get("e").getAsLong());
        Assertions.assertEquals(5F, newJsonObject.get().get("f").getAsFloat());
        Assertions.assertEquals(6.0, newJsonObject.get().get("g").getAsDouble());
        Assertions.assertEquals(7, newJsonObject.get().get("h").getAsInt());
        Assertions.assertEquals((byte) 8, newJsonObject.get().get("i").getAsByte());
        Assertions.assertTrue(newJsonObject.get().get("j").getAsBoolean());
        Assertions.assertEquals((short) 9, newJsonObject.get().get("k").getAsShort());
        Assertions.assertEquals(10L, newJsonObject.get().get("l").getAsLong());
        Assertions.assertEquals(11F, newJsonObject.get().get("m").getAsFloat());
        Assertions.assertEquals(12.0, newJsonObject.get().get("n").getAsDouble());
        Assertions.assertEquals(3, newJsonObject.get().getAsJsonArray("o").size());
        Assertions.assertEquals(1, newJsonObject.get().getAsJsonArray("o").get(0).getAsInt());
        Assertions.assertEquals(2, newJsonObject.get().getAsJsonArray("o").get(1).getAsInt());
        Assertions.assertEquals(3, newJsonObject.get().getAsJsonArray("o").get(2).getAsInt());
        Assertions.assertEquals(3, newJsonObject.get().getAsJsonArray("p").size());
        Assertions.assertEquals(4, newJsonObject.get().getAsJsonArray("p").get(0).getAsInt());
        Assertions.assertEquals(5, newJsonObject.get().getAsJsonArray("p").get(1).getAsInt());
        Assertions.assertEquals(6, newJsonObject.get().getAsJsonArray("p").get(2).getAsInt());
    }

    @Test
    protected void testCodecDecode() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("a", 1);
        jsonObject.addProperty("b", (byte) 2);
        jsonObject.addProperty("c", true);
        jsonObject.addProperty("d", (short) 3);
        jsonObject.addProperty("e", 4L);
        jsonObject.addProperty("f", 5F);
        jsonObject.addProperty("g", 6.0);
        jsonObject.addProperty("h", 7);
        jsonObject.addProperty("i", (byte) 8);
        jsonObject.addProperty("j", true);
        jsonObject.addProperty("k", (short) 9);
        jsonObject.addProperty("l", 10L);
        jsonObject.addProperty("m", 11F);
        jsonObject.addProperty("n", 12.0);
        JsonArray o = new JsonArray();
        o.add(1);
        o.add(2);
        o.add(3);
        jsonObject.add("o", o);
        JsonArray p = new JsonArray();
        p.add(4);
        p.add(5);
        p.add(6);
        jsonObject.add("p", p);
        DataResult<Pair<TestRecord, JsonElement>> recordResult = codec.decode(JsonOps.INSTANCE, jsonObject);
        Assertions.assertTrue(recordResult.isSuccess());
        Assertions.assertEquals(1, recordResult.map(Pair::getFirst).mapOrElse(TestRecord::a, _->0).intValue());
        Assertions.assertEquals((byte) 2, recordResult.map(Pair::getFirst).mapOrElse(TestRecord::b, _->0).byteValue());
        Assertions.assertTrue(recordResult.map(Pair::getFirst).mapOrElse(TestRecord::c, _->false));
        Assertions.assertEquals((short) 3, recordResult.map(Pair::getFirst).mapOrElse(TestRecord::d, _->0).shortValue());
        Assertions.assertEquals(4L, recordResult.map(Pair::getFirst).mapOrElse(TestRecord::e, _->0L).longValue());
        Assertions.assertEquals(5F, recordResult.map(Pair::getFirst).mapOrElse(TestRecord::f, _->0F).floatValue());
        Assertions.assertEquals(6.0, recordResult.map(Pair::getFirst).mapOrElse(TestRecord::g, _->0.0).doubleValue());
        Assertions.assertEquals(7, recordResult.map(Pair::getFirst).mapOrElse(TestRecord::h, _->0).intValue());
        Assertions.assertEquals((byte) 8, recordResult.map(Pair::getFirst).mapOrElse(TestRecord::i, _->0).byteValue());
        Assertions.assertTrue(recordResult.map(Pair::getFirst).mapOrElse(TestRecord::j, _->false));
        Assertions.assertEquals((short) 9, recordResult.map(Pair::getFirst).mapOrElse(TestRecord::k, _->0).shortValue());
        Assertions.assertEquals(10L, recordResult.map(Pair::getFirst).mapOrElse(TestRecord::l, _->0L).longValue());
        Assertions.assertEquals(11F, recordResult.map(Pair::getFirst).mapOrElse(TestRecord::m, _->0F).floatValue());
        Assertions.assertEquals(12.0, recordResult.map(Pair::getFirst).mapOrElse(TestRecord::n, _->0.0).doubleValue());
        Assertions.assertEquals(3, recordResult.map(Pair::getFirst).mapOrElse(TestRecord::o, _->new int[0]).length);
        Assertions.assertEquals(1, recordResult.map(Pair::getFirst).mapOrElse(TestRecord::o, _->new int[0])[0]);
        Assertions.assertEquals(2, recordResult.map(Pair::getFirst).mapOrElse(TestRecord::o, _->new int[0])[1]);
        Assertions.assertEquals(3, recordResult.map(Pair::getFirst).mapOrElse(TestRecord::o, _->new int[0])[2]);
        Assertions.assertEquals(3, recordResult.map(Pair::getFirst).mapOrElse(TestRecord::p, _->new byte[0]).length);
        Assertions.assertEquals(4, recordResult.map(Pair::getFirst).mapOrElse(TestRecord::p, _->new byte[0])[0]);
        Assertions.assertEquals(5, recordResult.map(Pair::getFirst).mapOrElse(TestRecord::p, _->new byte[0])[1]);
        Assertions.assertEquals(6, recordResult.map(Pair::getFirst).mapOrElse(TestRecord::p, _->new byte[0])[2]);
    }

    @Test
    protected void testStreamCodec() {
        TestRecord source = new TestRecord(1, (byte) 2, true, (short) 3, 4L, 5F, 6.0, 7, (byte) 8, true, (short) 9, 10L, 11F, 12.0, new int[]{1, 2, 3}, new byte[]{4, 5, 6});
        RegistryFriendlyByteBuf buf = new RegistryFriendlyByteBuf(PooledByteBufAllocator.DEFAULT.buffer(), new RegistryAccess.Frozen() {
            @Override
            public <E> @NonNull Optional<Registry<E>> lookup(@NonNull ResourceKey<? extends Registry<? extends E>> registryKey) {
                return Optional.empty();
            }

            @Override
            public @NonNull Stream<RegistryEntry<?>> registries() {
                return Stream.empty();
            }
        });
        streamCodec.encode(buf, source);
        TestRecord result = streamCodec.decode(buf);
        Assertions.assertEquals(1, result.a());
        Assertions.assertEquals((byte) 2, result.b());
        Assertions.assertTrue(result.c());
        Assertions.assertEquals((short) 3, result.d());
        Assertions.assertEquals(4L, result.e());
        Assertions.assertEquals(5F, result.f());
        Assertions.assertEquals(6.0, result.g());
        Assertions.assertEquals(7, result.h().intValue());
        Assertions.assertEquals((byte) 8, result.i().byteValue());
        Assertions.assertTrue(result.j());
        Assertions.assertEquals((short) 9, result.k().shortValue());
        Assertions.assertEquals(10L, result.l().longValue());
        Assertions.assertEquals(11F, result.m().floatValue());
        Assertions.assertEquals(12.0, result.n().doubleValue());
        Assertions.assertEquals(3, result.o().length);
        Assertions.assertEquals(1, result.o()[0]);
        Assertions.assertEquals(2, result.o()[1]);
        Assertions.assertEquals(3, result.o()[2]);
        Assertions.assertEquals(3, result.p().length);
        Assertions.assertEquals(4, result.p()[0]);
        Assertions.assertEquals(5, result.p()[1]);
        Assertions.assertEquals(6, result.p()[2]);
    }
}
