package testing

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import javax.inject.Inject

@Controller("/my")
class MyController (
        @Inject var testService: TestService
) {
    @Get("/")
    fun index(): HttpResponse<MyResponse> {
        val response = testService.doSomething("woop")
        return HttpResponse.ok(response)
    }
}
