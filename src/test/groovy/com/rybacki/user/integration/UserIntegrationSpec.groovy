package com.rybacki.user.integration

import com.github.tomakehurst.wiremock.WireMockServer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient
import spock.lang.Specification
import spock.lang.Unroll

import static com.github.tomakehurst.wiremock.client.WireMock.get
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class UserIntegrationSpec extends Specification implements Fixture {

    @Autowired
    private WireMockServer wireMockServer

    @Autowired
    private WebTestClient webClient

    @Autowired
    private JdbcTemplate jdbcTemplate

    @Value("\${user.selectSql}")
    private String selectSql

    def 'should return ok and increment request counter when user exist'() {
        given:
        def login = 'octocat'
        getRequestCounter(login) == 0
        wireMockServer.stubFor(get("/users/$login")
                .willReturn(aResponse().withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withJsonBody(correctUserResponse())))

        when:
        def response = webClient.get().uri("/users/$login").exchange()

        then:
        response.expectStatus().isOk()
        verifyResponseBody(response.expectBody())
        getRequestCounter(login) == 1

        when:
        response = webClient.get().uri("/users/$login").exchange()

        then:
        response.expectStatus().isOk()
        verifyResponseBody(response.expectBody())
        getRequestCounter(login) == 2
    }

    @Unroll
    def 'should return bad request when login is #login'() {
        expect:
        webClient.get().uri("/users/$login").exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .jsonPath('$.message').isEqualTo(errorMessage)

        where:
        login    | errorMessage
        ' '      | 'Login cannot be blank'
        'A' * 51 | 'Login is too long'
    }

    @Unroll
    def 'should return status #returnStatus when external service return status #externalStatus'() {
        given:
        wireMockServer.stubFor(get("/users/errorLogin")
                .willReturn(aResponse().withStatus(externalStatus)
                        .withHeader("Content-Type", "application/json")
                        .withJsonBody(body)))

        expect:
        webClient.get().uri("/users/errorLogin").exchange()
                .expectStatus().isEqualTo(returnStatus)
                .expectBody()
                .jsonPath('$.message').isEqualTo(errorMessage)

        where:
        externalStatus | body                  | returnStatus | errorMessage
        404            | correctUserResponse() | 400          | 'User does not exist'
        200            | emptyResponse()       | 400          | 'Problem with retrieve user'
        400            | correctUserResponse() | 400          | 'Problem with retrieve user'
        401            | correctUserResponse() | 400          | 'Problem with retrieve user'
        500            | correctUserResponse() | 400          | 'Problem with retrieve user'

    }

    def 'should return calculation error when in calculation divide by 0'() {
        given:
        wireMockServer.stubFor(get("/users/octocat")
                .willReturn(aResponse().withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withJsonBody(correctUserResponse("followers": 0))))

        expect:
        webClient.get().uri("/users/octocat").exchange()
                .expectStatus().isEqualTo(400)
                .expectBody()
                .jsonPath('$.message').isEqualTo('Cannot perform calculation for given login')
    }

    int getRequestCounter(String login) {
        try {
            return jdbcTemplate.queryForObject(selectSql, Integer.class, login)
        } catch (EmptyResultDataAccessException ignore) {
            return 0
        }
    }
}