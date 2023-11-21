package com.rybacki.user.integration

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.test.web.reactive.server.WebTestClient

trait Fixture {
    private final ObjectMapper jsonMapper = new ObjectMapper()

    JsonNode correctUserResponse(Map<String, Object> params = [:]) {
        def json = [
                'id': params.id != null ? params.id : 583231,
                'login': params.login != null ? params.login : 'octocat',
                'name': params.name != null ? params.name : 'The Octocat',
                'type': params.type != null ? params.type : 'User',
                'avatar_url': params.avatar_url != null ? params.avatar_url : 'https://avatars.githubusercontent.com/u/583231?v=4',
                'created_at': params.created_at != null ? params.created_at: '2011-01-25T18:44:36Z',
                'public_repos': params.public_repos != null ? params.public_repos: 8,
                'followers': params.followers != null ? params.followers: 11118
        ]
        return jsonMapper.convertValue(json, JsonNode.class)
    }

    JsonNode emptyResponse(Map<String, Object> params = [:]) {
        def json = []
        return jsonMapper.convertValue(json, JsonNode.class)
    }

    def verifyResponseBody(WebTestClient.BodyContentSpec bodyContentSpec) {
        bodyContentSpec
                .jsonPath('id').isEqualTo(583231)
                .jsonPath('login').isEqualTo('octocat')
                .jsonPath('name').isEqualTo('The Octocat')
                .jsonPath('type').isEqualTo('User')
                .jsonPath('avatarUrl').isEqualTo('https://avatars.githubusercontent.com/u/583231?v=4')
                .jsonPath('createdAt').isEqualTo('2011-01-25T18:44:36Z')
                .jsonPath('calculations').isEqualTo(0.0054d)
    }
}