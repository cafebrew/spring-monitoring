package spring.monitoring.service

import io.micrometer.core.instrument.Counter
import spock.lang.Specification
import spring.monitoring.model.User
import spring.monitoring.repository.UserRepository

class UserServiceTest extends Specification {

  UserRepository repository = Mock()
  UserService service
  Optional<User> user

  def setup() {
    service = new UserService(repository, Mock(Counter))
    def u = new User()
    u.id = 1
    u.userId = 'spring'
    u.username = 'leonardo'
    user = Optional.of(u)
  }

  def "When invoke detail should return user"() {
    given:
    def userId = 'leonardo'

    when:
    service.detail(userId)

    then:
    1 * repository.findByUserId({ it == userId }) >> user
  }

  def "When invoke modify should return user"() {
    given:
    User request = new User()
    request.setId(1)
    request.setUserId('spring')
    request.setUsername('leonardo')
    request.setEmail('ageofblue@gmail.com')

    when:
    service.modify(1, request)

    then:
    1 * repository.findById({ it == request.id }) >> user
    1 * repository.save({ it.email == 'ageofblue@gmail.com' })
  }

  def "When invoke create should return user"() {
    given:
    User request = new User()
    request.userId = 'leonardo'

    when:
    service.create(request)

    then:
    1 * repository.save({ it.userId == 'leonardo' })
  }


  def "When invoke remove parameter bypass well"() {
    when:
    user = service.remove(1)

    then:
    1 * repository.deleteById({ it == 1 })
  }

}
