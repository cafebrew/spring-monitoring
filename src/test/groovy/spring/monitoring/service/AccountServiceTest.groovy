package spring.monitoring.service

import io.micrometer.core.instrument.Counter
import spock.lang.Specification
import spring.monitoring.model.Account
import spring.monitoring.repository.AccountRepository

class AccountServiceTest extends Specification {

  AccountRepository repository = Mock()
  AccountService service
  Account account

  def setup() {
    service = new AccountService(repository, Mock(Counter))
    account = new Account()
    account.id = 1
    account.username = 'leonardo'
    account.password = '1234567890'
    account.name = 'Leonardo Park'
  }

  def "When invoke detail should return user"() {
    given:
    def username = 'leonardo'

    when:
    service.detail(username)

    then:
    1 * repository.findByUsername({ it == username }) >> Optional.of(account)
  }

  def "When invoke modify should return user"() {
    given:
    def request = new Account()
    request.id = 1
    request.username = 'spring'
    request.name = 'Leonardo.'
    request.email = 'ageofblue@gmail.com'

    when:
    service.modify(request.id, request)

    then:
    1 * repository.findById({ it == request.id }) >> Optional.of(account)
    1 * repository.save({ it.email == 'ageofblue@gmail.com' })
  }

  def "When invoke create should return user"() {
    given:
    Account request = new Account()
    request.username = 'leonardo'

    when:
    service.create(request)

    then:
    1 * repository.save({ it.username == 'leonardo' })
  }


  def "When invoke remove parameter bypass well"() {
    when:
    service.remove(1)

    then:
    1 * repository.deleteById({ it == 1 })
  }

}
