package testing

import io.kotlintest.shouldBe
import io.kotlintest.specs.BehaviorSpec
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.RxHttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.annotation.MicronautTest
import io.micronaut.test.annotation.MockBean
import io.micronaut.test.extensions.kotlintest.MicronautKotlinTestExtension.getMock
import io.mockk.every
import io.mockk.mockk

@MicronautTest
class MyControllerTest(
        private val testService: TestService,
        @Client("/") private val client: RxHttpClient
): BehaviorSpec({

    given("the server is running") {
        `when`("the mock is provided") {
            val mock = getMock(testService)
            every { mock.doSomething(any()) } returns MyResponse("only a test")
            // This needs to be at this scope level!
            val response = client.toBlocking().exchange(HttpRequest.GET<String>("/my"), MyResponse::class.java)

            then("The server should respond properly") {
                // "the response is succesful"
                val responseBody = response.body() as MyResponse
                responseBody.text shouldBe "only a test"
                response.status shouldBe HttpStatus.OK

            }
            then("the mock implementation is used") {
                mock.doSomething("kaka").text shouldBe "only a test"
            }
        }
    }
}) {

    @MockBean(TestServiceImpl::class)
    fun testService(): TestService {
        return mockk()
    }
}
