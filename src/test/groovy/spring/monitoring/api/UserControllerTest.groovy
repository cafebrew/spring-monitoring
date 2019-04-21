package spring.monitoring.api

import groovy.json.JsonOutput
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import spock.lang.Specification
import spock.mock.DetachedMockFactory
import spring.monitoring.model.User
import spring.monitoring.service.UserService

@WebMvcTest
@ContextConfiguration
class UserControllerTest extends Specification {
  @Autowired
  MockMvc mvc

  @Autowired
  UserService service

  User user

  def setup() {
    user = new User()
    user.id = 1
    user.userId = 'leonardo'
  }

  def "When request user data should return 200"() {
    given:
    def request = MockMvcRequestBuilders.get("/user?userId={userId}", 'leonardo')

    when:
    def response = mvc.perform(request).andReturn().response

    then:
    1 * service.detail('leonardo') >> user
    response.status == HttpStatus.OK.value()
  }

  def "When request save user data should return 201"() {
    given:
    def request = MockMvcRequestBuilders.post("/user/")
      .contentType(MediaType.APPLICATION_JSON_UTF8)
      .content(JsonOutput.toJson(user))

    when:
    def response = mvc.perform(request).andReturn().response

    then:
    1 * service.create(_) >> user
    response.status == HttpStatus.CREATED.value()
  }

  def "When request modify user data should return 201"() {
    given:
    def id = 1
    def request = MockMvcRequestBuilders.put("/user/{id}", id)
      .contentType(MediaType.APPLICATION_JSON_UTF8)
      .content(JsonOutput.toJson(user))

    when:
    def response = mvc.perform(request).andReturn().response

    then:
    1 * service.modify(id, _) >> user
    response.status == HttpStatus.CREATED.value()
  }

  def "When request remove user data should return 204"() {
    given:
    def id = 1
    def request = MockMvcRequestBuilders.delete("/user/{id}", id)

    when:
    def response = mvc.perform(request).andReturn().response

    then:
    1 * service.remove(id)
    response.status == HttpStatus.NO_CONTENT.value()
  }

  @TestConfiguration
  static class MockConfig {
    def factory = new DetachedMockFactory()

    @Bean
    UserService service() {
      return factory.Mock(UserService)
    }
  }

}
