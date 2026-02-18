package ykkz000.mcmod.util.internal;


import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.*;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import ykkz000.mcmod.util.api.CodecFactory;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CodecFactoryImpl implements CodecFactory {
    public static CodecFactoryImpl create() {
        return new CodecFactoryImpl();
    }

    protected CodecFactoryImpl() {
    }

    @Override
    public <T> Codec<T> codec(final Class<T> type) {
        return new Codec<>() {
            @Override
            public <T1> DataResult<Pair<T, T1>> decode(DynamicOps<T1> ops, T1 input) {
                Field[] fields = type.getDeclaredFields();
                Object[] values = new Object[fields.length];
                for (int i = 0; i < fields.length; i++) {
                    Field field = fields[i];
                    int finalI = i;
                    ops.get(input, field.getName()).result().flatMap(rawValue -> readValue(field.getType(), ops, rawValue).result()).ifPresent(value -> values[finalI] = value);
                }
                try {
                    Constructor<T> constructor = type.getDeclaredConstructor(Arrays.stream(fields).map(Field::getType).toArray(Class[]::new));
                    constructor.setAccessible(true);
                    return DataResult.success(Pair.of(constructor.newInstance(values), ops.empty()));
                } catch (InvocationTargetException | InstantiationException | IllegalAccessException |
                         NoSuchMethodException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public <T1> DataResult<T1> encode(T input, DynamicOps<T1> ops, T1 prefix) {
                Field[] fields = type.getDeclaredFields();
                RecordBuilder<T1> outputBuilder = ops.mapBuilder();
                for (Field field : fields) {
                    try {
                        field.setAccessible(true);
                        outputBuilder.add(field.getName(), writeValue(field.getType(), ops, field.get(input)));
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }
                return outputBuilder.build(prefix);
            }
        };
    }

    @Override
    public <T extends Record> StreamCodec<RegistryFriendlyByteBuf, T> streamCodec(final Class<T> type) {
        return new StreamCodec<>() {
            @Override
            public T decode(RegistryFriendlyByteBuf input) {
                Field[] fields = type.getDeclaredFields();
                Object[] values = new Object[fields.length];
                for (int i = 0; i < fields.length; i++) {
                    Field field = fields[i];
                    values[i] = readValueStream(field.getType(), input);
                }
                try {
                    Constructor<T> constructor = type.getDeclaredConstructor(Arrays.stream(fields).map(Field::getType).toArray(Class[]::new));
                    constructor.setAccessible(true);
                    return constructor.newInstance(values);
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                         NoSuchMethodException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void encode(RegistryFriendlyByteBuf output, T value) {
                Field[] fields = type.getDeclaredFields();
                for (Field field : fields) {
                    try {
                        field.setAccessible(true);
                        writeValueStream(field.getType(), output, field.get(value));
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        };
    }

    private <T> DataResult<Object> readValue(Class<?> type, DynamicOps<T> ops, T input) {
        if (type == int.class || type == Integer.class) {
            return ops.getNumberValue(input).map(Number::intValue);
        }
        if (type == byte.class || type == Byte.class) {
            return ops.getNumberValue(input).map(Number::byteValue);
        }
        if (type == boolean.class || type == Boolean.class) {
            return ops.getBooleanValue(input).map(Boolean::booleanValue);
        }
        if (type == short.class || type == Short.class) {
            return ops.getNumberValue(input).map(Number::shortValue);
        }
        if (type == long.class || type == Long.class) {
            return ops.getNumberValue(input).map(Number::longValue);
        }
        if (type == float.class || type == Float.class) {
            return ops.getNumberValue(input).map(Number::floatValue);
        }
        if (type == double.class || type == Double.class) {
            return ops.getNumberValue(input).map(Number::doubleValue);
        }
        if (type.isEnum()) {
            return ops.getNumberValue(input).map(Number::intValue).map(i -> type.getEnumConstants()[i]);
        }
        if (ItemStack.class.isAssignableFrom(type)) {
            return ItemStack.OPTIONAL_CODEC.decode(ops, input).map(Pair::getFirst);
        }
        if (type.isArray()) {
            return ops.getList(input).map(stream -> {
                List<Object> list = new ArrayList<>();
                stream.accept(entry -> readValue(type.getComponentType(), ops, entry).result().ifPresent(list::add));
                return list;
            }).map(list -> {
                Object array = Array.newInstance(type.getComponentType(), list.size());
                for (int i = 0; i < list.size(); i++) {
                    Array.set(array, i, list.get(i));
                }
                return array;
            });
        }
        throw new IllegalArgumentException("Unsupported type: " + type);
    }

    private <T> DataResult<T> writeValue(Class<?> type, DynamicOps<T> ops, Object obj) {
        if (type == int.class || type == Integer.class) {
            return DataResult.success(ops.createInt((Integer) obj));
        }
        if (type == byte.class || type == Byte.class) {
            return DataResult.success(ops.createByte((Byte) obj));
        }
        if (type == boolean.class || type == Boolean.class) {
            return DataResult.success(ops.createBoolean((Boolean) obj));
        }
        if (type == short.class || type == Short.class) {
            return DataResult.success(ops.createShort((Short) obj));
        }
        if (type == long.class || type == Long.class) {
            return DataResult.success(ops.createLong((Long) obj));
        }
        if (type == float.class || type == Float.class) {
            return DataResult.success(ops.createFloat((Float) obj));
        }
        if (type == double.class || type == Double.class) {
            return DataResult.success(ops.createDouble((Double) obj));
        }
        if (type.isEnum()) {
            return DataResult.success(ops.createInt(((Enum<?>) obj).ordinal()));
        }
        if (ItemStack.class.isAssignableFrom(type)) {
            return ItemStack.OPTIONAL_CODEC.encodeStart(ops, (ItemStack) obj);
        }
        if (type.isArray()) {
            ListBuilder<T> listBuilder = ops.listBuilder();
            for (int i = 0; i < Array.getLength(obj); i++) {
                listBuilder.add(writeValue(type.getComponentType(), ops, Array.get(obj, i)));
            }
            return listBuilder.build(ops.empty());
        }
        throw new IllegalArgumentException("Unsupported type: " + type);
    }


    private Object readValueStream(Class<?> type, RegistryFriendlyByteBuf input) {
        if (type == int.class || type == Integer.class) {
            return input.readInt();
        }
        if (type == byte.class || type == Byte.class) {
            return input.readByte();
        }
        if (type == boolean.class || type == Boolean.class) {
            return input.readBoolean();
        }
        if (type == short.class || type == Short.class) {
            return input.readShort();
        }
        if (type == long.class || type == Long.class) {
            return input.readLong();
        }
        if (type == float.class || type == Float.class) {
            return input.readFloat();
        }
        if (type == double.class || type == Double.class) {
            return input.readDouble();
        }
        if (type.isEnum()) {
            return type.getEnumConstants()[input.readInt()];
        }
        if (ItemStack.class.isAssignableFrom(type)) {
            return ItemStack.OPTIONAL_STREAM_CODEC.decode(input);
        }
        if (type.isArray()) {
            int size = input.readInt();
            Object array = Array.newInstance(type.getComponentType(), size);
            for (int i = 0; i < size; i++) {
                Array.set(array, i, readValueStream(type.getComponentType(), input));
            }
            return array;
        }
        throw new IllegalArgumentException("Unsupported type: " + type);
    }

    private <T> void writeValueStream(Class<?> type, RegistryFriendlyByteBuf output, T value) throws IllegalAccessException {
        if (type == int.class || type == Integer.class) {
            output.writeInt((Integer) value);
        } else if (type == byte.class || type == Byte.class) {
            output.writeByte((Byte) value);
        } else if (type == boolean.class || type == Boolean.class) {
            output.writeBoolean((Boolean) value);
        } else if (type == short.class || type == Short.class) {
            output.writeShort((Short) value);
        } else if (type == long.class || type == Long.class) {
            output.writeLong((Long) value);
        } else if (type == float.class || type == Float.class) {
            output.writeFloat((Float) value);
        } else if (type == double.class || type == Double.class) {
            output.writeDouble((Double) value);
        } else if (type.isEnum()) {
            output.writeInt(((Enum<?>) value).ordinal());
        } else if (ItemStack.class.isAssignableFrom(type)) {
            ItemStack.OPTIONAL_STREAM_CODEC.encode(output, (ItemStack) value);
        } else if (type.isArray()) {
            output.writeInt(Array.getLength(value));
            for (int i = 0; i < Array.getLength(value); i++) {
                writeValueStream(type.getComponentType(), output, Array.get(value, i));
            }
        } else {
            throw new IllegalArgumentException("Unsupported type: " + type);
        }
    }
}
