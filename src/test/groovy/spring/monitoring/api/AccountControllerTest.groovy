package spring.monitoring.api

import groovy.json.JsonOutput
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import spock.lang.Specification
import spock.mock.DetachedMockFactory
import spring.monitoring.model.Account
import spring.monitoring.service.AccountService

@WebMvcTest
@ContextConfiguration(classes = [AccountController, MockConfig])
class AccountControllerTest extends Specification {
  @Autowired
  MockMvc mvc

  @Autowired
  AccountService service

  Account account
  String body

  def setup() {
    account = new Account()
    account.id = 1
    account.username = 'leonardo'
    account.password = '1234567890'
    account.name = 'Leonardo Park'
    body = JsonOutput.toJson(account)
  }

  @WithMockUser(username = "leonardo", roles = "USER")
  def "When request account data should return 200"() {
    given:
    def request = MockMvcRequestBuilders.get("/account")

    when:
    def response = mvc.perform(request).andReturn().response

    then:
    1 * service.detail({ it == 'leonardo' }) >> Optional.of(account)
    response.status == HttpStatus.OK.value()
  }

  @WithMockUser(username = "leonardo", roles = "ADMIN")
  def "When request save account data should return 201"() {
    given:
    def request = MockMvcRequestBuilders.post(URI.create("/account"))
      .contentType(MediaType.APPLICATION_JSON_UTF8)
      .with(SecurityMockMvcRequestPostProcessors.csrf())
      .content(body)

    when:
    def response = mvc.perform(request)
      .andReturn().response

    then:
    1 * service.create(_) >> account
    response.status == HttpStatus.CREATED.value()
  }

  @WithMockUser(username = "leonardo", roles = ["ADMIN"])
  def "When request modify account data should return 201"() {
    given:
    def id = 1
    def request = MockMvcRequestBuilders.put("/account/{id}", id)
      .with(SecurityMockMvcRequestPostProcessors.csrf())
      .contentType(MediaType.APPLICATION_JSON_UTF8)
      .content(body)

    when:
    def response = mvc.perform(request)
      .andReturn().response

    then:
    1 * service.modify(id, _) >> account
    response.status == HttpStatus.CREATED.value()
  }

  @WithMockUser(username = "leonardo", roles = ["ADMIN"])
  def "When request remove account data should return 204"() {
    given:
    def id = 1
    def request = MockMvcRequestBuilders.delete("/account/{id}", id)
      .with(SecurityMockMvcRequestPostProcessors.csrf())

    when:
    def response = mvc.perform(request)
      .andReturn().response

    then:
    1 * service.remove(id)
    response.status == HttpStatus.NO_CONTENT.value()
  }

  @TestConfiguration
  static class MockConfig {
    def factory = new DetachedMockFactory()

    @Bean
    AccountService service() {
      return factory.Mock(AccountService)
    }
  }

}
