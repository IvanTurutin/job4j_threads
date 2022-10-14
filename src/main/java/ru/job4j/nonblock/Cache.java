package ru.job4j.nonblock;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;

public class Cache {
    private final Map<Integer, Base> memory = new ConcurrentHashMap<>();

    public boolean add(Base model) {
        return memory.putIfAbsent(model.getId(), model) == null;
    }

    public boolean update(Base model) throws OptimisticException {
        BiFunction<Integer, Base, Base> bi = (modelId, oldModel) -> {
            if (oldModel.getVersion() == model.getVersion()) {
                Base modelForUpdate = new Base(modelId, oldModel.getVersion() + 1);
                modelForUpdate.setName(model.getName());
                return modelForUpdate;
            }
            throw new OptimisticException("Versions do not match.");
        };
        return memory.computeIfPresent(model.getId(), bi) != null;
    }

    public void delete(Base model) {
        memory.remove(model.getId(), model);
    }

    public Base get(int modelId) {
        return memory.get(modelId);
    }
}