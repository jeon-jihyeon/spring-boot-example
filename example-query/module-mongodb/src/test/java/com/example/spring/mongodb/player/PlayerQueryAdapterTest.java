package com.example.spring.mongodb.player;

import com.example.spring.domain.query.PlayerQuery;
import com.example.spring.mongodb.BaseEmbeddedDbTest;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

class PlayerQueryAdapterTest extends BaseEmbeddedDbTest {

    @Autowired
    private PlayerQueryAdapter adapter;

    void save() {
        var query = new PlayerQuery(11L, "NOVICE", 11, "first", "last", 22L);
        adapter.create(query);
        var found = adapter.findById(query.id());
        assertThat(found.id()).isEqualTo(11L);
        assertThat(found.grade()).isEqualTo("NOVICE");
    }
}