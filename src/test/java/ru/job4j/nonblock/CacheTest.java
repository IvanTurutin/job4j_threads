package ru.job4j.nonblock;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class CacheTest {

    @Test
    public void whenAdd() {
        Base model = new Base(1, 0);
        model.setName("Ivan");
        Cache cache = new Cache();
        assertThat(cache.add(model)).isEqualTo(true);
        assertThat(cache.get(model.getId()).getName()).isEqualTo("Ivan");
        assertThat(cache.add(model)).isEqualTo(false);
    }

    @Test
    public void whenDelete() {
        Base model = new Base(1, 0);
        model.setName("Ivan");
        Cache cache = new Cache();
        cache.add(model);
        cache.delete(model);
        assertThat(cache.get(model.getId())).isEqualTo(null);
    }

    @Test
    public void whenUpdateIsDone() {
        Base model = new Base(1, 0);
        model.setName("Ivan");
        Cache cache = new Cache();
        cache.add(model);
        Base modelForUpdate = new Base(1, 0);
        modelForUpdate.setName("Nikolay");
        assertThat(cache.update(modelForUpdate)).isEqualTo(true);
        assertThat(cache.get(model.getId()).getName()).isEqualTo("Nikolay");
        assertThat(cache.get(model.getId()).getVersion()).isEqualTo(1);
    }

    @Test
    public void whenUpdateIsFail() {
        Base model = new Base(1, 0);
        model.setName("Ivan");
        Cache cache = new Cache();
        cache.add(model);
        Base modelForUpdate = new Base(1, 1);
        modelForUpdate.setName("Nikolay");
        assertThatThrownBy(() -> cache.update(modelForUpdate)).isInstanceOf(OptimisticException.class);
    }
}